package modeles;

import exceptions.LivreIndisponibleException;

public class Livre extends Document implements Empruntable {

    private String  auteur;
    private String  isbn;
    private boolean disponible;

    public Livre(String id, String titre, String auteur, String isbn, String categorie) {
        super(id, titre, categorie);
        this.auteur     = auteur;
        this.isbn       = isbn;
        this.disponible = true;
    }

    public String  getAuteur()    {
        return auteur; }
    public String  getIsbn()      {
        return isbn; }
    public boolean isDisponible() {
        return disponible; }

    @Override
    public void emprunter() throws LivreIndisponibleException {
        if (!disponible) {
            throw new LivreIndisponibleException(getTitre());
        }
        disponible = false;
    }

    @Override
    public void retourner() {
        disponible = true;
    }

    @Override
    public void afficherDetails() {
        String statut = disponible ? "[Disponible]" : "[Emprunte]  ";
        System.out.println("  " + statut + " " + super.toString());
        System.out.println("             Auteur : " + auteur + "  |  ISBN : " + isbn);
    }
}
