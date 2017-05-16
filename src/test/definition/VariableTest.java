package definition;

import definition.factories.VariableFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableTest {
    @Test
    void it_can_create_a_var() {
        assertEquals(VariableFactory.oneVariable(0, 0, 2).toString(), "x0 := {0, 1, 2}");
    }

    @Test
    void equals() {
        Variable[] vars = VariableFactory.createVariables(2, 0, 2);
        Variable v0 = vars[0];
        Variable v01 = VariableFactory.oneVariable(0, 0, 2);
        boolean[] actual = {v0.equals(vars[1]), v01.equals(v0)};
        boolean[] expected = {false, true};
        assertArrayEquals(expected, actual);
    }
}