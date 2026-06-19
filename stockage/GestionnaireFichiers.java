package stockage;

import catalogue.GestionnaireBibliotheque;
import java.io.*;
import modeles.Document;
import modeles.Livre;
import modeles.Magazine;
import modeles.Utilisateur;


public class GestionnaireFichiers {

    private static final String FICHIER = "bibliotheque_donnees.txt";
    private static final String SEP     = "|";


    public static void sauvegarder(GestionnaireBibliotheque g) {
        try (BufferedWriter w = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FICHIER), "UTF-8"))) {

            for (Document d : g.getTousLesDocuments()) {
                if (d instanceof Livre) {
                    Livre l = (Livre) d;
                    w.write("LIVRE" + SEP + l.getId() + SEP + l.getTitre()
                          + SEP + l.getAuteur() + SEP + l.getIsbn()
                          + SEP + l.getCategorie() + SEP + l.isDisponible());
                } else if (d instanceof Magazine) {
                    Magazine m = (Magazine) d;
                    w.write("MAGAZINE" + SEP + m.getId() + SEP + m.getTitre()
                          + SEP + m.getNumeroEdition() + SEP + m.getCategorie());
                }
                w.newLine();
            }

            for (Utilisateur u : g.getTousLesUtilisateurs().values()) {
                w.write("UTILISATEUR" + SEP + u.getId() + SEP + u.getNom());
                w.newLine();
            }

            for (Utilisateur u : g.getTousLesUtilisateurs().values()) {
                for (Livre l : u.getLivresEmpruntes()) {
                    w.write("EMPRUNT" + SEP + u.getId() + SEP + l.getId());
                    w.newLine();
                }
            }

            System.out.println("  [OK] Donnees sauvegardees -> " + FICHIER);

        } catch (IOException e) {
            System.out.println("  [!!] Erreur sauvegarde : " + e.getMessage());
        }
    }


    public static GestionnaireBibliotheque charger() {
        File fichier = new File(FICHIER);

        if (!fichier.exists()) {
            System.out.println("  Nouveau demarrage - aucune donnee existante.");
            return new GestionnaireBibliotheque();
        }

        GestionnaireBibliotheque g = new GestionnaireBibliotheque();

        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(new FileInputStream(fichier), "UTF-8"))) {

            String ligne;
            while ((ligne = r.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;

                String[] c = ligne.split("\\" + SEP, -1);

                switch (c[0]) {
                    case "LIVRE":
                        if (c.length == 7) {
                            Livre l = new Livre(c[1], c[2], c[3], c[4], c[5]);
                            if (c[6].equals("false")) {
                                try { l.emprunter(); }
                                catch (Exception ignored) {}
                            }
                            g.ajouterDocument(l);
                        }
                        break;
                    case "MAGAZINE":
                        if (c.length == 5) {
                            g.ajouterDocument(
                                new Magazine(c[1], c[2], Integer.parseInt(c[3]), c[4]));
                        }
                        break;
                    case "UTILISATEUR":
                        if (c.length == 3) {
                            g.ajouterUtilisateur(new Utilisateur(c[1], c[2]));
                        }
                        break;
                    case "EMPRUNT":
                        if (c.length == 3) {
                            Utilisateur u = g.trouverUtilisateur(c[1]);
                            try {
                                Livre l = g.trouverLivre(c[2]);
                                if (u != null) {
                                    u.ajouterLivre(l);
                                    g.getIdsEmpruntes().add(c[2]);
                                }
                            } catch (Exception ignored) {}
                        }
                        break;
                }
            }

            System.out.println("  [OK] " + g.getNombreDocuments()
                + " documents charges depuis " + FICHIER);

        } catch (IOException e) {
            System.out.println("  [!!] Erreur chargement : " + e.getMessage());
        }

        return g;
    }
}
