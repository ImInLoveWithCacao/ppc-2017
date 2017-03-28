package definition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainBitSetTest {
    @Test
    void it_can_create_set() {
        DomainBitSet set = new DomainBitSet(0, 2);
        assertEquals("{0, 1, 2}", set.toString());
    }

    @Test
    void equals() {
        DomainBitSet[] sets = {new DomainBitSet(0, 2), new DomainBitSet(0, 2), new DomainBitSet(0, 1)};
        boolean[] actual = {sets[0].equals(sets[1]), sets[0].equals(sets[2])};
        boolean[] expected = {true, false};
        assertArrayEquals(actual, expected);
    }

    @Test
    void remove() {
        DomainBitSet actual = new DomainBitSet(0, 3);
        actual.remove(2);
        assertEquals(actual.toString(), "{0, 1, 3}");
    }

    @Test
    void fix() {
        DomainBitSet actual = new DomainBitSet(0, 3);
        actual.fix(2);
        assertEquals(actual.toString(), "{2}");
    }
}