package solver;

import definition.ConstraintMax;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;
import tools.SearchResult;

import static definition.factories.VariableFactory.createVariables;
import static definition.factories.VariableFactory.oneVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static solver.Solver.WITHFILTER;
import static solver.Solver.createSolver;

public class Eval2017 {

    // ------------------------------------ Question 1 -----------------------------------------------------------------

    @Test
    void question_1_1() {
        assertQuestion1(3, 0);
    }

    @Test
    void question_1_2() {
        assertQuestion1(4, 6);
    }

    // J'obtiens 141 solutions
    @Test
    void question_1_3() {
        assertQuestion1(5, 141);
    }

    // J'obtiens 1401 solutions
    @Test
    void question_1_4() {
        assertQuestion1(6, 1401);
    }

    // J'obtiens 8751 solutions
    @Test
    void question_1_5() {
        assertQuestion1(7, 8751);
    }


    private void assertQuestion1(int valMax, int expectedNbSols) {
        assertEquals(
                createSolverAndPrintResult(
                        resolutionPb(valMax),
                        "Question 1 - valMax =".concat(" " + valMax),
                        false
                ).getNbSols(),
                expectedNbSols
        );
    }

    private SearchResult createSolverAndPrintResult(Csp csp, String name, boolean withSolutions) {
        SearchResult result = createSolver(WITHFILTER, name, csp).solve();
        System.out.println(withSolutions ? result.toString() : result.data());
        return result;
    }

    public Csp resolutionPb(int valMax) {
        Csp csp = new Csp();
        csp.addVariables(createVariables(10, 1, valMax));
        csp.addBinaryConstraints(
                "x0 < x1",
                "x0 <= x4",
                "x1 != x2",
                "x1 < x3",
                "x1 < x7",
                "x2 < x3",
                "x4 < x1",
                "x4 < x6",
                "x4 < x7",
                "x5 <= x9",
                "x6 < x7",
                "x6 != x8",
                "x7 <= x8",
                "x8 < x3",
                "x9 < x8"
        );
        return csp;
    }

    // ------------------------------------ Question 2.1 ---------------------------------------------------------------

    @Test
    void question_2_1_n_egal_10() {
        assertQuestion2(10, false);
    }

    @Test
    void question_2_1_n_egal_100() {
        assertQuestion2(100, false);
    }

    @Test
    void question_2_1_n_egal_1000() {
        assertQuestion2(1000, false);
    }

    @Test
    void question_2_1_n_egal_10000() {
        assertQuestion2(10000, false);
    }

    // Il me faut environ 6 secondes pour résoudre ce problème : 1 seconde de génération du problème et 5 secondes
    // (le temps afffiché dans la console) de recherche (le total étant le temps pris par le test JUnit)
    @Test
    void question_2_1_n_egal_100000() {
        assertQuestion2(100000, false);
    }

    @Test
    void question_2_1_n_egal_1000000() {
        assertQuestion2(1000000, false);
    }


    // ------------------------------------ Question 2.2 ---------------------------------------------------------------

    @Test
    void question_2_2_n_egal_10() {
        assertQuestion2(10, true);
    }

    @Test
    void question_2_2_n_egal_100() {
        assertQuestion2(100, true);
    }

    @Test
    void question_2_2_n_egal_1000() {
        assertQuestion2(1000, true);
    }

    @Test
    void question_2_2_n_egal_10000() {
        assertQuestion2(10000, true);
    }

    @Test
    void question_2_2_n_egal_100000() {
        assertQuestion2(100000, true);
    }

    // Il me faut environ 18 secondes pour résoudre ce problème : 6 secondes de génération du problème et 12 secondes
    // (le temps afffiché dans la console) de recherche
    @Test
    void question_2_2_n_egal_1000000() {
        assertQuestion2(1000000, true);
    }

    private void assertQuestion2(int nb, boolean fermeture) {
        assertEquals(
                createSolverAndPrintResult(
                        circuitsInferieurs(nb, fermeture),
                        " Question 2." + (fermeture ? 2 : 1) + " - nb = " + nb,
                        false
                ).getNbSols(),
                fermeture ? 0 : 45
        );
    }

    private Csp circuitsInferieurs(int nb, boolean fermeture) {
        Csp csp = new Csp();
        Variable[] variables = createVariables(nb, 1, 10);
        csp.addVariables(variables);
        for (int i = 0; i < nb / 2; i++) {
            csp.addBinaryConstraints(
                    variables[i].getName()
                            .concat("<=")
                            .concat(variables[(i + 1) % (nb / 2)].getName())
            );
        }
        for (int i = nb / 2; i < nb; i++) {
            csp.addBinaryConstraints(
                    variables[i].getName()
                            .concat("<=")
                            .concat(variables[nb / 2 + ((i + 1) % (nb / 2))].getName())
            );
        }
        csp.addBinaryConstraints("x0 < x" + nb / 2);
        if (fermeture) {
            csp.addBinaryConstraints("x" + (nb - 1) + " < x0");
        }
        return csp;
    }


    // ------------------------------------- Question 3 ----------------------------------------------------------------

    @Test
    void question3() {
        assertEquals(10, createSolverAndPrintResult(generateQ3(), "Question 3", true).getNbSols());
    }

    private Csp generateQ3() {
        Csp csp = new Csp();
        Variable[] variables = {
                oneVariable(0, 4, 7),
                oneVariable(1, 6, 10),
                oneVariable(2, 1, 5)
        };
        csp.addVariables(variables);
        csp.addConstraints(new ConstraintMax(variables));
        return csp;
    }
}