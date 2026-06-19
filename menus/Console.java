package menus;

import modeles.Document;
import java.util.Scanner;


public class Console {

    public static Scanner sc = new Scanner(System.in);


    public static void sousTitre(String titre) {
        System.out.println();
        System.out.println("  >> " + titre);
        System.out.println("  ------------------------------------------");
    }

    public static void ligne() {
        System.out.println("  ------------------------------------------");
    }

    public static void ok(String message)  { System.out.println("  [OK]     " + message); }
    public static void err(String message) { System.out.println("  [ERREUR] " + message); }




    public static int lireChoix(String invite, int min, int max) {
        while (true) {
            System.out.print("  " + invite + " [" + min + "-" + max + "] : ");
            String saisie = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(saisie);
                if (val >= min && val <= max) return val;
                System.out.println("  Entrez un nombre entre " + min + " et " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Saisie invalide - entrez un chiffre.");
            }
        }
    }


    public static int lireEntier(String invite, int min, int max) {
        while (true) {
            System.out.print("  " + invite + " (" + min + "-" + max + ") : ");
            String saisie = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(saisie);
                if (val >= min && val <= max) return val;
                System.out.println("  La valeur doit etre entre " + min + " et " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Nombre invalide - reessayez.");
            }
        }
    }


    public static String lireTexte(String invite) {
        while (true) {
            System.out.print("  " + invite + " : ");
            String saisie = sc.nextLine().trim();
            if (!saisie.isEmpty()) return saisie;
            System.out.println("  Ce champ ne peut pas etre vide.");
        }
    }


    public static String lireCategorie() {
        System.out.println("  Categories disponibles :");
        for (int i = 0; i < Document.CATEGORIES.length; i++) {
            System.out.println("    " + (i + 1) + ". " + Document.CATEGORIES[i]);
        }
        int idx = lireChoix("Numero de categorie", 1, Document.CATEGORIES.length);
        return Document.CATEGORIES[idx - 1];
    }
}
