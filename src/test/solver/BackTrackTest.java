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
            createOneVar(0, 0, 0),
            createOneVar(1, 1, 1),
            createOneVar(2, 1, 1)
        },
        successive(3),
        new Variable[]{
            createOneVar(0, 0, 0),
            createOneVar(1, 2, 2),
            createOneVar(2, 1, 1),
        },
        new Variable[]{
            createOneVar(0, 0, 0),
            createOneVar(1, 2, 2),
            createOneVar(2, 2, 2)
        },
        new Variable[]{
            createOneVar(0, 1, 1),
            createOneVar(1, 2, 2),
            createOneVar(2, 2, 2)
        }
    };

    private static final Variable[][] problem2Sol = {
        successive(2),
        new Variable[]{
            createOneVar(0, 1, 1),
            createOneVar(1, 0, 0)
        }
    };

    @Test
    void it_stops_when_necessary() {
        Variable[] vars = createVariables(2, 0, 1);
        Constraint[] cons = new Constraint[]{binaryConstraint(vars[0], INF, vars[1])};
        assertResultsEqual(vars, cons, mockSearchResult(4, new Solution(successive(2))));
    }

    @Test
    void simple_problem() {
        Variable[] vars = createVariables(3, 0, 2);
        Constraint[] cons = chainInferior(vars);

        assertResultsEqual(vars, cons, mockSearchResult(12, new Solution(successive(3))));
    }

    @Test
    void problem_1() {
        Variable[] vars = createVariables(3, 0, 2);
        Constraint[] cons = new Constraint[]{
            binaryConstraint(vars[0], INF, vars[1]),
            binaryConstraint(vars[0], INF, vars[2])};

        assertResultsEqual(vars, cons,
            mockSearchResult(18, Arrays.stream(problem1Sols).map(Solution::new).toArray(Solution[]::new)));
    }

    @Test
    void problem_2() {
        Variable[] vars = createVariables(2, 0, 1);
        Constraint[] cons = {binaryConstraint(vars[0], DIFF, vars[1])};
        assertResultsEqual(vars, cons,
            mockSearchResult(6, Arrays.stream(problem2Sol).map(Solution::new).toArray(Solution[]::new)));
    }

    private void assertResultsEqual(Variable[] vars, Constraint[] cons, SearchResult searchResult) {
        TestUtils.assertResultsEqual(BACKTRACK, new Csp(vars, cons), searchResult);
    }
}