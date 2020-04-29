import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
        /**
         * Klasa odpowiadajaca za okno glowne i obsluge menu glownego
         */
public class MenuGlowne extends JFrame implements ActionListener {
/** Wybor gry: online czy offline*/
    private OnlineCzyOffline onlineCzyOffline;
    /** Przechowuje informacje o zasadach gry*/
    private ZasadyGry zasadyGry;
    /** Zmienna odpowiedzialna za przycisk: GRAJ*/
    private JButton bGraj;
    /** Zmienna odpowiedzialna za przycisk: ZASADY GRY*/
    private JButton bZasadyGry;
    /** Zmienna odpowiedzialna za przycisk: RANKING NAJLEPSZYCH*/
    private JButton bRankingNajlepszych;
    /** Zmienna odpowiedzialna za przycisk: WYJSCIE*/
    private JButton bWyjscie;

            /** Metoda, ktora odpowiada za ustawienie okna glownego. Dodatkowo następuje utworzenie kazdego z przyciskow, dodanie go oraz utworzenie w kazdym sluchacza.
             */
    public MenuGlowne() {
        super("Lunar Lander");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        setSize(500,400);
        setLayout(new GridLayout(4,1));

        bGraj = new JButton("GRAJ!");
        add(bGraj);
        bGraj.addActionListener(this);

        bZasadyGry = new JButton("ZASADY GRY");
        add(bZasadyGry);
        bZasadyGry.addActionListener(this);

        bRankingNajlepszych = new JButton("RANKING NAJLEPSZYCH");
        add(bRankingNajlepszych);
        bRankingNajlepszych.addActionListener(this);

        bWyjscie = new JButton("WYJŚCIE");
        add(bWyjscie);
        bWyjscie.addActionListener(this);

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
        else if (zrodlo==bWyjscie)
            dispose();
    }
}
