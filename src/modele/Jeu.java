package modele;

import java.util.Observable;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;


public class Jeu extends Observable {

    private Case[][] tabCases;
    private static Random rnd = new Random(4);

    private boolean defaite = false;
    public Map <String, Point> hm = new HashMap<String,Point>();
    public Jeu(int size) { //constructeur de jeu
        tabCases = new Case[size][size];
        init();
        random();
        inithm(hm);
    }

    public int getSize() {
        return tabCases.length;
    } //recupere la taille

    public Case getCase(int i, int j) {
        return tabCases[i][j];
    } //recupere une case




    public void rnd() { //fonction rand de base
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

    public void init() { //initialise le la grille
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                tabCases[i][j] = new Case(0, Jeu.this);
            }
        }
    }

    //procédure qui affiche un nombre aléatoire entre 0,2,4 dans les cases vides
    public void random() {
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {

                if(tabCases[i][j].getValeur() == 0)
                {
                    int r= rnd.nextInt(70); //tire un nombre aléatoire entre 0 et 69
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
                    if((r >=0 )&&(r<=10)) //si ce nombre est entre 0 et 10 on attribut la valeur 2 à la case
                        tabCases[i][j].setValeur(2);
                    else if((r >=11 )&&(r<=20)) //si ce nombre est entre 11 et 20 on attribut la valeur 4 à la case
                        tabCases[i][j].setValeur(4);
                    else //sinon on ne met rien
                        tabCases[i][j].setValeur(0);

                }
            }
        }
    }

    //procédure d'initialisation de la hashmap
    public void inithm(Map hm)
    {
        for (int i = 0; i < tabCases.length; i++) {
            for (int j = 0; j < tabCases.length; j++) {
                Point actu = new Point(i,j);
                //if(tabCases[i][j] != null)
                    hm.put(tabCases[i][j].toString(),actu);
            }
        }
    }

    //Fonction qui retourne le voisin de la case en fonction de la direction
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

    //Fonction qui va test si le jeu est perdu ou non
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

    //Accesseur de defaite
    public boolean getDefaite() {
        return this.defaite;
    }

    //Fonction qui calcule et retourne le score (case la plus grande)
    public int score(){
        int score = 0;
        for(int i=0 ; i<this.tabCases.length ; i++){
            for(int j=0 ; j<this.tabCases.length ; j++){
                if(score<this.tabCases[i][j].getValeur())
                {
                    score = this.tabCases[i][j].getValeur();
                }
            }
        }
        return score;
    }

    public void action(Direction d) { //fonction qui applique l'action (deplacer ou fusionner) a toute les cases de la grille

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
        if(testtab) //si grille pleine on teste si on a perdu
            defaite = TestDefaite();
        if (defaite)
            System.out.println("Défaite!!!");
    }
}
