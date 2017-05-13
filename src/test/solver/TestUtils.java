package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import tools.SearchResult;
import tools.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static definition.factories.ConstraintFactory.*;
import static definition.factories.VariableFactory.createVariables;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.Solver.WITHFILTER;
import static solver.Solver.createSolver;

interface ConstraintFeeder {
    Constraint apply(int nb, Variable[] vars, int i);
}

public class TestUtils {
    static void assertQueston1(int maxDomainSize, int expectedNbSols, int... options) {
        assertForGivenOptions(maxDomainSize, expectedNbSols, res -> System.out.println(res.data()), options);
    }


    private static void assertForGivenOptions(int maxDomainSize, int expectedNbSols,
                                              Consumer<SearchResult> resultsPrinter, int... options) {
        int nbSolsFound;

        for (int i : options) {
            String solverName = "question 1 - n = " + maxDomainSize + " - solver type = " + i;
            SearchResult res = createSolver(i, solverName, generateQuestion1(maxDomainSize)).solve();
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

    static void assertQuestion21(int nbVariables) {
        assertQuestion2(nbVariables, 1, 120, "1");
    }

    static void assertQuestion22(int nbVariables) {
        assertQuestion2(nbVariables, 2, 0, "2");
    }

    private static void assertQuestion2(int nbVariables, int subIndex, int expected, String subQuestion) {
        SearchResult res = createSolver(
            WITHFILTER,
            "question 2.".concat(subQuestion).concat("#variables (n) = " + nbVariables),
            generateQ2(nbVariables, subIndex)
        ).solve();
        System.out.println(res.data());
        assertEquals(expected, res.getNbSols());
    }

    private static Csp generateQ2(int nb, int subIndex) {
        final Variable[] vars = createVariables(nb, 1, 10);
        List<Constraint> constraints = range(0, nb + 2)
            .mapToObj(i -> constraintFeederQ21(nb, vars, i))
            .collect(Collectors.toList());
        if (subIndex == 2) constraints.add(binaryConstraint(vars[nb - 1], "<", vars[0]));
        return new Csp(vars, constraints.toArray(new Constraint[0]));
    }

    private static Constraint constraintFeederQ21(int nb, Variable[] vars, int i) {
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
        range(0, nbNodes).forEach(i -> expected.addNode());
        return expected;
    }

    public static String domainsToString(Variable[]... vars) {
        StringBuilder res = new StringBuilder();
        Arrays.stream(vars).forEach(variables
            -> Arrays.stream(variables).forEach(var -> res.append(var.getDomain().toString())));
        return res.toString();
    }

    public static void assertGivesResults(Function<Constraint, Boolean> toTest, String operator, Boolean[] expected,
                                          Stream<Variable[]> variables) {
        assertArrayEquals(
            expected,
            variables.map(pair -> toTest.apply(binaryConstraint(pair[0], operator, pair[1])))
                .toArray(Boolean[]::new)
        );
    }

    public static void assertDomainsAfterFilterEqual(String operator, String expected, Variable[]... vars) {
        Arrays.stream(vars).forEach(pair -> binaryConstraint(pair[0], operator, pair[1]).filter());
        assertEquals(expected, domainsToString(vars));
    }
}