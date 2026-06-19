package modeles;


public class Magazine extends Document {

    private int numeroEdition;

    public Magazine(String id, String titre, int numeroEdition, String categorie) {
        super(id, titre, categorie);
        this.numeroEdition = numeroEdition;
    }

    public int getNumeroEdition() {
        return numeroEdition; }

    @Override
    public void afficherDetails() {
        System.out.println("  [MAGAZINE]   " + super.toString());
        System.out.println("               Edition n° " + numeroEdition);
    }
}
