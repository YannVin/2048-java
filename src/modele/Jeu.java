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
        init();
        random();
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

    public void init() {
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                tabCases[i][j] = new Case(0, Jeu.this);
            }
        }
    }

    public void random() {
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {

                if(tabCases[i][j].getValeur() == 0)
                {
                    int r= rnd.nextInt(70);
                    /*switch (r) {
                        case 0:
                            tabCases[i][j].setValeur(0);
                            break;
                        case 1:
                            tabCases[i][j].setValeur(2);
                            break;
                        case 2:
                            tabCases[i][j].setValeur(4);
                            break;
                    }*/
                    if((r >=0 )&&(r<=10))
                        tabCases[i][j].setValeur(2);
                    else if((r >=11 )&&(r<=20))
                        tabCases[i][j].setValeur(4);
                    else
                        tabCases[i][j].setValeur(0);

                }
            }
        }
    }


    public void inithm(Map hm) {
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                Point actu = new Point(i,j);
                //if(tabCases[i][j] != null)
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
        else if(d == Direction.droite)
        {
            Point actu = hm.get(c.toString());
            if(actu.y != tabCases.length-1)
            {
                voisin = new Point(actu.x, actu.y+1);
            }
            else
            {
                voisin = new Point(actu.x, actu.y);
            }
        }
        else if(d == Direction.haut)
        {
            Point actu = hm.get(c.toString());
            if(actu.x != 0)
            {
                voisin = new Point(actu.x-1, actu.y);
            }
            else
            {
                voisin = new Point(actu.x, actu.y);
            }
        }
        else if(d == Direction.bas)
        {
            Point actu = hm.get(c.toString());
            if(actu.x != tabCases.length-1)
            {
                voisin = new Point(actu.x+1, actu.y);
            }
            else
            {
                voisin = new Point(actu.x, actu.y);
            }

        }
        return voisin;
    }

   /* public boolean TestDefaite()
    {
        boolean test = false;
        Case [][] tabtest = new Case[getSize()][getSize()];
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                tabtest[i][j] = new Case(0, Jeu.this);
                tabtest[i][j].setValeur(tabCases[i][j].getValeur());
                tabtest[i][j].deplacer(Direction.gauche);
                tabtest[i][j].deplacer(Direction.droite);
                tabtest[i][j].deplacer(Direction.haut);
                tabtest[i][j].deplacer(Direction.bas);
                if( tabtest[i][j] != tabCases[i][j])
                {
                    test = false;
                }
                else
                {
                    test = true;
                }
            }
        }
        return test;
    }*/

    public boolean TestDefaite() {
        boolean test = true;
        for (int i = 0; i < this.tabCases.length; i++) {
            for (int j = 0; j < this.tabCases.length; j++) {
                if ((i > 0) && (this.tabCases[i][j].getValeur() == this.tabCases[i - 1][j].getValeur())) {
                    test=false;
                }
                if ((j > 0)
                        && (this.tabCases[i][j].getValeur() == this.tabCases[i][j - 1].getValeur())) {
                    test=false;
                }
                if ((i < this.tabCases.length - 1)
                        && (this.tabCases[i][j].getValeur() == this.tabCases[i + 1][j].getValeur())) {
                    test=false;
                }
                if ((j < this.tabCases.length - 1)
                        && (this.tabCases[i][j].getValeur() == this.tabCases[i][j + 1].getValeur())) {
                    test=false;
                }
                if (this.tabCases[i][j].getValeur() == 0) {
                    test=false;
                }
            }
        }
        return test;
    }

    public int score(){
        int score = 0;
        for(int i=0 ; i<this.tabCases.length ; i++){
            for(int j=0 ; j<this.tabCases.length ; j++){
                if(score<this.tabCases[i][j].getValeur()){
                    score = this.tabCases[i][j].getValeur();
                }
            }
        }
        return score;
    }

    public void action(Direction d) {

        if (d == Direction.gauche) {
            for (int i = 0; i < tabCases.length; i++) {
                for (int j = 0; j < tabCases.length; j++) {
                    tabCases[i][j].deplacer(d);
                }
            }
        } else if (d == Direction.droite) {
            for (int i = 0; i < tabCases.length; i++) {
                for (int j = tabCases.length - 1; j >= 0; j--) {
                    tabCases[i][j].deplacer(d);
                }

            }
        } else if (d == Direction.haut) {
            for (int j = 0; j < tabCases.length; j++) {
                for (int i = 0; i < tabCases.length; i++) {
                    tabCases[i][j].deplacer(d);
                }

            }
        } else if (d == Direction.bas) {
            for (int j = 0; j < tabCases.length; j++) {
                for (int i = tabCases.length - 1; i >= 0; i--) {
                    tabCases[i][j].deplacer(d);
                }
            }
        }
        random();
        setChanged();
        notifyObservers();

        boolean testtab=true;
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                if (tabCases[i][j].getValeur() == 0)
                    testtab = false;
            }
        }
        boolean defaite = false;
        if(testtab)
            defaite = TestDefaite();
        if (defaite)
            System.out.println("Tu as perdu");
    }
}
