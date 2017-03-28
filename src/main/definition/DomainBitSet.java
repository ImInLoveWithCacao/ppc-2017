package definition;

import java.util.BitSet;

public class DomainBitSet extends IterableBitSet implements Domain {

    /**
     * Les valeurs sont comprises entre min et max (inclus).
     */
    DomainBitSet(int min, int max) {
        super(min, max);
    }

    private DomainBitSet(BitSet set) {
        super(set);
    }

    public DomainBitSet clone() {
        BitSet newB = new BitSet();
        newB.or(values);
        return new DomainBitSet(newB);
    }

    public boolean remove(int v) {
        boolean rep = values.get(v);
        values.clear(v);
        return rep;
    }

    public void fix(int v) {
        values.clear(0, values.size() - 1);
        values.set(v);
    }

}
