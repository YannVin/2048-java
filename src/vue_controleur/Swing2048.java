package vue_controleur;

import modele.Case;
import modele.Direction;
import modele.Jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Swing2048 extends JFrame implements Observer {
    private static final int PIXEL_PER_SQUARE = 250;
    // tableau de cases : i, j -> case graphique
    private JLabel[][] tabC;
    private Jeu jeu;
    private JLabel label_score;
    private JPanel contentPane = new JPanel(new BorderLayout());


    public Swing2048(Jeu _jeu) {
        jeu = _jeu;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(jeu.getSize() * PIXEL_PER_SQUARE, jeu.getSize() * PIXEL_PER_SQUARE);
        tabC = new JLabel[jeu.getSize()][jeu.getSize()];

        //Panel du score
        label_score = new JLabel();
        JPanel scorePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scorePane.add(label_score);

        //Panel jeu
        JPanel jeuPane = new JPanel(new GridLayout(jeu.getSize(), jeu.getSize()));



        contentPane.add(scorePane,BorderLayout.NORTH);


        for (int i = 0; i < jeu.getSize(); i++) {
            for (int j = 0; j < jeu.getSize(); j++) {
                Border border = BorderFactory.createLineBorder(Color.darkGray, 5);
                tabC[i][j] = new JLabel();
                tabC[i][j].setBorder(border);
                tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);


                jeuPane.add(tabC[i][j]);

            }
        }
        contentPane.add(jeuPane,BorderLayout.CENTER);
        setContentPane(contentPane);
        ajouterEcouteurClavier();
        rafraichir();

    }




    /**
     * Correspond à la fonctionnalité de Vue : affiche les données du modèle
     */
    private void rafraichir()  {

        SwingUtilities.invokeLater(new Runnable() { // demande au processus graphique de réaliser le traitement
            @Override
            public void run() {
                label_score.setText("Score : " +jeu.score()+"");
                for (int i = 0; i < jeu.getSize(); i++) {
                    for (int j = 0; j < jeu.getSize(); j++) {
                        Case c = jeu.getCase(i, j);
                        tabC[i][j].setForeground(Color.black);
                        switch(c.getValeur()){
                            case 0:
                                tabC[i][j].setBackground(Color.decode("#CCC0B2"));
                                tabC[i][j].setOpaque(true);
                                tabC[i][j].setText("");
                                break;

                            case 2:
                                tabC[i][j].setBackground(Color.decode("#EEE4DA"));
                                tabC[i][j].setOpaque(true);
                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 4:
                                tabC[i][j].setBackground(Color.decode("#ECE0C8"));
                                tabC[i][j].setOpaque(true);
                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 8:
                                tabC[i][j].setBackground(Color.decode("#F3B07B"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 16:
                                tabC[i][j].setBackground(Color.decode("#F59563"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 32:
                                tabC[i][j].setBackground(Color.decode("#F57D5E"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 64:
                                tabC[i][j].setBackground(Color.decode("#F65D3B"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 128:
                                tabC[i][j].setBackground(Color.decode("#EDCE71"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 256:
                                tabC[i][j].setBackground(Color.decode("#EDCC61"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 512:
                                tabC[i][j].setBackground(Color.decode("#ECC850"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 1024:
                                tabC[i][j].setBackground(Color.decode("#EDC53F"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            case 2048:
                                tabC[i][j].setBackground(Color.decode("#EFC12A"));
                                tabC[i][j].setOpaque(true);

                                tabC[i][j].setText(c.getValeur() + "");
                                break;

                            default:
                                System.out.println("PAS DE COULEUR");
                                break;
                        }
                    }
                }
                if(jeu.getDefaite())
                {
                    String[] options = {"Rejouer", "Arreter"};
                    //JOptionPane.showMessageDialog(rootPane,"Pas de chance vous avez perdu !" + " Votre Score est de : " + jeu.score()+ " ","Défaite", JOptionPane.INFORMATION_MESSAGE);
                    int x = JOptionPane.showOptionDialog(null, "Pas de chance vous avez perdu !" + " Votre Score est de : " + jeu.score()+ " ", "Défaite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    //int input = JOptionPane.showConfirmDialog(null, "Are you sure to close this window?", "Really Closing me?",JOptionPane.OK_CANCEL_OPTION);

                    if(options[x] == "Rejouer")
                    {
                        contentPane.setVisible(false);
                        jeu = new Jeu(4);
                        Swing2048 vue = new Swing2048(jeu);
                        jeu.addObserver(vue);
                        vue.setVisible(true);
                    }
                    else if(options[x] == "Arreter")
                    {
                        System.exit(0);
                    }
                }
            }
        });


    }

    /**
     * Correspond à la fonctionnalité de Contrôleur : écoute les évènements, et déclenche des traitements sur le modèle
     */
    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : jeu.action(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : jeu.action(Direction.droite); break;
                    case KeyEvent.VK_DOWN : jeu.action(Direction.bas); break;
                    case KeyEvent.VK_UP : jeu.action(Direction.haut); break;
                }
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichir();
    }
}