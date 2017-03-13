package definition;

import java.util.BitSet;
import java.util.Iterator;

public class BitSetIterator implements Iterator<Integer> {
    private int i = 0;
	private BitSet d;

    BitSetIterator(BitSet d) {
        this.d = d;
	}

	@Override
	public boolean hasNext() {
		return (d.nextSetBit(i) != -1);
	}

	@Override
	public Integer next() {
		int j = d.nextSetBit(i);
		i = j + 1;
		return j;
	}

}
