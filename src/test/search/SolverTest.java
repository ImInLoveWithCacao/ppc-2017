package search;

import definition.Constraint;
import definition.ConstraintInf;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SolverTest {
    private final int[][] noConstraintsSolution = new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    private final int[][] simpleTestSolutions = new int[][]{{0, 1}};
    private final boolean[] propTest = new boolean[]{true, false, true};


    @Test
    void no_solution_on_empty_set() {
        Variable x1 = new Variable("x1", 0, 1, 0);
        Variable x2 = new Variable("x2", 1, 1, 0);
        Csp empty = new Csp(new Variable[]{x1, x2}, new Constraint[]{});
        SearchResult res = Solver.search(0, false, 0, empty, new SearchResult("empty"));
        assertEquals(res.getNbSols(), 0);
    }

    @Test
    void with_no_constraints() {
        Variable x0 = new Variable("x0", 0, 0, 1);
        Variable x1 = new Variable("x1", 1, 0, 1);
        Csp csp = new Csp(new Variable[]{x0, x1}, new Constraint[]{});
        SearchResult res = Solver.search(0, false, 0, csp, new SearchResult("no constraints"));
        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        for (int i = 0; i < nbSols; i++) assertArrayEquals(sols[i], noConstraintsSolution[i]);
    }

    @Test
    void simple_problem() {
        Variable x0 = new Variable("x0", 0, 0, 1);
        Variable x1 = new Variable("x1", 0, 0, 1);
        Constraint c = new ConstraintInf(x0, x1);
        Csp simpleCsp = new Csp(new Variable[]{x0, x1}, new Constraint[]{c});
        SearchResult res = Solver.search(0, false, 0, simpleCsp, new SearchResult("simple test"));
        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        assertEquals(nbSols, 1);
        assertArrayEquals(sols[0], simpleTestSolutions[0]);
    }

    @Test
    void it_propagates_correctly() {
        Variable x0 = new Variable("x0", 0, 0, 0);
        Variable x1 = new Variable("x1", 1, 0, 1);
        Constraint c = new ConstraintInf(x0, x1);
        Csp csp = new Csp(new Variable[]{x0, x1}, new Constraint[]{c});
        boolean[] prop = csp.propagate(x0);
        assertArrayEquals(propTest, prop);
        assertEquals(1, x1.getDomain().firstValue());
    }

}