import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.KeyStroke;
import java.util.Timer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import static java.lang.StrictMath.abs;

public abstract class Logika implements ActionListener {

    JFrame ramkaGry, ramkaZaliczenia, ramkaNiezaliczenia, ramkaKoncaGry;
    JPanel panelGry, panelKoncaGry;
    JButton bPauza, bWspaniale, bEhh, bMenuGlowne;
    JLabel lStatki, lPaliwo, lPredkoscX, lPredkoscY, lZaliczenia, lNiezaliczenia, lGraZakonczona, lGracz, lWynik;
    Timer licznikAnimacji;
    int ostatecznyWynik;
    //int pozostaleStatki = 3;

    Klawiatura klawiatura;

    public void oknoGry(int numerPlanszy) throws IOException {
        ramkaGry = new JFrame("Gra");
        ramkaGry.setSize(PropertiesGry.SZEROKOSC,PropertiesGry.WYSOKOSC);
        ramkaGry.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ramkaGry.setLocationRelativeTo(null);
        ramkaGry.setLayout(new BorderLayout());

        licznikAnimacji = new Timer();

        panelGry = new JPanel();
        panelGry.setLayout(new GridLayout(1,5));
        panelGry.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,0,0,0),BorderFactory.createMatteBorder(5,5,5,5,new Color(94, 179, 228))));

        lPredkoscX = new JLabel("", SwingConstants.CENTER);
        panelGry.add(lPredkoscX);

        lPredkoscY = new JLabel("", SwingConstants.CENTER);
        panelGry.add(lPredkoscY);

        bPauza = new JButton("PAUZA");
        bPauza.setForeground(Color.BLACK);
        panelGry.add(bPauza);

        lStatki = new JLabel("", SwingConstants.CENTER);
        lStatki.setForeground(Color.BLACK);
        panelGry.add(lStatki);

        lPaliwo = new JLabel("", SwingConstants.CENTER);
        panelGry.add(lPaliwo);

        ramkaGry.add(panelGry,BorderLayout.SOUTH);

        ramkaGry.setVisible(true);

        Gra gra = new Gra(numerPlanszy);
        bPauza.addActionListener(event -> zapauzuj(gra));

        licznikAnimacji.scheduleAtFixedRate(gra.new Animacja(), 100, 15);

        ramkaGry.add(gra);

        klawiatura = new Klawiatura(gra);
        ramkaGry.addKeyListener(klawiatura);

        ramkaGry.requestFocus();
        //ramka.setVisible(true);

        //setBackground(Color.BLACK);

        new Thread(() -> {
            while (!gra.czyRozbito && !gra.czyWyladowano) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lStatki.setText("LICZBA STATKÓW: " + DodajGracza.pozostaleStatki);

                lPaliwo.setText("PALIWO: " + gra.paliwo + "%");
                if (gra.paliwo >= 50)
                    lPaliwo.setForeground(Color.BLACK);
                else if (gra.paliwo >= 25 && gra.paliwo < 50)
                    lPaliwo.setForeground(new Color(255, 145,0));
                else
                    lPaliwo.setForeground(Color.RED);
                if (gra.paliwo == 0) {
                    resetujLicznikAnimacji();
                    if (DodajGracza.pozostaleStatki > 1) {
                        nieZaliczPlanszy(gra);
                        ramkaGry.dispose();
                    }
                    else {
                        koniecGry(DodajGracza.nazwaGracza, ostatecznyWynik);
                        ramkaGry.dispose();
                    }
                    return;
                }

                lPredkoscX.setText("PRĘDKOŚĆ X: " + String.format("%.1f", abs(gra.xPredkosc)));
                if (abs(gra.xPredkosc) > PropertiesGry.xPredkoscLadowania)
                    lPredkoscX.setForeground(Color.RED);
                else
                    lPredkoscX.setForeground(Color.BLACK);

                lPredkoscY.setText("PRĘDKOŚĆ Y: " + String.format("%.1f", abs(gra.yPredkosc)));
                if (abs(gra.yPredkosc) > PropertiesGry.yPredkoscLadowania)
                    lPredkoscY.setForeground(Color.RED);
                else
                    lPredkoscY.setForeground(Color.BLACK);
            }
            try {
                resetujLicznikAnimacji();
                if (gra.czyWyladowano) {
                    ostatecznyWynik += gra.wynik;
                    ostatecznyWynik += DodajGracza.pozostaleStatki * 10;
                    ostatecznyWynik += gra.paliwo;
                    ostatecznyWynik += gra.punktyZaPlansze;
                    zaliczPlansze();
                    ramkaGry.dispose();
                    System.out.println(ostatecznyWynik);
                }
                if ((gra.czyRozbito || gra.paliwo <= 0) && DodajGracza.pozostaleStatki > 1) {
                    nieZaliczPlanszy(gra);
                    //gra.liczbaStatkow--;
                    ramkaGry.dispose();
                }
                if ((gra.czyRozbito || gra.paliwo == 0) && DodajGracza.pozostaleStatki <= 1) {
                    koniecGry(DodajGracza.nazwaGracza, ostatecznyWynik);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void resetujLicznikAnimacji() {
        licznikAnimacji.cancel();
        licznikAnimacji.purge();
    }

    public void zapauzuj(Gra gra) {
        if (!gra.czyZapauzowano) {
            gra.czyZapauzowano();
            gra.czyZapauzowano = true;
        }
        else {
            gra.czyWznowiono();
            gra.czyZapauzowano = false;
            ramkaGry.requestFocus();
        }
    }

    public void zaliczPlansze() {
        ramkaZaliczenia = new JFrame("");
        ramkaZaliczenia.setSize(300,130);
        ramkaZaliczenia.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ramkaZaliczenia.setLocationRelativeTo(null);
        ramkaZaliczenia.setLayout(null);
        ramkaZaliczenia.setResizable(false);

        lZaliczenia = new JLabel("Gratulacje - plansza zaliczona!",SwingConstants.CENTER);
        lZaliczenia.setBounds(40,20,200,15);
        ramkaZaliczenia.add(lZaliczenia);

        bWspaniale = new JButton("Wspaniale! Przejdźmy dalej!");
        bWspaniale.setBounds(45,50,198,30);
        ramkaZaliczenia.add(bWspaniale);

        bWspaniale.addActionListener(event -> {
            ramkaZaliczenia.dispose();
            try {
                nastepnaPlansza();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ramkaZaliczenia.setVisible(true);
    }

    public void nieZaliczPlanszy(Gra gra) {
        ramkaNiezaliczenia = new JFrame("");
        ramkaNiezaliczenia.setSize(300,130);
        ramkaNiezaliczenia.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ramkaNiezaliczenia.setLocationRelativeTo(null);
        ramkaNiezaliczenia.setLayout(null);
        ramkaNiezaliczenia.setResizable(false);

        if (gra.paliwo == 0)
            lNiezaliczenia = new JLabel("Niestety - koniec paliwa!",SwingConstants.CENTER);
        else
            lNiezaliczenia = new JLabel("Niestety - rozbiłeś się!",SwingConstants.CENTER);
        lNiezaliczenia.setBounds(40,20,200,15);
        ramkaNiezaliczenia.add(lNiezaliczenia);

        bEhh = new JButton("Ehh... Spróbujmy jeszcze raz");
        bEhh.setBounds(45,50,198,30);
        ramkaNiezaliczenia.add(bEhh);

        bEhh.addActionListener(event -> {
            ramkaNiezaliczenia.dispose();
            try {
                oknoGry(DodajGracza.nrPlanszy);
                DodajGracza.pozostaleStatki--;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ramkaNiezaliczenia.setVisible(true);
    }

    public void koniecGry(String gracz, int wynik) {
        ramkaGry.dispose();

        ramkaKoncaGry = new JFrame("KONIEC GRY");
        ramkaKoncaGry.setSize(300,120);
        ramkaKoncaGry.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ramkaKoncaGry.setLocationRelativeTo(null);
        ramkaKoncaGry.setLayout(new BorderLayout());
        ramkaKoncaGry.setResizable(false);

        panelKoncaGry = new JPanel();
        panelKoncaGry.setLayout(new GridLayout(1,2));

        lGraZakonczona = new JLabel("Gra zakończona", SwingConstants.CENTER);
        ramkaKoncaGry.add(lGraZakonczona, BorderLayout.NORTH);

        lGracz = new JLabel("Gracz: " + gracz, SwingConstants.CENTER);
        panelKoncaGry.add(lGracz);

        lWynik = new JLabel("Wynik: " + wynik, SwingConstants.CENTER);
        panelKoncaGry.add(lWynik);

        bMenuGlowne = new JButton("Powrót do menu głównego");
        bMenuGlowne.addActionListener(event -> ramkaKoncaGry.dispose());

        ramkaKoncaGry.add(bMenuGlowne, BorderLayout.SOUTH);

        ramkaKoncaGry.add(panelKoncaGry);

        ostatecznyWynik = 0;
        DodajGracza.pozostaleStatki = 3;
        DodajGracza.nrPlanszy = 1;

        ramkaKoncaGry.setVisible(true);
    }

    public void nastepnaPlansza() throws IOException {
        resetujLicznikAnimacji();
        if (DodajGracza.nrPlanszy < PropertiesGry.liczbaPoziomow) {
            DodajGracza.nrPlanszy++;
            if (DodajGracza.pozostaleStatki < 3)
                DodajGracza.pozostaleStatki = 3;
            oknoGry(DodajGracza.nrPlanszy);
        }
        else
            koniecGry(DodajGracza.nazwaGracza, ostatecznyWynik);
    }
}