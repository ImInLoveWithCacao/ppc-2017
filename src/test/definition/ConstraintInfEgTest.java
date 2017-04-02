package definition;

import org.junit.jupiter.api.Test;
import solver.TestUtils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import static definition.factories.ConstraintFactory.INF_EQ;
import static definition.factories.VariableFactory.*;
import static solver.TestUtils.domainsToString;

class ConstraintInfEgTest {

    private static void assertDomainsAfterFilterEqual(String expected, Variable[]... actual) {
        TestUtils.assertDomainsAfterFilterEqual(INF_EQ, expected, actual);
    }

    @Test
    void isSatisfied() {
        assertGivesResults(
            Constraint::isSatisfied,

            new Boolean[]{true, true, false, false},

            Arrays.stream(new Variable[][]{
                successive(2),
                createVariables(2, 0, 0),
                decreasingDomains(2, 1),
                new Variable[]{
                    createOneVar(0, 1, 1),
                    createOneVar(1, 0, 0)
                }
            })
        );
    }

    @Test
    void isNecessary() {
        assertGivesResults(
            Constraint::isNecessary,

            new Boolean[]{true, true, true, false},

            Arrays.stream(new Variable[][]{
                successive(2),
                createVariables(2, 0, 0),
                decreasingDomains(2, 1),
                new Variable[]{
                    createOneVar(0, 1, 1),
                    createOneVar(1, 0, 0)
                }
            })
        );
    }

    private void assertGivesResults(Function<Constraint, Boolean> toTest, Boolean[] expected, Stream<Variable[]> actual) {
        TestUtils.assertGivesResults(toTest, INF_EQ, expected, actual);
    }

    @Test
    void it_shlould_not_filter_if_not_needed() {
        Variable[][] variables = {
            successive(2),
            new Variable[]{
                createOneVar(0, 0, 1),
                createOneVar(1, 0, 1)
            },
            new Variable[]{
                createOneVar(0, 0, 1),
                createOneVar(1, 1, 2)
            }
        };
        assertDomainsAfterFilterEqual(domainsToString(variables), variables);
    }

    @Test
    void filter() {
        assertDomainsAfterFilterEqual("{1}{1}{1}{1}{}{}",
            new Variable[]{
                createOneVar(0, 1, 1),
                createOneVar(1, 0, 1)
            },
            new Variable[]{
                createOneVar(0, 1, 2),
                createOneVar(1, 0, 1)
            },
            new Variable[]{
                createOneVar(0, 1, 2),
                createOneVar(1, 0, 0)
            }
        );
    }

}