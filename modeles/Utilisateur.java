package modeles;

import java.io.Serializable;
import java.util.ArrayList;


public class Utilisateur implements Serializable {

    private String id;
    private String nom;

    private ArrayList<Livre> livresEmpruntes;

    public Utilisateur(String id, String nom) {
        this.id              = id;
        this.nom             = nom;
        this.livresEmpruntes = new ArrayList<>();
    }

    public String           getId()               { return id; }
    public String           getNom()              { return nom; }
    public ArrayList<Livre> getLivresEmpruntes()  { return livresEmpruntes; }

    public void ajouterLivre(Livre l) { livresEmpruntes.add(l); }
    public void retirerLivre(Livre l) { livresEmpruntes.remove(l); }

    @Override
    public String toString() {
        return nom + " (ID: " + id + ")  |  emprunts actifs: " + livresEmpruntes.size();
    }
}
