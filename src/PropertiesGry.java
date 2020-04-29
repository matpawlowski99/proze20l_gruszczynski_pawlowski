import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.Arrays;
/** Klasa odpowiadajaca za rozmieszczenie i ruch elementow na planszy gry*/
public class PropertiesGry {
/** Szerokosc okna ustawiona na domyslna*/
    static final int SZEROKOSC = 800;
    /** Wysokosc okna ustawiona na domyślna*/
    static final int WYSOKOSC = 600;
/** Polozenie na osi x wierzcholkow planety*/
    static int[] xWierzcholkiPlanety;
    /** Polozenie na osi y wierzcholkow planety*/
    static int[] yWierzcholkiPlanety;
    /** Polozenie na osi x ladowiska*/
    static int[] xLadowiska;
    /** Polozenie na osi y ladowiska*/
    static int[] yLadowiska;
    /** Polozenie na osi x punktu startowego statku*/
    static int[] xStartoweStatku;
    /** Polozenie na osi y punktu startowego statku*/
    static int[] yStartoweStatku;
    /** Poloznie na osi x bonusu*/
    static int[] xBonusu;
    /** Polozenie na osi y bonusu*/
    static int[] yBonusu;

    /** Predkosc statku wzdluz osi x*/
    static int xPredkoscStatku;
    /** Predkosc statku wzdluz osi y*/
    static int yPredkoscStatku;
    /** Predkosc maksymalna wzdluz osi x*/
    static int xPredkoscMax;
    /** Predkosc maksymalna wzdluz osi y*/
    static int yPredkoscMax;
    /** Predkosc maksymalna ladowania wzdluz osi x (ktora nie powoduje rozbicia sie statku)*/
    static int xPredkoscLadowania;
    /** Predkosc maksymalna ladowania wzdluz osi y (ktora nie powoduje rozbicia sie statku)*/
    static int yPredkoscLadowania;
    /** Zmienna przechowujaca liczbe statkow*/
    static int liczbaStatkow;
    /** Zmienna przechowujaca paliwo, które pozostało*/
    static int paliwo;
    /** Zmienna przechowująca liczbe poziomow gry*/
    static int liczbaPoziomow;
    /** Zmienna przechowujaca liczbe uzyskanych punktow za przejscie poziomu*/
    static int punktyZaPlansze;
    /** Zmienna przechowujaca liczbe uzyskanych punktow za zdobycie bonusu*/
    static int punktyZaBonus;
/** Plik jako strumien wejsciowy */
    private static InputStream plik;
 /** Wlasciwosci rozgrywki*/
    private static Properties configPlanszy;

/** Metoda jednoparametrowa, ktora wczytuje plansze gry, obsluguje wyjatki zwiazane z wczytaniem plikow przy pomocy IOException
 * @param nrPlanszy jako nrPlanszy,
 * @throws IOException , gdy plik nie istnieje.
 * */
    public static void wczytajPlansze(int nrPlanszy) throws IOException {
        plik = new FileInputStream("plansza" + nrPlanszy + ".txt");
        configPlanszy = new Properties();
        configPlanszy.load(plik);

        xWierzcholkiPlanety = Arrays.stream(configPlanszy.getProperty("xWierzcholki").split("-")).mapToInt(Integer::parseInt).toArray();
        yWierzcholkiPlanety = Arrays.stream(configPlanszy.getProperty("yWierzcholki").split("-")).mapToInt(Integer::parseInt).toArray();
        xLadowiska = Arrays.stream(configPlanszy.getProperty("xLadowiska").split("-")).mapToInt(Integer::parseInt).toArray();
        yLadowiska = Arrays.stream(configPlanszy.getProperty("yLadowiska").split("-")).mapToInt(Integer::parseInt).toArray();
        xStartoweStatku = Arrays.stream(configPlanszy.getProperty("xStartoweStatku").split("-")).mapToInt(Integer::parseInt).toArray();
        yStartoweStatku = Arrays.stream(configPlanszy.getProperty("yStartoweStatku").split("-")).mapToInt(Integer::parseInt).toArray();
        xBonusu = Arrays.stream(configPlanszy.getProperty("xBonusu").split("-")).mapToInt(Integer::parseInt).toArray();
        yBonusu = Arrays.stream(configPlanszy.getProperty("yBonusu").split("-")).mapToInt(Integer::parseInt).toArray();

        xPredkoscStatku = Integer.parseInt(configPlanszy.getProperty("xPredkoscStatku"));
        yPredkoscStatku = Integer.parseInt(configPlanszy.getProperty("yPredkoscStatku"));
        xPredkoscMax = Integer.parseInt(configPlanszy.getProperty("xPredkoscMax"));
        yPredkoscMax = Integer.parseInt(configPlanszy.getProperty("yPredkoscMax"));
        xPredkoscLadowania = Integer.parseInt(configPlanszy.getProperty("xPredkoscLadowania"));
        yPredkoscLadowania = Integer.parseInt(configPlanszy.getProperty("yPredkoscLadowania"));

        liczbaStatkow = Integer.parseInt(configPlanszy.getProperty("liczbaStatkow"));
        paliwo = Integer.parseInt(configPlanszy.getProperty("paliwo"));
        liczbaPoziomow = Integer.parseInt(configPlanszy.getProperty("liczbaPoziomow"));
        punktyZaPlansze = Integer.parseInt(configPlanszy.getProperty("punktyZaPlansze"));
        punktyZaBonus = Integer.parseInt(configPlanszy.getProperty("punktyZaBonus"));

        dostosujSkale();
    }
/** Metoda, ktora dostosowywuje skale planszy*/
    public static void dostosujSkale() {
        xWierzcholkiPlanety = Arrays.stream(xWierzcholkiPlanety).map(x_wierzcholki_planety -> (int)(0.0125 * PropertiesGry.SZEROKOSC * x_wierzcholki_planety)).toArray();
        yWierzcholkiPlanety = Arrays.stream(yWierzcholkiPlanety).map(y_wierzcholki_planety -> (int)(0.0125 * PropertiesGry.WYSOKOSC * y_wierzcholki_planety)).toArray();
        xLadowiska = Arrays.stream(xLadowiska).map(x_ladowiska -> (int)(0.0125 * PropertiesGry.SZEROKOSC * x_ladowiska)).toArray();
        yLadowiska = Arrays.stream(yLadowiska).map(y_ladowiska -> (int)(0.0125 * PropertiesGry.WYSOKOSC * y_ladowiska)).toArray();
        xStartoweStatku = Arrays.stream(xStartoweStatku).map(x_startowe_statku -> (int)(0.0125 * PropertiesGry.SZEROKOSC * x_startowe_statku)).toArray();
        yStartoweStatku = Arrays.stream(yStartoweStatku).map(y_startowe_statku -> (int)(0.0125 * PropertiesGry.WYSOKOSC * y_startowe_statku)).toArray();
        xBonusu = Arrays.stream(xBonusu).map(x_bonusu -> (int)(0.0125 * PropertiesGry.SZEROKOSC * x_bonusu)).toArray();
        yBonusu = Arrays.stream(yBonusu).map(y_bonusu -> (int)(0.0125 * PropertiesGry.WYSOKOSC * y_bonusu)).toArray();
    }
}
