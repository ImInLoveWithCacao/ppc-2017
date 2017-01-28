package definition;

import java.util.Iterator;

public interface Domain extends Iterable<Integer> {
    Domain clone();

    int size();

    // retourne vrai ssi la valeur v est dans le domaine
    boolean contains(int v);

    // retourne la premiere valeur du domaine
    int firstValue();

    // retourne la derniere valeur du domaine
    int lastValue();

    /**
     * Supprime la valeur v du domaine. 
     * @param v valeur à supprimer
     * @return true si la valeur existait.
     */
    boolean remove(int v);
    
    /**
     * Supprime toutes les valeurs du domaine excepte v.
     * @param v
     */
    void fix(int v);

    int next(int i);

    int previous(int i);

    Iterator<Integer> iterator();


}
