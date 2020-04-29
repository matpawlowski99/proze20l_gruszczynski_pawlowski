import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Klawiatura implements KeyListener {

    private Gra rozgrywka;

    Klawiatura(Gra gra) {
        rozgrywka = gra;
    }

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

    @Override
    public void keyReleased(KeyEvent e) {
        int klawisz = e.getKeyCode();

        if (klawisz == KeyEvent.VK_UP)
            rozgrywka.yPredkosc -= 1;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}