package solver;

import definition.Variable;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static definition.factories.ConstraintFactory.INF;
import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.VariableFactory.createVariables;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.Solver.BRUTEFORCE;
import static solver.Solver.createSolver;

class BruteForceTest {
    private final Integer[][] noConstraintsSolution = new Integer[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    private final Integer[][] simpleTestSolutions = new Integer[][]{{0, 1}};


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