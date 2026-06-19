import menus.Console;
import menus.MenuAdmin;
import menus.MenuUtilisateur;
import modeles.Livre;
import modeles.Magazine;
import modeles.Utilisateur;
import catalogue.GestionnaireBibliotheque;
import stockage.GestionnaireFichiers;

public class Principale {

    public static void main(String[] args) {

        GestionnaireBibliotheque bib = GestionnaireFichiers.charger();
        if (bib.getTousLesDocuments().isEmpty()) {
            initialiserDemo(bib);
        }

        MenuAdmin       admin = new MenuAdmin(bib);
        MenuUtilisateur user  = new MenuUtilisateur(bib);

        boolean actif = true;
        while (actif) {
            Console.ligne();
            System.out.println("  MENU PRINCIPAL");
            Console.ligne();
            System.out.println("  1. Espace Administrateur");
            System.out.println("  2. Espace Utilisateur");
            System.out.println("  3. Sauvegarder et Quitter");
            Console.ligne();

            switch (Console.lireChoix("Votre choix", 1, 3)) {
                case 1: admin.afficher(); break;
                case 2: user.afficher();  break;
                case 3:
                    GestionnaireFichiers.sauvegarder(bib);
                    System.out.println("\n  Au revoir !\n");
                    actif = false;
                    break;
            }
        }

        Console.sc.close();
    }

   

    private static void initialiserDemo(GestionnaireBibliotheque bib) {

        bib.ajouterDocument(new Livre("L1", "Le Petit Prince",  "Antoine de Saint-Exupery", "978-2-07-040850-4", "Roman"));
        bib.ajouterDocument(new Livre("L2", "1984",             "George Orwell",            "978-0-452-28423-4", "Roman"));
        bib.ajouterDocument(new Livre("L3", "L'Etranger",       "Albert Camus",             "978-2-07-036024-5", "Philosophie"));
        bib.ajouterDocument(new Livre("L4", "Harry Potter T1",  "J.K. Rowling",             "978-2-07-054090-1", "Roman"));

        bib.ajouterDocument(new Magazine("M1", "Science & Vie",  1234, "Science"));
        bib.ajouterDocument(new Magazine("M2", "Le Monde Diplo",  789, "Histoire"));

        bib.ajouterUtilisateur(new Utilisateur("U1", "Alice Martin"));
        bib.ajouterUtilisateur(new Utilisateur("U2", "Bob Dupont"));

        Console.ok("Demo chargee : 4 livres, 2 magazines, 2 utilisateurs.");
        System.out.println("  IDs livres : L1 L2 L3 L4  |  Utilisateurs : U1 U2");
    }
}
