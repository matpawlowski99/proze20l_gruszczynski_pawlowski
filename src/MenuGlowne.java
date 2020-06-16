import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Klasa odpowiadajaca za okno glowne i obsluge menu glownego
 */
public class MenuGlowne extends JFrame implements ActionListener {
    /** Wybor gry: online czy offline*/
    private OnlineCzyOffline onlineCzyOffline;
    /** Przechowuje informacje o zasadach gry*/
    private ZasadyGry zasadyGry;
    /** Przechowuje informacje o rankingu najlepszych graczy*/
    private RankingNajlepszych rankingNajlepszych;
    /** Przechowuja odpowiednie elementy menu glownego*/
    private JPanel lewyPanel, prawyPanel, centralnyPanel;
    /** Zmienna odpowiedzialna za przycisk: GRAJ*/
    private JButton bGraj;
    /** Zmienna odpowiedzialna za przycisk: ZASADY GRY*/
    private JButton bZasadyGry;
    /** Zmienna odpowiedzialna za przycisk: RANKING NAJLEPSZYCH*/
    private JButton bRankingNajlepszych;
    /** Zmienna odpowiedzialna za przycisk: WYJSCIE*/
    private JButton bWyjscie;
    /** Tablice etykiet przechowujacych litery*/
    private JLabel[] leweEtykiety, praweEtykiety;
    /** Lancuch przechowujacy litery do etykiet*/
    private String tytul = "LUNAR LANDER";

    /** Metoda, ktora odpowiada za ustawienie okna glownego. Dodatkowo następuje utworzenie kazdego z przyciskow, dodanie go oraz utworzenie w kazdym sluchacza.
     */
    public MenuGlowne() {
        super("Lunar Lander");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        setSize(550,400);
        setLayout(new BorderLayout());

        centralnyPanel = new JPanel();
        centralnyPanel.setLayout(new GridLayout(4,1));

        bGraj = new JButton("GRAJ!");
        centralnyPanel.add(bGraj);
        bGraj.addActionListener(this);

        bZasadyGry = new JButton("ZASADY GRY");
        centralnyPanel.add(bZasadyGry);
        bZasadyGry.addActionListener(this);

        bRankingNajlepszych = new JButton("RANKING NAJLEPSZYCH");
        centralnyPanel.add(bRankingNajlepszych);
        bRankingNajlepszych.addActionListener(this);

        bWyjscie = new JButton("WYJŚCIE");
        centralnyPanel.add(bWyjscie);
        bWyjscie.addActionListener(this);

        lewyPanel = new JPanel();
        leweEtykiety = new JLabel[tytul.split(" ")[0].length()];
        for (int i = 0; i < tytul.split(" ")[0].length(); i++) {
            leweEtykiety[i] = new JLabel(" " + tytul.split(" ")[0].charAt(i) + " ",SwingConstants.CENTER);
            leweEtykiety[i].setFont(new Font("",Font.BOLD,50));
            leweEtykiety[i].setBackground(new Color(0,7,72));
            leweEtykiety[i].setForeground(Color.WHITE);
            leweEtykiety[i].setOpaque(true);
            lewyPanel.add(leweEtykiety[i]);
        }
        lewyPanel.setLayout(new GridLayout(leweEtykiety.length,1));

        prawyPanel = new JPanel();
        praweEtykiety = new JLabel[tytul.split(" ")[1].length()];
        for (int i = 0; i < tytul.split(" ")[1].length(); i++) {
            praweEtykiety[i] = new JLabel(" " + tytul.split(" ")[1].charAt(i) + " ",SwingConstants.CENTER);
            praweEtykiety[i].setFont(new Font("",Font.BOLD,50));
            praweEtykiety[i].setBackground(new Color(0,7,72));
            praweEtykiety[i].setForeground(Color.WHITE);
            praweEtykiety[i].setOpaque(true);
            prawyPanel.add(praweEtykiety[i]);
        }
        prawyPanel.setLayout(new GridLayout(praweEtykiety.length,1));

        add(lewyPanel,BorderLayout.WEST);
        add(prawyPanel,BorderLayout.EAST);
        add(centralnyPanel,BorderLayout.CENTER);
    }

    /** Sprawdzamy zrodlo zdarzenia, pobrane metoda getSource() - czy zostal nacisniety przycisk: GRAJ, ZASADY GRY lub WYJSCIE. Ustawiamy tez widocznosc kazdego z przyciskow
     * @param e to zdarzenie (wybor odpowiedniego przycisku)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo==bGraj) {
            if (onlineCzyOffline==null)
                onlineCzyOffline = new OnlineCzyOffline(this);
            onlineCzyOffline.setVisible(true);
        }
        else if (zrodlo==bZasadyGry) {
            if (zasadyGry==null)
                zasadyGry = new ZasadyGry(this);
            zasadyGry.setVisible(true);
        }
        else if (zrodlo==bRankingNajlepszych) {
            if (rankingNajlepszych==null) {
                try {
                    RankingNajlepszych.wczytajRanking();
                    rankingNajlepszych = new RankingNajlepszych(this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            rankingNajlepszych.setVisible(true);
        }
        else if (zrodlo==bWyjscie)
            dispose();
    }
}
