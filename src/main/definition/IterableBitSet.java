package definition;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;

public class IterableBitSet implements Iterable<Integer> {
    /**
     * Un bitset represente le domaine (voir api java pour plus d'infos).
     */
    BitSet values;

    public IterableBitSet() {
        this.values = new BitSet();
    }

    IterableBitSet(BitSet set) {
        this.values = set;
    }

    public IterableBitSet(int min, int max) {
        this.values = new BitSet();
        this.values.set(min, max + 1);
    }

    /**
     * @return la taille du domaine, ie le nombre de valeurs dans le domaine.
     */
    public int size() {
        return this.values.cardinality();
    }

    public int firstValue() {
        return values.nextSetBit(0);
    }

    public int lastValue() {
        return values.length() - 1;
    }

    public String toString() {
        return this.values.toString();
    }

    public Iterator<Integer> iterator() {
        return new BitSetIterator(values);
    }

    public void addValue(int v) {
        values.set(v);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
            || !(o == null || getClass() != o.getClass())
            && Objects.equals(values, ((IterableBitSet) o).values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}
