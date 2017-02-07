package search;

import definition.Constraint;
import definition.ConstraintInf;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropagatorTest {
    private final boolean[] propTest = new boolean[]{true, true, false};

    @Test
    void it_propagates_correctly() {
        Variable x0 = new Variable("x0", 0, 0, 2);
        Variable x1 = new Variable("x1", 1, 0, 2);
        Variable x2 = new Variable("x2", 2, 2, 2);

        Csp csp = new Csp(
                new Variable[]{x0, x1, x2},
                new Constraint[]{
                        new ConstraintInf(x0, x1),
                        new ConstraintInf(x1, x2)
                });

        boolean[] prop = (new Propagator(csp, x2)).lauchPropagation();
        assertArrayEquals(propTest, prop);
        assertEquals(1, x1.getDomain().lastValue());
        assertEquals(0, x0.getDomain().lastValue());
    }
}