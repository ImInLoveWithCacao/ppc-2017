package eval;


import definition.ConstraintMax;
import definition.Csp;
import definition.Variable;
import tools.SearchResult;

import static definition.factories.VariableFactory.createVariables;
import static definition.factories.VariableFactory.oneVariable;
import static solver.Solver.WITHFILTER;
import static solver.Solver.createSolver;

public class Eval2017 {

    // ------------------------------------ Question 1 -----------------------------------------------------------------

    static void question_1_1() {
        createSolverAndPrintResult(
                resolutionPb(3),
                "Question 1 - valMax =".concat(" " + 3),
                false
        );
    }

    static void question_1_2() {
        createSolverAndPrintResult(
                resolutionPb(4),
                "Question 1 - valMax =".concat(" " + 4),
                false
        );
    }

    static void question_1_3() {
        createSolverAndPrintResult(
                resolutionPb(5),
                "Question 1 - valMax =".concat(" " + 5),
                false
        );
    }

    static void question_1_4() {
        createSolverAndPrintResult(
                resolutionPb(6),
                "Question 1 - valMax =".concat(" " + 6),
                false
        );
    }

    static void question_1_5() {
        createSolverAndPrintResult(
                resolutionPb(7),
                "Question 1 - valMax =".concat(" " + 7),
                false
        );
    }


    private static void createSolverAndPrintResult(Csp csp, String name, boolean withSolutions) {
        SearchResult result = createSolver(WITHFILTER, name, csp).solve();
        System.out.println(withSolutions ? result.toString() : result.data());
    }

    private static Csp resolutionPb(int valMax) {
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

    static void question_2_1_n_egal_10() {
        createSolverAndPrintResult(
                circuitsInferieurs(10, false),
                " Question 2." + (false ? 2 : 1) + " - nb = " + 10,
                false
        );
    }

    static void question_2_1_n_egal_100() {
        createSolverAndPrintResult(
                circuitsInferieurs(100, false),
                " Question 2." + (false ? 2 : 1) + " - nb = " + 100,
                false
        );
    }

    static void question_2_1_n_egal_1000() {
        createSolverAndPrintResult(
                circuitsInferieurs(1000, false),
                " Question 2." + (false ? 2 : 1) + " - nb = " + 1000,
                false
        );
    }

    static void question_2_1_n_egal_10000() {
        createSolverAndPrintResult(
                circuitsInferieurs(10000, false),
                " Question 2." + (false ? 2 : 1) + " - nb = " + 10000,
                false
        );
    }

    // Il me faut environ 6 secondes pour résoudre ce problème : 1 seconde de génération du problème et 5 secondes
    // (le temps afffiché dans la console) de recherche (le total étant le temps pris par le test JUnit)
    static void question_2_1_n_egal_100000() {
        createSolverAndPrintResult(
                circuitsInferieurs(100000, false),
                " Question 2." + (false ? 2 : 1) + " - nb = " + 100000,
                false
        );
    }

    static void question_2_1_n_egal_1000000() {
        createSolverAndPrintResult(
                circuitsInferieurs(1000000, false),
                " Question 2." + (false ? 2 : 1) + " - nb = " + 1000000,
                false
        );
    }


    // ------------------------------------ Question 2.2 ---------------------------------------------------------------

    static void question_2_2_n_egal_10() {
        createSolverAndPrintResult(
                circuitsInferieurs(10, true),
                " Question 2." + (true ? 2 : 1) + " - nb = " + 10,
                false
        );
    }

    static void question_2_2_n_egal_100() {
        createSolverAndPrintResult(
                circuitsInferieurs(100, true),
                " Question 2." + (true ? 2 : 1) + " - nb = " + 100,
                false
        );
    }


    static void question_2_2_n_egal_1000() {
        createSolverAndPrintResult(
                circuitsInferieurs(1000, true),
                " Question 2." + (true ? 2 : 1) + " - nb = " + 1000,
                false
        );
    }

    static void question_2_2_n_egal_10000() {
        createSolverAndPrintResult(
                circuitsInferieurs(10000, true),
                " Question 2." + (true ? 2 : 1) + " - nb = " + 10000,
                false
        );
    }

    static void question_2_2_n_egal_100000() {
        createSolverAndPrintResult(
                circuitsInferieurs(100000, true),
                " Question 2." + (true ? 2 : 1) + " - nb = " + 100000,
                false
        );
    }

    // Il me faut environ 18 secondes pour résoudre ce problème : 6 secondes de génération du problème et 12 secondes
    // (le temps afffiché dans la console) de recherche
    static void question_2_2_n_egal_1000000() {
        createSolverAndPrintResult(
                circuitsInferieurs(1000000, true),
                " Question 2." + (true ? 2 : 1) + " - nb = " + 1000000,
                false
        );
    }

    private static Csp circuitsInferieurs(int nb, boolean fermeture) {
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

    static void question3() {
        createSolverAndPrintResult(generateQ3(), "Question 3", true);
    }

    static private Csp generateQ3() {
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

    public static void main(String[] args) {
//        question_1_1();  // j'obtiens 0 solutions
//        question_1_2();  // 6 solutions
//        question_1_3();  // 141 solutions
//        question_1_4();  // 1401 solutions
//        question_1_5();  // 8751 solutions
//        question_2_1_n_egal_10();
//        question_2_1_n_egal_100();
//        question_2_1_n_egal_1000();
//        question_2_1_n_egal_10000(); // ~2 secondes
//        question_2_1_n_egal_100000(); // Il me faut environ 8 secondes pour résoudre ce problème :
        // 2 secondes de génération du problème et 6 secondes
        // (le temps afffiché dans la console) de recherche
//        question_2_1_n_egal_1000000(); // trop long
//        question_2_2_n_egal_10();
//        question_2_2_n_egal_100();
//        question_2_2_n_egal_1000();
//        question_2_2_n_egal_10000();
//        question_2_2_n_egal_100000();
//        question_2_2_n_egal_1000000();  // 12 secondes
        question3();
    }
}
