import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/** Klasa odpowiadajaca za sterowanie klawiatura, obsluguje jej przyciski.
 */
public class Klawiatura implements KeyListener {
/** Stworzenie zmiennej "rozgrywka", typu Gra - mamy dostep do tej klasy*/
    private Gra rozgrywka;

    /** Konstruktor jednoparametrowy klasy Klawiatura:
     * @param gra to parametr, ktory zostaje przypisany do obiektu rozgrywka
     */
    Klawiatura(Gra gra) {
        rozgrywka = gra;
    }

    /** Zdarzenie wcisniecia klawisza.
     * Sprawdzamy zrodlo zdarzenia, pobrane metoda getKeyCode() - ktory przycisk zostal nacisniety, wystepuje parametr:
     * @param e to zdarzenie (wybor odpowiedniego przycisku).
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int klawisz = e.getKeyCode();

        if (klawisz == KeyEvent.VK_LEFT) {
            rozgrywka.xPredkosc -= 4;
            rozgrywka.paliwo -= 1;
        }
        if (klawisz == KeyEvent.VK_RIGHT) {
            rozgrywka.xPredkosc += 4;
            rozgrywka.paliwo -= 1;
        }
        if (klawisz == KeyEvent.VK_UP && rozgrywka.yPredkosc > -8) {
            rozgrywka.yPredkosc -= 2;
            rozgrywka.paliwo -= 1;
        }
    }

    /** Zdarzenie zwolnienia klawisza.
     * Sprawdzamy zrodlo zdarzenia, pobrane metoda getKeyCode() - czy strzalka w gore juz nie jest wcisnieta, wystepuje parametr:
     * @param e to zdarzenie (wybor odpowiedniego przycisku).
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int klawisz = e.getKeyCode();

        if (klawisz == KeyEvent.VK_UP)
            rozgrywka.yPredkosc -= 1;
    }

/** Zdarzenie typu kluczowego - wpisywanie znaku Unicode*/
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
