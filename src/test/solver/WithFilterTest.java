package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static factories.ConstraintFactory.chainInferior;
import static factories.VariableFactory.createVariables;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static solver.Solver.WITHFILTER;
import static solver.Solver.createSolver;

class WithFilterTest {
    private static final Integer[][] problem1Sols = new Integer[][]{{0, 1, 2}};

    @Test
    void random_problem_1() {
        Variable[] vars = createVariables(3, 0, 2);
        Constraint[] cons = chainInferior(vars);
        assertArrayEquals(
                problem1Sols,
                createSolver(
                        WITHFILTER, "With filter test",
                        new Csp(vars, cons)
                ).solve().serializedSolutions()
        );
    }
}