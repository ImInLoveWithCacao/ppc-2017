package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;
import tools.SearchResult;
import tools.Solution;

import java.util.Arrays;

import static definition.factories.ConstraintFactory.*;
import static definition.factories.VariableFactory.*;
import static solver.Solver.BACKTRACK;
import static solver.TestUtils.mockSearchResult;

class BackTrackTest {
    private static final Variable[][] problem1Sols = {
        new Variable[]{
            oneVariable(0, 0, 0),
            oneVariable(1, 1, 1),
            oneVariable(2, 1, 1)
        },
        successive(3),
        new Variable[]{
            oneVariable(0, 0, 0),
            oneVariable(1, 2, 2),
            oneVariable(2, 1, 1),
        },
        new Variable[]{
            oneVariable(0, 0, 0),
            oneVariable(1, 2, 2),
            oneVariable(2, 2, 2)
        },
        new Variable[]{
            oneVariable(0, 1, 1),
            oneVariable(1, 2, 2),
            oneVariable(2, 2, 2)
        }
    };

    private static final Variable[][] problem2Sol = {
        successive(2),
        new Variable[]{
            oneVariable(0, 1, 1),
            oneVariable(1, 0, 0)
        }
    };

    @Test
    void it_stops_when_necessary() {
        Csp csp = new Csp();
        Variable[] vars = createVariables(2, 0, 1);
        csp.addVariables(vars);
        csp.addBinaryConstraints("x0 < x1");
        assertResultsEqual(mockSearchResult(4, new Solution(Arrays.stream(successive(2)))), csp);
    }

    @Test
    void simple_problem() {
        Csp csp = new Csp();
        Variable[] vars = createVariables(3, 0, 2);
        csp.addVariables(vars);
        Constraint[] cons = chainInferior(vars);

        assertResultsEqual(mockSearchResult(12, new Solution(Arrays.stream(successive(3)))), new Csp(vars, cons));
    }

    @Test
    void problem_1() {
        Variable[] vars = createVariables(3, 0, 2);
        Constraint[] cons = new Constraint[]{
            binaryConstraint(vars[0], INF, vars[1]),
            binaryConstraint(vars[0], INF, vars[2])};

        assertResultsEqual(
            mockSearchResult(
                18,
                Arrays.stream(problem1Sols)
                    .map(Arrays::stream)
                    .map(Solution::new).toArray(Solution[]::new)), new Csp(vars, cons));
    }

    @Test
    void problem_2() {
        Variable[] vars = createVariables(2, 0, 1);
        Constraint[] cons = {binaryConstraint(vars[0], DIFF, vars[1])};
        assertResultsEqual(
            mockSearchResult(6, Arrays.stream(problem2Sol)
                .map(Arrays::stream)
                .map(Solution::new)
                .toArray(Solution[]::new)), new Csp(vars, cons)
        );
    }

    private void assertResultsEqual(SearchResult searchResult, Csp csp) {
        TestUtils.assertResultsEqual(BACKTRACK, csp, searchResult);
    }
}