package modele;
import java.awt.Point;

public class Case {
    private int valeur;
    private Jeu j;

    public Case(int _valeur, Jeu _jeu) { //Constrcuteur de Case
        valeur = _valeur;
        j = _jeu;
    }

    public int getValeur() {
        return valeur;
    } //recupere la valeur d'une case

    public void setValeur(int valeur) {
        this.valeur = valeur;
    } //change la valeur d'une case

    public void deplacer(Direction d) { //deplace une case ne fonction de la direction
        if (d == Direction.gauche) {
            Point voisin = j.getVoisin(d, this); //on recupere les coordonnées du voisin de la case
            Case voisinC = j.getCase(voisin.x, voisin.y);
            Point actu = j.hm.get(this.toString()); //on recupere les coordonnées de la case actuel
            Case actuC = j.getCase(actu.x, actu.y);
            //System.out.println("Je suis le voisin gauche de : " + this.getValeur() + "mes coord en x et en y sont : " + voisin.x + "___ " + voisin.y);
            while ((actu.y != 0) && (voisinC.getValeur() == 0)) { //On deplace si case vide et pas en bordure
                voisinC.setValeur(actuC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x, actu.y - 1);
                voisin = j.getVoisin(d, voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);

                /*System.out.println("--------");
                System.out.println("--------Je suis " + actuC.getValeur() + " et mon nouveau voisin est: " + voisinC.getValeur() + "  " + voisin.x + "     " + voisin.y);
                System.out.println("--------");*/
            }
            while ((actu.y != 0) && (voisinC.getValeur() == actuC.getValeur())) { // on fusionne si meme case
                voisinC.setValeur(actuC.getValeur()+voisinC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x, actu.y - 1);
                voisin = j.getVoisin(d, voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);
            }

        }else if(d == Direction.droite) // meme chose pour la droite
        {
            Point voisin = j.getVoisin(d, this);
            Case voisinC = j.getCase(voisin.x, voisin.y);
            Point actu = j.hm.get(this.toString());
            Case actuC = j.getCase(actu.x,actu.y);
            //System.out.println("Je suis le voisin gauche de : " + this.getValeur() + "mes coord en x et en y sont : " + voisin.x + "___ " + voisin.y );
            while ((actu.y != j.getSize()-1) && (voisinC.getValeur() == 0)) {
                voisinC.setValeur(actuC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x, actu.y + 1);
                voisin = j.getVoisin(d,voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);

                /*System.out.println("--------");
                System.out.println("--------Je suis " + actuC.getValeur()+ " et mon nouveau voisin est: " + voisinC.getValeur() +"  "+ voisin.x +"     " + voisin.y);
                System.out.println("--------");*/
            }
            while ((actu.y != j.getSize()-1) && (voisinC.getValeur() == actuC.getValeur())) {
                voisinC.setValeur(actuC.getValeur()+voisinC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x, actu.y + 1);
                voisin = j.getVoisin(d,voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);
            }
        }
        else if(d == Direction.haut) //meme chose pour haut
        {
            Point voisin = j.getVoisin(d, this);
            Case voisinC = j.getCase(voisin.x, voisin.y);
            Point actu = j.hm.get(this.toString());
            Case actuC = j.getCase(actu.x,actu.y);
            //System.out.println("Je suis le voisin gauche de : " + this.getValeur() + "mes coord en x et en y sont : " + voisin.x + "___ " + voisin.y );
            while ((actu.x != 0) && (voisinC.getValeur() == 0)) {
                voisinC.setValeur(actuC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x-1, actu.y);
                voisin = j.getVoisin(d,voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);

                /*System.out.println("--------");
                System.out.println("--------Je suis " + actuC.getValeur()+ " et mon nouveau voisin est: " + voisinC.getValeur() +"  "+ voisin.x +"     " + voisin.y);
                System.out.println("--------");*/
            }
            while ((actu.x != 0) && (voisinC.getValeur() == actuC.getValeur())) {
                voisinC.setValeur(actuC.getValeur()+voisinC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x-1, actu.y);
                voisin = j.getVoisin(d,voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);
            }
        }
        else if(d == Direction.bas)
        {
            Point voisin = j.getVoisin(d, this);
            Case voisinC = j.getCase(voisin.x, voisin.y);
            Point actu = j.hm.get(this.toString());
            Case actuC = j.getCase(actu.x,actu.y);
            //System.out.println("Je suis le voisin gauche de : " + this.getValeur() + "mes coord en x et en y sont : " + voisin.x + "___ " + voisin.y );
            while ((actu.x != j.getSize()-1) && (voisinC.getValeur() == 0)) {
                voisinC.setValeur(actuC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x+1, actu.y);
                voisin = j.getVoisin(d,voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);

                /*System.out.println("--------");
                System.out.println("--------Je suis " + actuC.getValeur()+ " et mon nouveau voisin est: " + voisinC.getValeur() +"  "+ voisin.x +"     " + voisin.y);
                System.out.println("--------");*/
            }
            while ((actu.x != j.getSize()-1) && (voisinC.getValeur() == actuC.getValeur())) {
                voisinC.setValeur(actuC.getValeur()+voisinC.getValeur());
                actuC.setValeur(0);

                actu = new Point(actu.x+1, actu.y);
                voisin = j.getVoisin(d,voisinC);
                voisinC = j.getCase(voisin.x, voisin.y);
                actuC = j.getCase(actu.x, actu.y);
            }
        }



    }
}
