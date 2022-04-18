package modele;

import java.util.Observable;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;


public class Jeu extends Observable {

    private Case[][] tabCases;
    private static Random rnd = new Random(4);

    public Map <String, Point> hm = new HashMap<String,Point>();
    public Jeu(int size) {

        tabCases = new Case[size][size];
        rnd();
        inithm(hm);
    }

    public int getSize() {
        return tabCases.length;
    }

    public Case getCase(int i, int j) {
        return tabCases[i][j];
    }




    public void rnd() {
        new Thread() { // permet de libérer le processus graphique ou de la console
            public void run() {
                int r;

                for (int i = 0; i < tabCases.length; i++) {
                    for (int j = 0; j < tabCases.length; j++) {
                        r = rnd.nextInt(3);

                        switch (r) {
                            case 0:
                                tabCases[i][j] = new Case(0,Jeu.this);
                                break;
                            case 1:
                                tabCases[i][j] = new Case(2,Jeu.this);
                                break;
                            case 2:
                                tabCases[i][j] = new Case(4,Jeu.this);
                                break;
                        }
                    }
                }
            }

        }.start();


        setChanged();
        notifyObservers();


    }



    public void inithm(Map hm) {
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                Point actu = new Point(i,j);
                hm.put(tabCases[i][j].toString(),actu);
            }
        }
    }

    public Point getVoisin(Direction d,Case c){
        Point voisin = new Point (0,0);
        if(d == Direction.gauche)
        {
            Point actu = hm.get(c.toString());
            if(actu.y != 0)
                voisin = new Point(actu.x,actu.y-1);
            else
            {
                voisin = new Point(actu.x, actu.y);
            }
        }
        /*else if(d == Direction.droite)
        {
            Point actu = hm.get(c.toString());
            voisin = new Point(actu.x+1,actu.y);
        }
        else if(d == Direction.haut)
        {
            Point actu = hm.get(c.toString());
            voisin = new Point(actu.x,actu.y-1);
        }
        else if(d == Direction.bas)
        {
            Point actu = hm.get(c.toString());
            voisin = new Point(actu.x,actu.y+1);
        }*/
        return voisin;
    }


    public void action(Direction d) {

        if (d == Direction.gauche) {
            for (int i = 0; i < tabCases.length; i++) {

                for (int j = 0; j < tabCases.length; j++) {
                    tabCases[i][j].deplacer(d);
                    //System.out.println(d);
                    //System.out.println("La case qui est affiché est i:" + i + " et la coord j est :" + j +" la valeur est :" + this.getCase(i,j).getValeur());
                    //System.out.print(this.getCase(i,j).getValeur() + "  ");
                }
                //System.out.println(" ");
            }
        } /*else if (d == Direction.droite) {
            for (int i = tabCases.length; i > 0; i--) {
                for (int j = 0; j < tabCases.length; j++) {
                    tabCases[i][j].deplacer(d);
                }

            }
        }
        else if (d == Direction.haut)
        {
            for (int j = 0; j < tabCases.length; j++) {
                for (int i = 0; i < tabCases.length; i++) {
                    tabCases[i][j].deplacer(d);
                }

            }
        }
        else if (d == Direction.bas)
        {
            for (int j = tabCases.length; j > 0; j--) {
                for (int i = 0; i < tabCases.length; i++) {
                    tabCases[i][j].deplacer(d);
                }
            }
        }*/
        setChanged();
        notifyObservers();
    }
}
