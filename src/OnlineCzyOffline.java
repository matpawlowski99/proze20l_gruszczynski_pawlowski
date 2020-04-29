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

public class OnlineCzyOffline extends JDialog implements ActionListener {

    private DodajGracza dodajGracza;
    private JPanel panel;
    private JButton bOnline, bOffline;
    private JLabel lWybierzTryb;

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