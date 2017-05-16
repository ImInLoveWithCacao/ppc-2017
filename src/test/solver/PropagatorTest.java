package solver;

import definition.Constraint;
import definition.Csp;
import definition.IterableBitSet;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static definition.factories.ConstraintFactory.INF;
import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.VariableFactory.oneVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropagatorTest {
    private final IterableBitSet propTest = new IterableBitSet(0, 1);

    @Test
    void it_propagates_correctly() throws EmptyVariableException {
        Variable x0 = oneVariable(0, 0, 2);
        Variable x1 = oneVariable(1, 0, 2);
        Variable x2 = oneVariable(2, 2, 2);
        Constraint c1 = binaryConstraint(x0, INF, x1);
        Constraint c2 = binaryConstraint(x1, INF, x2);

        Propagator p = new Propagator(new Csp(new Variable[]{x0, x1, x2}, new Constraint[]{c1, c2}), x2);

        p.propagate();
        assertEquals(propTest, p.changedDomains());
        assertEquals(1, x1.getDomain().lastValue());
        assertEquals(0, x0.getDomain().lastValue());
    }
}