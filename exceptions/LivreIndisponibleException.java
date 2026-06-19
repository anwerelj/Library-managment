package exceptions;


public class LivreIndisponibleException extends Exception {

    private String titreLivre;

    public LivreIndisponibleException(String titreLivre) {
        
        super("Le livre \"" + titreLivre + "\" n'est pas disponible. Il est actuellement emprunte par quelqu'un d'autre.");
        this.titreLivre = titreLivre;
    }

    public String getTitreLivre() {
        return titreLivre;
    }
}
