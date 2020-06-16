import javax.swing.JFrame;
/** Klasa odpowiadajaca za uruchomienie programu*/
public class LunarLander {

    /** Glowna metoda, to punkt wejscia kazdego programu Java, posiada jeden parametr:
     * @param args argument jako tablica ciagow.
     */
    public static void main(String[] args) {

        MenuGlowne menuGlowne = new MenuGlowne();
        menuGlowne.setLocationRelativeTo(null);
        menuGlowne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuGlowne.setVisible(true);
    }
}
