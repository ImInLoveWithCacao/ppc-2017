package definition;

import java.util.Iterator;

public interface Domain extends Iterable<Integer> {
    public Domain clone();
    
    public int size();

    // retourne vrai ssi la valeur v est dans le domaine
    public boolean contains(int v);

    // retourne la premiere valeur du domaine
    public int firstValue();

    // retourne la derniere valeur du domaine
    public int lastValue();

    /**
     * Supprime la valeur v du domaine. 
     * @param v
     * @return true si la valeur existait.
     */
    public boolean remove(int v);
    
    /**
     * Supprime toutes les valeurs du domaine excepte v.
     * @param v
     */
    public void fix(int v);
    
    public int next(int i);
    
    public int previous(int i);
   
    public Iterator<Integer> iterator();


}
