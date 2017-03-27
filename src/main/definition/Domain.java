package definition;

import java.util.Iterator;

public interface Domain extends Iterable<Integer>, Cloneable {
    DomainBitSet clone();

    int size();

    int firstValue();

    int lastValue();

    /**
     * @return true si la valeur existait.
     */
    boolean remove(int v);
    
    /**
     * Supprime toutes les valeurs du domaine excepte v.
     */
    void fix(int v);

    Iterator<Integer> iterator();


}
