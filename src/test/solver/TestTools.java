package solver;

import definition.Csp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.Solver.createSolver;

class TestTools {
    static void assertQueston1(int maxDomainSize, int expectedNbSols, int... options) {
        assertForGivenOptions(
                "question 1, n=" + maxDomainSize,
                Eval2016.generateQuestion1(maxDomainSize), expectedNbSols,
                options
        );
    }

    private static void assertForGivenOptions(String solverName, Csp csp, int expectedNbSols, int... options) {
        int nbSolsFound;

        for (int i : options) {
            nbSolsFound = createSolver(i, solverName, csp).solve().getNbSols();
            try {
                assertEquals(nbSolsFound, expectedNbSols);
            } catch (AssertionError e) {
                throw new AssertionError(
                                                new StringBuilder()
                                                        .append("\n----------- ").append(solverName).append(" -----------\n")
                                                        .append("Solver type ").append(i)
                                                        .append(" didn't find the correct number of solutions.\n")
                                                        .append("expected: ").append(expectedNbSols).append("\n")
                                                        .append("actual: ").append(nbSolsFound).append("\n")
                );
            }
        }
    }
}