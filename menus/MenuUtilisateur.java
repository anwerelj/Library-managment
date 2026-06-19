package menus;

import catalogue.GestionnaireBibliotheque;
import exceptions.LivreIndisponibleException;
import exceptions.LivreIntrouvableException;
import modeles.Document;
import modeles.Livre;
import modeles.Utilisateur;


public class MenuUtilisateur {

    private final GestionnaireBibliotheque bib;

    public MenuUtilisateur(GestionnaireBibliotheque bib) {
        this.bib = bib;
    }

    public void afficher() {
        Console.sousTitre("Connexion Utilisateur");
        String id = Console.lireTexte("Votre ID utilisateur");

        Utilisateur u = bib.trouverUtilisateur(id);
        if (u == null) {
            Console.err("Utilisateur introuvable. Demandez a l'admin de creer votre compte.");
            return;
        }
        Console.ok("Bienvenue, " + u.getNom() + " !");

        boolean actif = true;
        while (actif) {
            Console.ligne();
            System.out.println("  ESPACE UTILISATEUR - " + u.getNom()
                + "  |  emprunts: " + u.getLivresEmpruntes().size());
            Console.ligne();
            System.out.println("  1. Voir les livres disponibles");
            System.out.println("  2. Emprunter un livre");
            System.out.println("  3. Retourner un livre");
            System.out.println("  4. Mes emprunts en cours");
            System.out.println("  5. Retour");
            Console.ligne();

            switch (Console.lireChoix("Votre choix", 1, 5)) {
                case 1: voirDisponibles(); break;
                case 2: emprunter(u);      break;
                case 3: retourner(u);      break;
                case 4: mesEmprunts(u);    break;
                case 5: actif = false;     break;
            }
        }
    }


    private void voirDisponibles() {
        Console.sousTitre("Livres disponibles");
        boolean aucun = true;
        for (Document d : bib.getTousLesDocuments()) {
            if (d instanceof Livre) {
                Livre l = (Livre) d;
                if (l.isDisponible()) {
                    l.afficherDetails();
                    aucun = false;
                }
            }
        }
        if (aucun) System.out.println("  Aucun livre disponible pour le moment.");
    }

    private void emprunter(Utilisateur u) {
        Console.sousTitre("Emprunter un livre");
        voirDisponibles();
        String idLivre = Console.lireTexte("ID du livre a emprunter");
        try {
            bib.emprunterLivre(u.getId(), idLivre);
        } catch (LivreIntrouvableException e) {
            Console.err(e.getMessage());
        } catch (LivreIndisponibleException e) {
            Console.err("Emprunt impossible : " + e.getMessage());
        }
    }

    private void retourner(Utilisateur u) {
        Console.sousTitre("Retourner un livre");
        if (u.getLivresEmpruntes().isEmpty()) {
            System.out.println("  Vous n'avez aucun livre a retourner.");
            return;
        }
        System.out.println("  Vos emprunts en cours :");
        for (Livre l : u.getLivresEmpruntes()) {
            System.out.println("    -> [" + l.getId() + "] " + l.getTitre());
        }
        String idLivre = Console.lireTexte("ID du livre a retourner");
        try {
            bib.retournerLivre(u.getId(), idLivre);
        } catch (LivreIntrouvableException e) {
            Console.err(e.getMessage());
        }
    }

    private void mesEmprunts(Utilisateur u) {
        Console.sousTitre("Mes emprunts - " + u.getNom()
            + " (" + u.getLivresEmpruntes().size() + ")");
        if (u.getLivresEmpruntes().isEmpty()) {
            System.out.println("  Vous n'avez aucun livre emprunte.");
            return;
        }
        for (Livre l : u.getLivresEmpruntes()) {
            l.afficherDetails();
        }
    }
}
