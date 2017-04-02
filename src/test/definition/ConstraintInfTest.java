package definition;

import org.junit.jupiter.api.Test;
import solver.TestUtils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import static definition.factories.ConstraintFactory.INF;
import static definition.factories.VariableFactory.*;
import static solver.TestUtils.assertDomainsAfterFilterEqual;
import static solver.TestUtils.domainsToString;

class ConstraintInfTest {
    @Test
    void isSatisfied() {
        assertGivesResults(
            Constraint::isSatisfied,

            new Boolean[]{true, false, false},

            Arrays.stream(new Variable[][]{
                successive(2),
                createVariables(2, 0, 0),
                decreasingDomains(2, 1)
            })
        );
    }

    @Test
    void isNecessary() {
        assertGivesResults(Constraint::isNecessary, new Boolean[]{true, true, true, false}, Arrays.stream(new Variable[][]{
            successive(2),
            new Variable[]{
                createOneVar(0, 0, 0),
                createOneVar(1, 0, 1)
            },
            createVariables(2, 0, 1),
            new Variable[]{
                createOneVar(0, 1, 1),
                createOneVar(1, 0, 1)
            }
        }));
    }

    private void assertGivesResults(Function<Constraint, Boolean> toTest, Boolean[] expected, Stream<Variable[]> actual) {
        TestUtils.assertGivesResults(toTest, INF, expected, actual);
    }

    @Test
    void it_should_not_filter_if_not_needed() {
        Variable[][] variables = {
            successive(2),
            new Variable[]{
                createOneVar(0, 0, 1),
                createOneVar(1, 1, 2)
            },
            new Variable[]{
                createOneVar(0, 0, 1),
                createOneVar(1, 2, 3)
            }
        };
        assertDomainsAfterFilterEqual(INF, domainsToString(variables), variables);
    }

    @Test
    void filter() {
        assertDomainsAfterFilterEqual(
            INF, "{0}{1}{0}{1}{0}{}",
            new Variable[]{
                createOneVar(0, 0, 0),
                createOneVar(1, 0, 1)
            },
            createVariables(2, 0, 1),
            new Variable[]{
                createOneVar(0, 0, 0),
                createOneVar(1, 0, 0)
            }
        );
    }

}