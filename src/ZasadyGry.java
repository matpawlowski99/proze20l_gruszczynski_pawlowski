import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileReader;
/**
 * Klasa odpowiadajaca za okno ZASADY GRY
 */
public class ZasadyGry extends JDialog {
    /** Komponent wypelniajacy ramke (frame), mozna do niego dodawac elementy - pomaga w ich rozmieszczaniu)*/
    private JPanel panel;
    /** Pole, ktore przechowuje tekst*/
    private JTextPane textPane;
    /** Czcionka - forma graficzna tekstu*/
    private Font font;
    /** Przewijanie tekstu przy pomocy bocznego suwaka*/
    private JScrollPane scrollPane;

    /** Metoda rysujaca okienko ZASADY GRY, posiada jeden parametr:
     * @param owner jest komponentem modalnym na rzecz komponentu public ZasadyGry().
     */
    public ZasadyGry(JFrame owner) {
        super(owner,"Zasady gry", true);
        panel = new JPanel();
        textPane = new JTextPane();
        wyswietlZasady(textPane);
        font = new Font("", Font.PLAIN, 15);
        textPane.setFont(font);
        textPane.setForeground(Color.WHITE);
        textPane.setBackground(new Color(0, 7, 72));
        scrollPane = new JScrollPane(textPane);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800,408);
        setLocationRelativeTo(null);
        textPane.setEditable(false);
    }

    /** Metoda wyswietlajaca zasady gry, posiada jeden parametr:
     * @param textPane to pole, ktore przechowuje tekst.
     */
    private static void wyswietlZasady(JTextPane textPane) {
        try {
            File plik = new File("zasady.txt");
            FileReader fileReader = new FileReader(plik);
            while (fileReader.read() != -1) {
                textPane.read(fileReader, null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
