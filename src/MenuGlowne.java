import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGlowne extends JFrame implements ActionListener {

    private OnlineCzyOffline onlineCzyOffline;
    private JButton bGraj, bZasadyGry, bRankingNajlepszych, bWyjscie;

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


    @Override
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if (zrodlo==bGraj) {
            if (onlineCzyOffline==null)
                onlineCzyOffline = new OnlineCzyOffline(this);
            onlineCzyOffline.setVisible(true);
        }
        else if (zrodlo==bZasadyGry) {

        }
        else if (zrodlo==bWyjscie)
            dispose();
    }
}