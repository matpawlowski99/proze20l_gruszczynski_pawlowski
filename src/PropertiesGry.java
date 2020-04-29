import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.Arrays;

public class PropertiesGry {

    static final int SZEROKOSC = 800;
    static final int WYSOKOSC = 600;

    static int[] xWierzcholkiPlanety;
    static int[] yWierzcholkiPlanety;
    static int[] xLadowiska;
    static int[] yLadowiska;
    static int[] xStartoweStatku;
    static int[] yStartoweStatku;
    static int[] xBonusu;
    static int[] yBonusu;

    static int xPredkoscStatku;
    static int yPredkoscStatku;
    static int xPredkoscMax;
    static int yPredkoscMax;
    static int xPredkoscLadowania;
    static int yPredkoscLadowania;
    static int liczbaStatkow;
    static int paliwo;
    static int liczbaPoziomow;
    static int punktyZaPlansze;
    static int punktyZaBonus;

    private static InputStream plik;
    private static Properties configPlanszy;

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