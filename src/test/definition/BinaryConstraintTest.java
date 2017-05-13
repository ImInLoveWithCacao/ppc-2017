package definition;

import org.junit.jupiter.api.Test;

import static definition.factories.ConstraintFactory.*;
import static definition.factories.VariableFactory.oneVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BinaryConstraintTest {
    @Test
    void same_constraints_should_have_same_hash() {
        Variable v1 = oneVariable(0, 0, 2);
        Variable v2 = oneVariable(1, 1, 3);
        int h1 = binaryConstraint(v1, DIFF, v2).hashCode();
        System.out.println(DIFF.hashCode() + " " + DIFF.hashCode());
        assertEquals(h1, binaryConstraint(v1, DIFF, v2).hashCode());

    }

    @Test
    void different_constraints_should_have_different_hash() {
        Variable v1 = oneVariable(0, 0, 2);
        Variable v2 = oneVariable(1, 1, 3);
        int h1 = binaryConstraint(v1, DIFF, v2).hashCode();
        assertNotEquals(h1, binaryConstraint(v1, INF, v2).hashCode());
    }

}