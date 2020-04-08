import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DodajGracza extends JDialog implements ActionListener {

    private JLabel lPodajNazweGracza;
    private JTextField tNick;
    private JButton bZatwierdz, bWstecz;
    private Gra gra;

    public DodajGracza() {
        super((Dialog) null,"",true);
        setSize(400,150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        lPodajNazweGracza = new JLabel("Podaj nazwę gracza: ", JLabel.LEFT);
        lPodajNazweGracza.setBounds(5,0,200,20);
        add(lPodajNazweGracza);

        tNick = new JTextField();
        tNick.setBounds(5,25,200,20);
        add(tNick);

        bZatwierdz = new JButton("Zatwierdź");
        bZatwierdz.setBounds(10,75,150,20);
        add(bZatwierdz);
        bZatwierdz.addActionListener(this);

        bWstecz = new JButton("Wstecz");
        bWstecz.setBounds(225,75,150,20);
        add(bWstecz);
        bWstecz.addActionListener(this);

        //setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo==bZatwierdz) {
            dispose();
            gra = new Gra();
            gra.setVisible(true);
        }
        else if (zrodlo==bWstecz) {
            dispose();
            OnlineCzyOffline onlineCzyOffline = new OnlineCzyOffline(null);
            onlineCzyOffline.setVisible(true);
        }
    }
}