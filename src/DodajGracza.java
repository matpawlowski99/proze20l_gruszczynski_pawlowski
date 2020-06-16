import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * Klasa odpowiadajaca za okno z nazwa gracza, obsluguje przyciski znajdujace sie w nim.
 */
public class DodajGracza extends Logika implements ActionListener {
    /** Zmienna przechowujaca numer planszy*/
    static int nrPlanszy;
    /** Zmienna przechowujaca liczbe statkow na poczatku rozgrywki*/
    static int pozostaleStatki = 3;
    /** Zmienna przechowujaca nazwe gracza*/
    static String nazwaGracza;
    /** Przycisk, ktory jest odpowiedzialny za dodanie gracza*/
    static JDialog dodajGracza;
    /** Przycisk odpowiedzialny za wyswietlenie informacji: Podaj nazwe gracza */
    private JLabel lPodajNazweGracza;
    /** Przycisk odpowiedzialny za utworzenie pola do wpisania nicku gracza*/
    private JTextField tNick;
    /** Zmienna odpowiedzialna za przycisk: ZATWIERDZ */
    private JButton bZatwierdz;
    /** Zmienna odpowiedzialna za przycisk: WSTECZ*/
    private JButton bWstecz;

    /** Metoda, ktora rysuje okienko z nazwa gracza do uzupelnienia oraz wyswietla przyciski: "Zatwierdz" oraz "Wstecz"*/
    public DodajGracza() {
        dodajGracza = new JDialog((Dialog) null,"",true);
        //super((Dialog) null,"",true);
        dodajGracza.setSize(400,150);
        dodajGracza.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dodajGracza.setLocationRelativeTo(null);
        dodajGracza.setResizable(false);
        dodajGracza.setLayout(null);
        //dodajGracza.setVisible(true);

        nrPlanszy = 1;

        lPodajNazweGracza = new JLabel("Podaj nazwę gracza: ", JLabel.LEFT);
        lPodajNazweGracza.setBounds(10,5,200,20);
        dodajGracza.add(lPodajNazweGracza);

        tNick = new JTextField();
        tNick.setBounds(10,30,200,20);
        dodajGracza.add(tNick);

        bZatwierdz = new JButton("Zatwierdź");
        bZatwierdz.setBounds(10,75,150,20);
        dodajGracza.add(bZatwierdz);
        bZatwierdz.addActionListener(this);

        bWstecz = new JButton("Wstecz");
        bWstecz.setBounds(225,75,150,20);
        dodajGracza.add(bWstecz);
        bWstecz.addActionListener(this);

        //dodajGracza.setVisible(true);
    }
    /** Sprawdzamy zrodlo zdarzenia, pobrane metoda getSource() - czy zostal nacisniety przycisk: Zatwierdz lub Wstecz. Ustawiamy tez widocznosc okienka: Dodaj Gracza
     * @param e zdarzenie (wybor odpowiedniego przycisku)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo==bZatwierdz) {
            if (tNick.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Nie wprowadzono nazwy gracza!", "Błąd",JOptionPane.ERROR_MESSAGE);
            else {
                nazwaGracza = tNick.getText();
                dodajGracza.dispose();
                try {
                    oknoGry(nrPlanszy);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        else if (zrodlo==bWstecz) {
            dodajGracza.dispose();
            OnlineCzyOffline onlineCzyOffline = new OnlineCzyOffline(null);
            onlineCzyOffline.setVisible(true);
        }
    }
}
