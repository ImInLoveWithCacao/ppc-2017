package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import tools.SearchResult;
import tools.Solution;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static definition.factories.ConstraintFactory.*;
import static definition.factories.VariableFactory.createVariables;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.Solver.WITHFILTER;
import static solver.Solver.createSolver;

class TestUtils {
    static void assertQueston1(int maxDomainSize, int expectedNbSols, int... options) {
        assertForGivenOptions(
            "question 1, n = " + maxDomainSize,
            generateQuestion1(maxDomainSize), expectedNbSols,
            res -> System.out.println(res.data()), options
        );
    }


    private static Csp generateQuestion1(int maxD) {
        Variable[] vars = createVariables(10, 1, maxD);
        Variable x0 = vars[0];
        Variable x1 = vars[1];
        Variable x2 = vars[2];
        Variable x3 = vars[3];
        Variable x4 = vars[4];
        Variable x6 = vars[6];

        Constraint[] cons = new Constraint[14];
        cons[0] = binaryConstraint(x0, INF, x2);
        cons[1] = binaryConstraint(x0, INF, x4);
        cons[2] = binaryConstraint(x1, INF_EQ, x2);
        cons[3] = binaryConstraint(x1, INF, x3);
        cons[4] = binaryConstraint(x1, INF, vars[7]);
        cons[5] = binaryConstraint(x3, INF, x2);
        cons[6] = binaryConstraint(x4, INF_EQ, x1);
        cons[7] = binaryConstraint(x4, INF, x6);
        cons[8] = binaryConstraint(x4, DIFF, vars[9]);
        cons[9] = binaryConstraint(vars[5], INF, vars[9]);
        cons[10] = binaryConstraint(x6, DIFF, vars[7]);
        cons[11] = binaryConstraint(x6, DIFF, vars[8]);
        cons[12] = binaryConstraint(vars[7], INF_EQ, vars[8]);
        cons[13] = binaryConstraint(vars[9], INF, vars[8]);

        return new Csp(vars, cons);
    }

    private static void assertForGivenOptions(String solverName, Csp csp, int expectedNbSols,
                                              Consumer<SearchResult> resultsPrinter, int... options) {
        int nbSolsFound;

        for (int i : options) {
            SearchResult res = createSolver(i, solverName, csp).solve();
            resultsPrinter.accept(res);
            nbSolsFound = res.getNbSols();
            try {
                assertEquals(nbSolsFound, expectedNbSols);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "\n----------- ".concat(solverName).concat(" -----------\n")
                        .concat("Solver type ").concat("" + i)
                        .concat(" didn't find the correct number of solutions.\n")
                        .concat("expected: ").concat("" + expectedNbSols).concat("\n")
                        .concat("actual: ").concat("" + nbSolsFound).concat("\n")
                );
            }
        }
    }

    static void assertQuestion21(int nbVariables) {
        SearchResult res = createSolver(
            WITHFILTER,
            "question 2.1 - #variables (n) = " + nbVariables,
            generateQ21(nbVariables)
        ).solve();
        System.out.println(res.data());
        assertEquals(res.getNbSols(), 120);
    }

    private static Csp generateQ21(int nb) {
        final Variable[] vars = createVariables(nb, 1, 10);
        return new Csp(
            vars,
            IntStream.range(0, nb + 2)
                .mapToObj(i -> constraintFeeder(nb, vars, i))
                .toArray(Constraint[]::new)
        );
    }

    private static Constraint constraintFeeder(int nb, Variable[] vars, int i) {
        int oneThird = (int) Math.floor(nb / 3.0);
        if (i == oneThird - 1)
            return binaryConstraint(vars[i], "<=", vars[0]);
        else if (i == 2 * oneThird - 1)
            return binaryConstraint(vars[i], "<=", vars[oneThird]);
        else if (i == nb - 1)
            return binaryConstraint(vars[i], "<=", vars[2 * oneThird]);
        else if (i == nb)
            return binaryConstraint(vars[0], "<", vars[oneThird]);
        else if (i == nb + 1)
            return binaryConstraint(vars[oneThird], "<", vars[2 * oneThird]);
        else
            return binaryConstraint(vars[i], "<=", vars[i + 1]);
    }

    static void assertResultsEqual(int type, Csp csp, SearchResult expected) {
        assertEquals(createSolver(type, "actual", csp).solve(), expected);
    }

    static SearchResult mockSearchResult(int nbNodes, Solution... sols) {
        SearchResult expected = new SearchResult("expected");
        Arrays.stream(sols).forEach(expected::addSol);
        IntStream.range(0, nbNodes).forEach(i -> expected.addNode());
        return expected;
    }
}