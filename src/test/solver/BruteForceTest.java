package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;
import tools.Solution;

import java.util.Arrays;
import java.util.stream.IntStream;

import static definition.factories.ConstraintFactory.INF;
import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.VariableFactory.createOneVar;
import static definition.factories.VariableFactory.createVariables;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.Solver.*;
import static solver.TestUtils.assertResultsEqual;
import static solver.TestUtils.mockSearchResult;

class BruteForceTest {
    private final Integer[][] noConstraintsSolution = new Integer[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    private final Integer[][] simpleTestSolutions = new Integer[][]{{0, 1}};

    @Test
    void it_selects_first_variable_first_if_both_uninstantiated() {
        assertAllPickFirst(
            createVariables(2, 0, 1),
            0, BRUTEFORCE, BACKTRACK, WITHFILTER);
    }

    @Test
    void it_selects_first_uninstantiated_variable() {
        assertAllPickFirst(
            new Variable[]{createOneVar(0, 0, 0), createOneVar(1, 0, 1)},
            1, BRUTEFORCE, BACKTRACK, WITHFILTER
        );
    }

    private void assertAllPickFirst(Variable[] variables, int actualIndex, int... options) {
        Arrays.stream(options).forEach(
            i -> {
                int expected = createSolver(i, "Variable selection test ".concat("option " + i),
                    variables).choseNextVar().getInd();
                assertEquals(expected, actualIndex);
            }
        );
    }

    @Test
    void it_searches_all_nodes() {
        Variable[] vars = createVariables(2, 0, 1);
        Constraint[] cons = new Constraint[]{binaryConstraint(vars[0], INF, vars[1])};
        assertResultsEqual(
            BRUTEFORCE,
            new Csp(vars, cons),
            mockSearchResult(6,
                new Solution(new Variable[]{
                    createOneVar(0, 0, 0), createOneVar(1, 1, 1)
                }))
        );
    }

    @Test
    void no_solution_on_empty_set() {
        assertEquals(
                createSolver(
                        BRUTEFORCE, "empty",
                        createVariables(2, 1, 0)
                ).solve().getNbSols(),
                0
        );
    }

    @Test
    void with_no_constraints() {
        final Integer[][] sols = createSolver(
                BRUTEFORCE, "no constraints",
                createVariables(2, 0, 1)
        ).solve().serializedSolutions();

        IntStream.range(0, sols.length)
                .forEach(i -> assertArrayEquals(noConstraintsSolution[i], sols[i]));
    }

    @Test
    void simple_problem() {
        Variable[] vars = createVariables(2, 0, 1);

        Integer[][] sols = createSolver(
                BRUTEFORCE, "simple tools",
                vars, binaryConstraint(vars[0], INF, vars[1])
        ).solve().serializedSolutions();

        assertEquals(1, sols.length);
        assertArrayEquals(sols[0], simpleTestSolutions[0]);
    }
}