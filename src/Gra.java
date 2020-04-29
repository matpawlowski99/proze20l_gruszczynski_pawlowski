import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.TimerTask;

import static java.lang.StrictMath.abs;

public class Gra extends Canvas {

    Polygon planeta, ladowisko, statek, bonus;
    Graphics2D g2d;
    AffineTransform skaluj, macierz;
    float skalujX, skalujY;
    int wynik = 0;
    int liczbaStatkow, paliwo, punktyZaPlansze, punktyZaBonus;

    int [] xStatku, yStatku;
    double szerokoscStatku, wysokoscStatku;
    float xPredkosc, yPredkosc;

    boolean czyZebranoBonus = false;
    boolean czyRozbito;
    boolean czyWyladowano;
    boolean czyZapauzowano = false;

    Gra(int level) throws IOException {
        PropertiesGry.wczytajPlansze(level);
        xStatku = PropertiesGry.xStartoweStatku;
        yStatku = PropertiesGry.yStartoweStatku;
        liczbaStatkow = PropertiesGry.liczbaStatkow;
        paliwo = PropertiesGry.paliwo;
        punktyZaPlansze = PropertiesGry.punktyZaPlansze;
        punktyZaBonus = PropertiesGry.punktyZaBonus;
    }

    private BufferStrategy zainicjalizujBufor() {
        BufferStrategy bs = getBufferStrategy();
        if (null == bs) {
            createBufferStrategy(3);
            bs = getBufferStrategy();
        }
        return bs;
    }

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

    private void zebranieBonusu(Polygon bonus, Polygon statek) {
        if (bonus.intersects(statek.getBounds())) {
            wynik += punktyZaBonus;
            czyZebranoBonus = true;
        }
    }

    public void czyZapauzowano() {
        czyZapauzowano = true;
    }

    public void czyWznowiono() {
        czyZapauzowano = false;
    }

    public class Animacja extends TimerTask {
        int i = 0;

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