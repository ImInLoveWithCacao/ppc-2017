package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.ConstraintFactory.chainInferior;
import static definition.factories.VariableFactory.createVariables;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static solver.Solver.WITHFILTER;
import static solver.Solver.createSolver;

class WithFilterTest {
    private static final Integer[][] problem1Sols = new Integer[][]{{0, 1, 2}};
    private static final Integer[][] problem2Sols = new Integer[][]{{1, 0, 0}, {2, 0, 0}, {2, 0, 1}, {2, 1, 0}, {2, 1, 1}};

    @Test
    void random_problem_1() {
        Variable[] vars = createVariables(3, 0, 2);
        Constraint[] cons = chainInferior(vars);
        assertArrayEquals(
                problem1Sols,
                createSolver(
                        WITHFILTER, "With filter random test 1",
                        new Csp(vars, cons)
                ).solve().serializedSolutions()
        );
    }

    @Test
    void random_problem_2() {
        Variable[] vars = createVariables(3, 0, 2);
        Constraint[] cons = new Constraint[]{
                binaryConstraint(vars[2], "<", vars[0]),
                binaryConstraint(vars[1], "<", vars[0]),
        };
        assertArrayEquals(
                problem2Sols,
                createSolver(
                        WITHFILTER, "With filter random test 2",
                        new Csp(vars, cons)
                ).solve().serializedSolutions()
        );
    }
}