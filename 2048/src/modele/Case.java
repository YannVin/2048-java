package modele;
import java.awt.Point;

public class Case {
    private int valeur;
    private Jeu j;

    public Case(int _valeur,Jeu _jeu)
    {
        valeur = _valeur;
        j = _jeu;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public void deplacer(Direction d)
    {
        if(d == Direction.gauche)
        {
            Point voisin = j.getVoisin(d,this);
            Case voisinC = j.getCase(voisin.x, voisin.y);
            while((voisin.x!=0) && (voisinC.getValeur()==0)){
                voisinC.setValeur(this.getValeur());
                voisin = j.getVoisin(d,voisinC);
            }
        }
        if(d == Direction.droite)
        {
            Point voisin = j.getVoisin(d,this);
        }
        if(d == Direction.haut)
        {
            Point voisin = j.getVoisin(d,this);
        }
        if(d == Direction.bas)
        {
            Point voisin = j.getVoisin(d,this);
        }

    }

}
