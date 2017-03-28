package definition;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static definition.factories.ConstraintFactory.DIFF;
import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.VariableFactory.createVariables;
import static definition.factories.VariableFactory.successive;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstraintDiffTest {

    @Test
    void isSatisfied() {
        Variable[][] vars = {
            createVariables(2, 0, 1),
            successive(2),
            createVariables(2, 0, 0)
        };
        assertArrayEquals(
            new Boolean[]{true, true, false},
            Arrays.stream(vars)
                .map(pair -> binaryConstraint(pair[0], DIFF, pair[1]).isSatisfied())
                .toArray(Boolean[]::new));
    }

    @Test
    void it_should_not_filter_if_not_needed() {
        Variable[][] vars = {createVariables(2, 0, 1), successive(2)};
        String expected = domainsToString(vars);
        Arrays.stream(vars).forEach(pair -> binaryConstraint(pair[0], DIFF, pair[1]).filter());
        assertEquals(expected, domainsToString(vars));
    }

    private String domainsToString(Variable[][] vars) {
        StringBuilder res = new StringBuilder();
        for (Variable[] var : vars)
            for (Variable aVar : var)
                res.append(aVar.getDomain().toString());
        return res.toString();
    }

    @Test
    void toString_test() {
        Variable[] vars = successive(2);
        assertEquals("x0 != x1", binaryConstraint(vars[0], DIFF, vars[1]).toString());
    }

}