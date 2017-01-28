package definition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import search.SearchResult;
import search.Solution;
import search.Solver;

import java.util.ArrayList;


class CspTest {
    private int[][] noConstraintsSolution = new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}};


    @Test
    void no_solution_on_empty_set() {
        Variable x1 = new Variable("x1", 0, 1, 0);
        Variable x2 = new Variable("x2", 0, 1, 0);
        Csp empty = new Csp(new Variable[] {x1, x2}, new Constraint[]{});
        SearchResult res = Solver.search(0, false, 0, empty, new SearchResult("empty"));
        Assertions.assertEquals(res.getNbSols(), 0);
    }

    @Test
    void with_no_constraints() {
        Variable x1 = new Variable("x1", 0, 0, 1);
        Variable x2 = new Variable("x2", 0, 0, 1);
        Csp csp = new Csp(new Variable[]{x1, x2}, new Constraint[]{});
        SearchResult res = Solver.search(0, false, 0, csp, new SearchResult("no constraints"));
        int[][] sols = res.serializedSolutions();
        int nbSols = sols.length;
        for (int i = 0; i < nbSols; i++) Assertions.assertEquals(sols[i], noConstraintsSolution[i]);
    }

}