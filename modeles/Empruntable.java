package modeles;

import exceptions.LivreIndisponibleException;


public interface Empruntable {

    void emprunter() throws LivreIndisponibleException;

    void retourner();
}
