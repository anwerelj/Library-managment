package modeles;

import java.io.Serializable;


public abstract class Document implements Serializable {

    public static final String[] CATEGORIES = {
        "Roman", "Science", "Histoire", "Informatique", "Philosophie", "Autre"
    };

    private String id;
    private String titre;
    private String categorie;

    public Document(String id, String titre, String categorie) {
        this.id        = id;
        this.titre     = titre;
        this.categorie = categorieValide(categorie) ? categorie : "Autre";
    }

    public static boolean categorieValide(String cat) {
        for (String c : CATEGORIES) {
            if (c.equalsIgnoreCase(cat)) return true;
        }
        return false;
    }

    public String getId()        { return id; }
    public String getTitre()     { return titre; }
    public String getCategorie() { return categorie; }

    public abstract void afficherDetails();

    @Override
    public String toString() {
        return "[" + id + "] " + titre + " (" + categorie + ")";
    }
}
