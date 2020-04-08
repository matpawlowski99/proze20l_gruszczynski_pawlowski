import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Gra extends JPanel {

    private JFrame frame;
    private JPanel panel;
    private JButton bPauza;
    private JLabel lStatki, lPaliwo;
    private Polygon planeta, ladowisko, statek;
    private Graphics2D g2d;
    private AffineTransform skaluj, macierz;
    private float skalujX,skalujY;

    public Gra() {
        try {
            PropertiesGry.wczytajPlansze(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Gra");
        frame.setSize(PropertiesGry.SZEROKOSC,PropertiesGry.WYSOKOSC);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,0,0,0),BorderFactory.createMatteBorder(5,5,5,5,new Color(94, 179, 228))));

        bPauza = new JButton("PAUZA");
        panel.add(bPauza);

        lStatki = new JLabel(" LICZBA STATKÓW: " + PropertiesGry.liczbaStatkow + " ", SwingConstants.CENTER);
        panel.add(lStatki);

        lPaliwo = new JLabel("PALIWO: " + PropertiesGry.paliwo + " % ",SwingConstants.CENTER);
        panel.add(lPaliwo);

        frame.add(panel,BorderLayout.EAST);
//        System.out.println(panel.getSize());

        frame.add(this);
        frame.setVisible(true);

        //setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        try {
//            PropertiesGry.wczytajPlansze(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        planeta = new Polygon(PropertiesGry.xWierzcholkiPlanety,PropertiesGry.yWierzcholkiPlanety,PropertiesGry.xWierzcholkiPlanety.length);
        ladowisko = new Polygon(PropertiesGry.xLadowiska,PropertiesGry.yLadowiska,PropertiesGry.xLadowiska.length);
        statek = new Polygon(PropertiesGry.xStartoweStatku,PropertiesGry.yStartoweStatku,PropertiesGry.xStartoweStatku.length);

        g2d = (Graphics2D) g;

        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,getWidth(),getHeight());

        skaluj = g2d.getTransform();
        macierz = new AffineTransform();
        skalujX = (1 + (getSize().width - PropertiesGry.SZEROKOSC) / (float)PropertiesGry.SZEROKOSC);
        skalujY = (1 + (getSize().height - PropertiesGry.WYSOKOSC) / (float)PropertiesGry.WYSOKOSC);
        macierz.scale(skalujX,skalujY);
        g2d.setTransform(macierz);

        g2d.drawPolygon(planeta);
        g2d.setColor(new Color(108, 108, 145));
        g2d.fill(planeta);

        g2d.drawPolygon(ladowisko);
        g2d.setColor(new Color(29,84,13));
        g2d.fill(ladowisko);

        g2d.drawPolygon(statek);
        g2d.setColor(Color.CYAN);
        g2d.fill(statek);

        g2d.setTransform(skaluj);
    }
}