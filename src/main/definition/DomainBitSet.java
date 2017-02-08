package definition;
import java.util.BitSet;
import java.util.Iterator;

public class DomainBitSet implements Domain {

    /**
     * Un bitset represente le domaine (voir api java pour plus d'infos).
     */
    private BitSet values;

    /**
     * Construit un domaine dont les valeurs sont comprises entre min et max (inclus).
     */
    DomainBitSet(int min, int max) {
        this.values = new BitSet();
		this.values.set(min, max+1);
	}

    private DomainBitSet(BitSet set) {
        this.values = set;
	}

	public DomainBitSet clone() {
		BitSet newB = new BitSet();
		newB.or(values);
		return new DomainBitSet(newB);
	}

    /**
     * @return la taille du domaine, ie le nombre de valeurs dans le domaine.
     */
    public int size() {
		return this.values.cardinality();
	}

    /**
     * @return la première valeur du domaine
     */
    public int firstValue() {
		return values.nextSetBit(0);
	}

    /**
     * @return la derniere valeur du domaine
     */
    public int lastValue() {
        return values.length() - 1;
    }

	public String toString() {
		return this.values.toString();
	}

	public Iterator<Integer> iterator() {
		return new DomainIterator(values);
	}

	public boolean contains(int v) {
        return values.nextSetBit(v) == v;
    }

	public boolean remove(int v) {
		boolean rep = values.get(v);
		values.clear(v);
		return rep;
	}
	
	public int next(int i){
		return values.nextSetBit(i+1);
	}
	
	public int previous(int i){
		return values.previousSetBit(i);
	}

	public void fix(int v) {
		values.clear(0, values.size() - 1);
		values.set(v);
	}

    public boolean equals(Object o) {
        return (o instanceof Domain)
                && ((Domain) o).firstValue() == firstValue()
                && ((Domain) o).lastValue() == lastValue();
    }

}
