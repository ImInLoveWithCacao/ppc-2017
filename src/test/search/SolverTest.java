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


    @Test
    void no_solution_on_empty_set() {
        Solver empty = new Solver("empty",
                new Variable[]{
                        new Variable("x1", 0, 1, 0),
                        new Variable("x2", 1, 1, 0)
                });

        SearchResult res = empty.bruteForce();
        assertEquals(res.getNbSols(), 0);
    }

    @Test
    void with_no_constraints() {
        Solver solver = new Solver("no constraints",
                new Variable[]{
                        new Variable("x0", 0, 0, 1),
                        new Variable("x1", 1, 0, 1)
                });

        SearchResult res = solver.bruteForce();
        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        for (int i = 0; i < nbSols; i++) assertArrayEquals(sols[i], noConstraintsSolution[i]);
    }

    @Test
    void simple_problem() {
        Variable x0 = new Variable("x0", 0, 0, 1);
        Variable x1 = new Variable("x1", 0, 0, 1);

        Solver simpleProblem = new Solver("simple test",
                new Variable[]{x0, x1},
                new Constraint[]{new ConstraintInf(x0, x1)});

        SearchResult res = simpleProblem.bruteForce();
        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        assertEquals(nbSols, 1);
        assertArrayEquals(sols[0], simpleTestSolutions[0]);
    }

    @Test
    void it_choses_the_smallest_variable_with_heuristic() {
        Variable x0 = new Variable("x0", 0, 0, 3);
        Variable x1 = new Variable("x1", 1, 0, 2);
        Variable x2 = new Variable("x2", 2, 0, 1);
        Variable x3 = new Variable("x3", 3, 0, 0);

        Csp csp = new Csp(new Variable[]{x0, x1, x2, x3});
        assertEquals(csp.smallestVar(), x2);
    }

}