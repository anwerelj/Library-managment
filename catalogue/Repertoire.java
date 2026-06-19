package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Repertoire<T> implements Serializable {

    private List<T> elements;

    public Repertoire() {
        this.elements = new ArrayList<>();
    }

    public void    ajouter(T e)   { elements.add(e); }
    public boolean supprimer(T e) { return elements.remove(e); }
    public List<T> getTous()      { return elements; }
    public int     taille()       { return elements.size(); }
    public boolean estVide()      { return elements.isEmpty(); }
}
