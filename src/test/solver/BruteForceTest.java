package solver;

import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;
import tools.SearchResult;

import static factories.ConstraintFactory.INF;
import static factories.ConstraintFactory.binaryConstraint;
import static factories.VariableFactory.createVariables;
import static factories.VariableFactory.decreasingDomains;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BruteForceTest {
    private final int[][] noConstraintsSolution = new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    private final int[][] simpleTestSolutions = new int[][]{{0, 1}};


    @Test
    void no_solution_on_empty_set() {
        SearchResult res = Solver.createSolver(
                Solver.BRUTEFORCE, "empty",
                createVariables(2, 1, 0)
        ).solve();
        assertEquals(res.getNbSols(), 0);
    }

    @Test
    void with_no_constraints() {
        SearchResult res = Solver.createSolver(
                Solver.BRUTEFORCE, "no constraints",
                createVariables(2, 0, 1)
        ).solve();

        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        for (int i = 0; i < nbSols; i++) assertArrayEquals(noConstraintsSolution[i], sols[i]);
    }

    @Test
    void simple_problem() {
        Variable[] vars = createVariables(2, 0, 1);

        SearchResult res = Solver.createSolver(
                "simple test", Solver.BRUTEFORCE,
                vars, binaryConstraint(vars[0], INF, vars[1])
        ).solve();

        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        assertEquals(1, nbSols);
        assertArrayEquals(sols[0], simpleTestSolutions[0]);
    }

    @Test
    void it_choses_the_smallest_variable_with_heuristic() {
        Variable[] vars = decreasingDomains(4, 3);
        Csp csp = new Csp(vars);
        assertEquals(csp.smallestDomain(), vars[2]);
    }

}