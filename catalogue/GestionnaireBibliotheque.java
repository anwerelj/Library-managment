package catalogue;

import exceptions.LivreIndisponibleException;
import exceptions.LivreIntrouvableException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import modeles.Document;
import modeles.Livre;
import modeles.Magazine;
import modeles.Utilisateur;


public class GestionnaireBibliotheque implements Serializable {

    
    private Repertoire<Document> documents = new Repertoire<>();

   
    private HashMap<String, Utilisateur> utilisateurs = new HashMap<>();

    
    private HashSet<String> idsEmpruntes = new HashSet<>();

   
    public void ajouterDocument(Document doc) {
        documents.ajouter(doc); 
    }

    public void supprimerLivre(String id) throws LivreIntrouvableException {
        Livre l = trouverLivre(id);
        documents.supprimer(l); 
        idsEmpruntes.remove(id);
        System.out.println("  [OK] Livre supprime : " + l.getTitre());
    }

 
    public Livre trouverLivre(String id) throws LivreIntrouvableException {
        for (Document d : documents.getTous()) { 
            if (d instanceof Livre && d.getId().equalsIgnoreCase(id)) {
                return (Livre) d;
            }
        }
        throw new LivreIntrouvableException("Aucun livre avec l'ID [" + id + "].");
    }

   
    public ArrayList<Livre> rechercherParTitre(String motCle) {
        ArrayList<Livre> resultat = new ArrayList<>();
        for (Document d : documents.getTous()) {
            if (d instanceof Livre && d.getTitre().toLowerCase().contains(motCle.toLowerCase())) {
                resultat.add((Livre) d);
            }
        }
        return resultat;
    }

    
    public ArrayList<Livre> rechercherParAuteur(String motCle) {
        ArrayList<Livre> resultat = new ArrayList<>();
        for (Document d : documents.getTous()) {
            if (d instanceof Livre) {
                Livre l = (Livre) d;
                if (l.getAuteur().toLowerCase().contains(motCle.toLowerCase())) {
                    resultat.add(l);
                }
            }
        }
        return resultat;
    }

    public void ajouterUtilisateur(Utilisateur u) {
        utilisateurs.put(u.getId(), u);
    }

    public Utilisateur trouverUtilisateur(String id) {
        return utilisateurs.get(id);
    }

    public HashMap<String, Utilisateur> getTousLesUtilisateurs() {
        return utilisateurs;
    }

   

    public void emprunterLivre(String idUser, String idLivre)
            throws LivreIntrouvableException, LivreIndisponibleException {

        Utilisateur u = trouverUtilisateur(idUser);
        if (u == null) {
            System.out.println("  [!!] Utilisateur introuvable (ID: " + idUser + ").");
            return;
        }

        Livre l = trouverLivre(idLivre);  
        l.emprunter();                    

        idsEmpruntes.add(idLivre);       
        u.ajouterLivre(l);               
        System.out.println("  [OK] " + u.getNom() + " a emprunte : " + l.getTitre());
    }

    public void retournerLivre(String idUser, String idLivre)
            throws LivreIntrouvableException {

        Utilisateur u = trouverUtilisateur(idUser);
        if (u == null) {
            System.out.println("  [!!] Utilisateur introuvable (ID: " + idUser + ").");
            return;
        }

        Livre livreARetourner = null;
        for (Livre l : u.getLivresEmpruntes()) {
            if (l.getId().equalsIgnoreCase(idLivre)) {
                livreARetourner = l;
                break;
            }
        }

        if (livreARetourner == null) {
            throw new LivreIntrouvableException(
                "Vous n'avez pas emprunte le livre [" + idLivre + "].");
        }

        livreARetourner.retourner();
        idsEmpruntes.remove(idLivre);     
        u.retirerLivre(livreARetourner);  
        System.out.println("  [OK] Livre retourne : " + livreARetourner.getTitre());
    }

    
    public ArrayList<Document> getTousLesDocuments() {
        return new ArrayList<>(documents.getTous());
    }

    public HashSet<String> getIdsEmpruntes()    { return idsEmpruntes; }
    public int             getNombreDocuments() { return documents.taille(); }

    public int getNombreLivres() {
        int n = 0;
        for (Document d : documents.getTous()) if (d instanceof Livre)    n++;
        return n;
    }

    public int getNombreMagazines() {
        int n = 0;
        for (Document d : documents.getTous()) if (d instanceof Magazine) n++;
        return n;
    }
}
