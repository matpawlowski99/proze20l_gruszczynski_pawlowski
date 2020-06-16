import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Comparator;

/**
 * Klasa odpowiadajaca za okno RANKING NAJLEPSZYCH
 */
class RankingNajlepszych extends JDialog {
    /** Lista przechowujaca rekordy w postaci lancucha znakow*/
    private static ArrayList<String> rekordy;
    /** Panel przechowujacy komponenty (kolejne wyniki graczy)*/
    private JPanel panel;
    /** Tablica etykiet przechowujacych nicki graczy oraz ich wyniki*/
    private JLabel[] lRekord;
    /** Przycisk odpowiadajacy za powrot do menu glownego*/
    private JButton bWstecz;

    /** Metoda rysujaca okienko RANKING NAJLEPSZYCH, posiada jeden parametr:
     * @param owner jest komponentem modalnym na rzecz komponentu public RankingNajlepszych().
     */
    RankingNajlepszych(JFrame owner) throws IOException {
        super(owner,"Ranking najlepszych",true);
        //wczytajRanking();
        setSize(400,300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(rekordy.size(),1));

        lRekord = new JLabel[rekordy.size()];
        for (int i = 0; i < rekordy.size(); i++) {
            lRekord[i] = new JLabel((i + 1) + ". " + rekordy.get(i), SwingConstants.CENTER);
            lRekord[i].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            panel.add(lRekord[i]);
        }
        add(panel,BorderLayout.CENTER);

        bWstecz = new JButton("Powrót do menu głównego");
        bWstecz.addActionListener(e -> dispose());
        add(bWstecz,BorderLayout.SOUTH);

    }

    /** Metoda zapisujaca ranking najlepszych graczy
     @throws IOException gdy plik nie istnieje
     */
    static void zapiszRanking() throws IOException {
        InputStream plik = new FileInputStream("ranking.txt");
        Properties ranking = new Properties();
        ranking.load(plik);
        for (int i = 0; i < rekordy.size(); i++) {
            ranking.setProperty("gracz" + (i + 1), rekordy.get(i).split(" - ")[0]);
            ranking.setProperty("wynik" + (i + 1), rekordy.get(i).split(" - ")[1]);
        }
        ranking.store(new FileOutputStream("ranking.txt"), null);
        plik.close();
    }

    /** Metoda wczytujaca ranking najlepszych graczy
     @throws IOException gdy plik nie istnieje
     */
    static void wczytajRanking() throws IOException {
        InputStream plik = new FileInputStream("ranking.txt");
        Properties ranking = new Properties();
        ranking.load(plik);
        rekordy = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            if (ranking.containsKey("gracz" + i))
                rekordy.add(ranking.getProperty("gracz" + i) + " - " + ranking.getProperty("wynik" + i));
        }
        plik.close();
        rekordy.sort(new PosortujRanking());
    }

    /** Metoda dodajaca rekord do listy rekordow
     @param gracz nick gracza
     @param wynik wynik gracza
     @throws IOException gdy plik nie istnieje
     */
    static void dodajRekord(String gracz, int wynik) throws IOException {
        wczytajRanking();
        rekordy.add(gracz + " - " + wynik);
        rekordy.sort(new PosortujRanking());
        if (rekordy.size() > 10)
            rekordy.remove(rekordy.size() - 1);
        zapiszRanking();
    }

    /** Klasa odpowiadajaca za sortowanie wynikow w rankingu, implementuje interfejs Comparator
     */
    static class PosortujRanking implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            Integer pierwszy = Integer.parseInt(s1.split(" - ")[1]);
            Integer drugi = Integer.parseInt(s2.split(" - ")[1]);
            return -pierwszy.compareTo(drugi);
        }
    }
}
