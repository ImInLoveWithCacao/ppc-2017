package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static factories.ConstraintFactory.INF;
import static factories.ConstraintFactory.binaryConstraint;
import static factories.VariableFactory.createOneVar;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropagatorTest {
    private final boolean[] propTest = new boolean[]{true, true, false};

    @Test
    void it_propagates_correctly() {
        Variable x0 = createOneVar(0, 0, 2);
        Variable x1 = createOneVar(1, 0, 2);
        Variable x2 = createOneVar(2, 2, 2);
        Constraint c1 = binaryConstraint(x0, INF, x1);
        Constraint c2 = binaryConstraint(x1, INF, x2);

        Propagator p = new Propagator(new Csp(new Variable[]{x0, x1, x2}, new Constraint[]{c1, c2}), x2);

        p.propagateFromCurrentNode();
        assertArrayEquals(propTest, p.changedDomains());
        assertEquals(1, x1.getDomain().lastValue());
        assertEquals(0, x0.getDomain().lastValue());
    }
}