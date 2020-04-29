import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.TimerTask;
import static java.lang.StrictMath.abs;
/** Klasa rozszerza Canvas, ktora stosujemy, by swobodnie rysowac po ekranie */
public class Gra extends Canvas {
/** Opis planety jako wielokata*/
    Polygon planeta;
    /** Opis ladowiska jako wielokata*/
    Polygon ladowisko;
    /** Opis statku jako wielokata*/
    Polygon statek;
    /** Opis bonusa jako wielokata*/
    Polygon bonus;
    /**Graphics2D to rozszerzenie graphics do rysowania figur 2D*/
    Graphics2D g2d;
    /** Przeksztalcenie geometryczne przestrzeni, ktore odwzorowuje odcinki na odcinki, proste w proste itd */
    AffineTransform skaluj, macierz;
    /** Wspolczynniki przeksztalcenia wzdluz osi x oraz osi y*/
    float skalujX, skalujY;
    /** Wynik gracza na poczatku przyjmuje wartosc 0 */
    int wynik = 0;
    /** Liczba statkow*/
    int liczbaStatkow;
    /** Ilosc paliwa*/
    int paliwo;
    /** Punkty uzyskane za przejscie planszy*/
    int punktyZaPlansze;
    /**Punkty uzyskane za zdobycie bonusu*/
    int punktyZaBonus;

    /** Wspolrzedne statku*/
    int [] xStatku, yStatku;
    /** Szerokosc statku*/
    double szerokoscStatku;
    /** Wysokosc statku*/
    double wysokoscStatku;
    /** Predkosc statku wzdluz osi x oraz wzdluz osi y*/
    float xPredkosc, yPredkosc;

    /** Zmienna informujaca czy zdobyto bonus*/
    boolean czyZebranoBonus = false;
    /** Zmienna informujaca czy sie rozbito*/
    boolean czyRozbito;
    /** Zmienna informujaca czy wyladowano*/
    boolean czyWyladowano;
    /** Zmienna informujaca czy zapauzowano gre, wstepnie gra nie jest zapauzowana*/
    boolean czyZapauzowano = false;

    /**Konstruktor, ktory wczytuje poziom gry, obsluguje wyjatki zwiazane z wczytaniem plikow przy pomocy IOException
     @param level jako poziom
     @throws IOException , gdy plik nie istnieje */
    Gra(int level) throws IOException {
        PropertiesGry.wczytajPlansze(level);
        xStatku = PropertiesGry.xStartoweStatku;
        yStatku = PropertiesGry.yStartoweStatku;
        liczbaStatkow = PropertiesGry.liczbaStatkow;
        paliwo = PropertiesGry.paliwo;
        punktyZaPlansze = PropertiesGry.punktyZaPlansze;
        punktyZaBonus = PropertiesGry.punktyZaBonus;
    }

    /** Metoda, ktora tworzy nam strategie buforowania potrojnego, ta metoda jest potrzebna do animacji.
     * @return - zwraca nam wartosc bs, pobrana dzieki getBufferStrategy().
     */
    private BufferStrategy zainicjalizujBufor() {
        BufferStrategy bs = getBufferStrategy();
        if (null == bs) {
            createBufferStrategy(3);
            bs = getBufferStrategy();
        }
        return bs;
    }

    /** Metoda paint tworzy obiekt klasy Graphics i przypisuje mu zrzutowany na te klase argument g.
     * Metoda paint uwzglednia skalowanie elementow w rozgrywce, zawiera jeden parametr:
     * @param g parametr przekazujacy informacje o malowaniu danego elementu.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        planeta = new Polygon(PropertiesGry.xWierzcholkiPlanety, PropertiesGry.yWierzcholkiPlanety, PropertiesGry.xWierzcholkiPlanety.length);
        ladowisko = new Polygon(PropertiesGry.xLadowiska, PropertiesGry.yLadowiska, PropertiesGry.xLadowiska.length);
        statek = new Polygon(xStatku,yStatku,xStatku.length);

        g2d = (Graphics2D) g;
        setBackground(Color.BLACK);
        g.clearRect(0, 0, getWidth(), getHeight());

        skaluj = g2d.getTransform();
        macierz = new AffineTransform();
        skalujX = (1+(getSize().width - PropertiesGry.SZEROKOSC) / (float) PropertiesGry.SZEROKOSC);
        skalujY = (1+(getSize().height - PropertiesGry.WYSOKOSC) / (float) PropertiesGry.WYSOKOSC);
        macierz.scale(skalujX, skalujY);
        g2d.setTransform(macierz);

        g2d.setColor(Color.CYAN);
        g2d.fill(statek);

        g2d.setColor(new Color (29, 84, 13));
        g2d.fill(ladowisko);

        g2d.setColor(new Color (108, 108, 145));
        g2d.fill(planeta);

        if (!czyZebranoBonus) {
            bonus = new Polygon(PropertiesGry.xBonusu, PropertiesGry.yBonusu, PropertiesGry.xBonusu.length);
            g2d.setColor(Color.RED);
            g2d.fill(bonus);
            zebranieBonusu(bonus, statek);
        }

        //System.out.println("SZEROKOŚĆ: " + this.getSize().width + ", WYSOKOŚĆ: " + this.getSize().height);

        szerokoscStatku = statek.getBounds().getWidth();
        wysokoscStatku = statek.getBounds().getHeight();

        g2d.setTransform(skaluj);
        //System.out.println("SZEROKOŚĆ: " + this.getSize().width + ", WYSOKOŚĆ: " + this.getSize().height);
        zderzenie(planeta, ladowisko, statek);
    }

    /** Metoda, ktora aktualizuje polozenie statku i jego prędkosc*/
    public void aktualizujPolozenieStatku() {
        for (int i = 0; i <= 2; i++) {
            xStatku[i] += xPredkosc;
            yStatku[i] += yPredkosc;
        }

        if (xPredkosc > abs(PropertiesGry.xPredkoscMax))
            xPredkosc = PropertiesGry.xPredkoscMax;
        if (xPredkosc < -PropertiesGry.xPredkoscMax)
            xPredkosc = -PropertiesGry.xPredkoscMax;
        if (yPredkosc < abs(PropertiesGry.yPredkoscMax))
            yPredkosc += 0.25;
        if (yPredkosc < -PropertiesGry.yPredkoscMax)
            yPredkosc = -PropertiesGry.yPredkoscMax;

        if (xStatku[0] < 0) {
            xStatku[0] = 0;
            xStatku[1] = (int) szerokoscStatku;
            xStatku[2] = (int) (szerokoscStatku/2);
            xPredkosc = 0;
        }
        if (xStatku[1] > (PropertiesGry.SZEROKOSC * 1.25)) {
            xStatku[0] = (int) ((PropertiesGry.SZEROKOSC * 1.25) - szerokoscStatku);
            xStatku[1] = (int) (PropertiesGry.SZEROKOSC * 1.25);
            xStatku[2] = (int) ((PropertiesGry.SZEROKOSC * 1.25) - (szerokoscStatku/2));
            xPredkosc = 0;
        }
        if (yStatku[2] < 1) {
            yStatku[0] = (int) wysokoscStatku + 1;
            yStatku[1] = (int) wysokoscStatku + 1;
            yStatku[2] = 1;
            yPredkosc = 0;
        }

        if (xPredkosc > 0)
            xPredkosc -= 0.5;
        if (xPredkosc < 0)
            xPredkosc += 0.5;
    }

    /** Metoda informujaca o zderzeniu statku z podlozem (planeta lub ladowiskiem), posiada 3 parametry:
     * @param planeta to bledne podloze- kontakt z nim to stracone zycie,
     * @param ladowisko to poprawne podloze- kontakt z nim to ukonczenie poziomu,
     * @param statek to nasz obiekt, ktorym sie poruszamy.
     */
    private void zderzenie(Polygon planeta, Polygon ladowisko, Polygon statek) {
        if (ladowisko.intersects(statek.getBounds()) && (abs(xPredkosc) <= PropertiesGry.xPredkoscLadowania && abs(yPredkosc) <= abs(PropertiesGry.yPredkoscLadowania))) {
            czyWyladowano = true;
            czyRozbito = false;
            //System.out.println("Wyladowano");
        }
        if ((ladowisko.intersects(statek.getBounds()) && (abs(xPredkosc) > PropertiesGry.xPredkoscLadowania || abs(yPredkosc) > PropertiesGry.yPredkoscLadowania)) || planeta.intersects(statek.getBounds())) {
            czyWyladowano = false;
            czyRozbito = true;
            //System.out.println("Rozbito");
        }
    }

    /** Metoda informujaca o zebraniu bonusu przez statek, posiada 2 parametry:
     * @param bonus to obiekt unoszacy sie w przestrzeni, zderzenie z nim to uzyskanie dodatkowych punktow,
     * @param statek to nasz obiekt, ktorym sie poruszamy.
     */
    private void zebranieBonusu(Polygon bonus, Polygon statek) {
        if (bonus.intersects(statek.getBounds())) {
            wynik += punktyZaBonus;
            czyZebranoBonus = true;
        }
    }

    /** Metoda, ktora informuje o zapauzowaniu rozgrywki.
     */
    public void czyZapauzowano() {
        czyZapauzowano = true;
    }

    /** Metoda, ktora informuje o wznowieniu rozgrywki.
     */
    public void czyWznowiono() {
        czyZapauzowano = false;
    }

    /** Klasa odpowiadajaca za animacje rozgrywki, aktualizuje polozenie, uwzgledniajac pauze. Wyswietla nastepny dostepny bufor, kopiujac pamiec.
     */
    public class Animacja extends TimerTask {
        int i = 0;
/** Metoda, ktora odpowiada za dzialanie Timera*/
        public void run() {
            if (!czyZapauzowano) {
                if (i == 3) {
                    aktualizujPolozenieStatku();
                    i = 0;
                }
                i++;
            }
            do {
                do {
                    Graphics graphics = zainicjalizujBufor().getDrawGraphics();
                    paint(graphics);
                    graphics.dispose();
                } while (zainicjalizujBufor().contentsRestored());
                zainicjalizujBufor().show();
            } while (zainicjalizujBufor().contentsLost());
        }
    }
}
