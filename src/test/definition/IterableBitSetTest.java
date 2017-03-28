package definition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IterableBitSetTest {
    @Test
    void it_can_create_set() {
        IterableBitSet set = new IterableBitSet(0, 2);
        assertEquals("{0, 1, 2}", set.toString());
    }

    @Test
    void equals() {
        IterableBitSet[] sets = {new IterableBitSet(0, 2), new IterableBitSet(0, 2), new IterableBitSet(0, 1)};
        boolean[] actual = {sets[0].equals(sets[1]), sets[0].equals(sets[2])};
        boolean[] expected = {true, false};
        assertArrayEquals(actual, expected);
    }
}