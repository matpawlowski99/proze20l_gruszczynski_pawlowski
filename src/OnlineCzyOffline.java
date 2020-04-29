import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
        /** Klasa odpowiadajaca za okno Online/Offline, obsluguje przyciski znajdujace sie w nim.
         */
public class OnlineCzyOffline extends JDialog implements ActionListener {
/** Zmienna odpowiedzialna za dodanie gracza*/
    private DodajGracza dodajGracza;
    /** Przechowuje informacje o okienku*/
    private JPanel panel;
    /** Przechowuje informacje o wyborze gry online*/
    private JButton bOnline;
    /** Przechowuje informacje o wyborze gry offline*/
    private JButton bOffline;
    /** Przechowuje informacje o wyborze trybu gry*/
    private JLabel lWybierzTryb;

            /** Metoda rysujaca okienko online/offline, posiada jeden parametr:
             * @param owner jest komponentem modalnym na rzecz komponentu public OnlineCzyOffline().
             */
    public OnlineCzyOffline(JFrame owner) {
        super(owner, "", true);
        setSize(250,125);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(new BorderLayout(10,15));
        add(panel);

        lWybierzTryb = new JLabel("Wybierz tryb rozgrywki", SwingConstants.CENTER);
        panel.add(lWybierzTryb,BorderLayout.NORTH);

        bOnline = new JButton("Graj online");
        panel.add(bOnline,BorderLayout.WEST);
        bOnline.addActionListener(this);

        bOffline = new JButton("Graj offline");
        panel.add(bOffline,BorderLayout.EAST);
        bOffline.addActionListener(this);

        //setVisible(true);
    }
            /** Sprawdzamy zrodlo zdarzenia, pobrane metoda getSource() - czy zostal nacisniety przycisk: Offline czy Online. Ustawiamy tez widocznosc okienka: Offline
             * @param e zdarzenie (wybor odpowiedniego przycisku)
             */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo==bOffline) {
            this.dispose();
            if (dodajGracza==null)
                dodajGracza = new DodajGracza();
            DodajGracza.dodajGracza.setVisible(true);
        }

    }
}
