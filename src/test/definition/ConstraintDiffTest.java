package definition;

import org.junit.jupiter.api.Test;
import solver.TestUtils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import static definition.factories.ConstraintFactory.DIFF;
import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.VariableFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.TestUtils.domainsToString;

class ConstraintDiffTest {

    private static void assertGivesResults(Function<Constraint, Boolean> toTest, Boolean[] expected, Stream<Variable[]> actual) {
        TestUtils.assertGivesResults(toTest, DIFF, expected, actual);
    }

    @Test
    void isSatisfied() {
        assertGivesResults(
            Constraint::isSatisfied,

            new Boolean[]{true, true, false},

            Arrays.stream(new Variable[][]{
                createVariables(2, 0, 1),
                successive(2),
                createVariables(2, 0, 0)
            })
        );
    }

    @Test
    void it_should_not_filter_if_not_needed() {
        Variable[][] vars = {createVariables(2, 0, 1), successive(2)};
        String expected = domainsToString(vars);
        Arrays.stream(vars).forEach(pair -> binaryConstraint(pair[0], DIFF, pair[1]).filter());
        assertEquals(expected, domainsToString(vars));
    }

    @Test
    void filter() {
        Variable[] variables = createVariables(2, 0, 1);
        Variable filtering = oneVariable(2, 0, 0);
        binaryConstraint(filtering, DIFF, variables[0]).filter();
        binaryConstraint(filtering, DIFF, variables[1]).filter();
        assertEquals("{1}{1}{0}", domainsToString(variables, new Variable[]{filtering}));
    }

    @Test
    void toString_test() {
        Variable[] vars = successive(2);
        assertEquals("(x0 != x1)", binaryConstraint(vars[0], DIFF, vars[1]).toString());
    }

}