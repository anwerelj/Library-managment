package menus;

import catalogue.GestionnaireBibliotheque;
import exceptions.LivreIntrouvableException;
import modeles.Document;
import modeles.Livre;
import modeles.Magazine;
import modeles.Utilisateur;

import java.util.ArrayList;


public class MenuAdmin {

    private final GestionnaireBibliotheque bib;

    public MenuAdmin(GestionnaireBibliotheque bib) {
        this.bib = bib;
    }

    public void afficher() {
        boolean actif = true;
        while (actif) {
            Console.ligne();
            System.out.println("  ESPACE ADMINISTRATEUR");
            System.out.println("  " + bib.getNombreLivres() + " livres  |  "
                + bib.getNombreMagazines() + " magazines");
            Console.ligne();
            System.out.println("  1.  Voir tous les documents");
            System.out.println("  2.  Rechercher par titre");
            System.out.println("  3.  Rechercher par auteur");
            System.out.println("  4.  Ajouter un livre");
            System.out.println("  5.  Ajouter un magazine");
            System.out.println("  6.  Supprimer un livre");
            System.out.println("  7.  Voir tous les utilisateurs");
            System.out.println("  8.  Ajouter un utilisateur");
            System.out.println("  9.  Voir les emprunts actifs");
            System.out.println("  10. Retour");
            Console.ligne();

            switch (Console.lireChoix("Votre choix", 1, 10)) {
                case 1:  voirTous();           break;
                case 2:  rechercherTitre();    break;
                case 3:  rechercherAuteur();   break;
                case 4:  ajouterLivre();       break;
                case 5:  ajouterMagazine();    break;
                case 6:  supprimerLivre();     break;
                case 7:  voirUtilisateurs();   break;
                case 8:  ajouterUtilisateur(); break;
                case 9:  voirEmpruntsActifs(); break;
                case 10: actif = false;        break;
            }
        }
    }



    private void voirTous() {
        Console.sousTitre("Tous les documents (" + bib.getNombreDocuments() + ")");
        if (bib.getTousLesDocuments().isEmpty()) {
            System.out.println("  Aucun document enregistre.");
            return;
        }
        for (Document d : bib.getTousLesDocuments()) {
            d.afficherDetails();
        }
    }

    private void rechercherTitre() {
        Console.sousTitre("Recherche par titre");
        String motCle = Console.lireTexte("Mot-cle");
        ArrayList<Livre> res = bib.rechercherParTitre(motCle);
        System.out.println("  " + res.size() + " resultat(s) :");
        for (Livre l : res) l.afficherDetails();
        if (res.isEmpty()) System.out.println("  Aucun livre trouve.");
    }

    private void rechercherAuteur() {
        Console.sousTitre("Recherche par auteur");
        String motCle = Console.lireTexte("Nom de l'auteur");
        ArrayList<Livre> res = bib.rechercherParAuteur(motCle);
        System.out.println("  " + res.size() + " livre(s) trouve(s) :");
        for (Livre l : res) l.afficherDetails();
        if (res.isEmpty()) System.out.println("  Aucun livre trouve.");
    }

    private void ajouterLivre() {
        Console.sousTitre("Ajouter un livre");
        String id = Console.lireTexte("ID (ex: L5)");
        if (idDejaUtilise(id)) return;

        String titre  = Console.lireTexte("Titre");
        String auteur = Console.lireTexte("Auteur");
        String isbn   = Console.lireTexte("ISBN");
        String cat    = Console.lireCategorie();

        bib.ajouterDocument(new Livre(id, titre, auteur, isbn, cat));
        Console.ok("Livre \"" + titre + "\" ajoute.");
    }

    private void ajouterMagazine() {
        Console.sousTitre("Ajouter un magazine");
        String id = Console.lireTexte("ID (ex: M3)");
        if (idDejaUtilise(id)) return;

        String titre  = Console.lireTexte("Titre");
        int    numero = Console.lireEntier("Numero d'edition", 1, 99999);
        String cat    = Console.lireCategorie();

        bib.ajouterDocument(new Magazine(id, titre, numero, cat));
        Console.ok("Magazine \"" + titre + "\" ajoute.");
    }

    private void supprimerLivre() {
        Console.sousTitre("Supprimer un livre");
        voirTous();
        String id = Console.lireTexte("ID du livre a supprimer");
        try {
            bib.supprimerLivre(id);
        } catch (LivreIntrouvableException e) {
            Console.err(e.getMessage());
        }
    }


    private void voirUtilisateurs() {
        Console.sousTitre("Tous les utilisateurs (" + bib.getTousLesUtilisateurs().size() + ")");
        if (bib.getTousLesUtilisateurs().isEmpty()) {
            System.out.println("  Aucun utilisateur enregistre.");
            return;
        }
        for (Utilisateur u : bib.getTousLesUtilisateurs().values()) {
            System.out.println("  " + u);
            for (Livre l : u.getLivresEmpruntes()) {
                System.out.println("      -> [" + l.getId() + "] " + l.getTitre());
            }
        }
    }

    private void ajouterUtilisateur() {
        Console.sousTitre("Ajouter un utilisateur");
        String id = Console.lireTexte("ID utilisateur (ex: U3)");
        if (bib.trouverUtilisateur(id) != null) {
            Console.err("L'ID [" + id + "] est deja utilise.");
            return;
        }
        String nom = Console.lireTexte("Nom complet");
        bib.ajouterUtilisateur(new Utilisateur(id, nom));
        Console.ok("Utilisateur \"" + nom + "\" ajoute.");
    }


    private void voirEmpruntsActifs() {
        Console.sousTitre("Emprunts actifs (" + bib.getIdsEmpruntes().size() + ")");
        if (bib.getIdsEmpruntes().isEmpty()) {
            System.out.println("  Aucun emprunt en cours.");
            return;
        }
        for (String id : bib.getIdsEmpruntes()) {
            try {
                Livre l = bib.trouverLivre(id);
                System.out.println("  * [" + id + "] " + l.getTitre()
                    + "  -  " + l.getAuteur());
            } catch (LivreIntrouvableException ignored) {}
        }
    }


    private boolean idDejaUtilise(String id) {
        try {
            bib.trouverLivre(id);
            Console.err("L'ID [" + id + "] est deja utilise.");
            return true;
        } catch (LivreIntrouvableException e) {
            return false;
        }
    }
}
