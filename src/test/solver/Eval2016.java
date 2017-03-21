package solver;

import org.junit.jupiter.api.Test;

import static solver.Solver.BACKTRACK;
import static solver.Solver.WITHFILTER;
import static solver.TestUtils.assertQuestion21;
import static solver.TestUtils.assertQueston1;


public class Eval2016 {
    //---------------------QUESTION 1------------------------------------------------------

    @Test
    void question_1_n_egal_5() {
        assertQueston1(5, 107, BACKTRACK, WITHFILTER);
    }

    @Test
    void question_1_n_egal_6() {
        assertQueston1(6, 1468, WITHFILTER);
    }

    @Test
    void question1_n_egal_7() {
        assertQueston1(7, 11024, WITHFILTER);
    }

    //------------------QUESTION 2---------------------------------------

    @Test
    void question_2_1_n_egal_10() {
        assertQuestion21(10);
    }

    @Test
    void question_2_1_n_egal_100() {
        assertQuestion21(100);
    }

    @Test
    void question_2_1_n_egal_1000() {
        assertQuestion21(1000);
    }

    @Test
    void question_2_1_n_egal_10000() {
        assertQuestion21(10000);
    }

//    public static Csp generateQ22(int nb){
//        Variable[] vars = new Variable[nb];
//        for (int i=0; i<nb; i++)
//            vars[i] = new Variable("x_"+i, i, 1, 10);
//
//        Constraint[] cons = new Constraint[nb + 3];
//
//        int unTiers = (int) Math.floor(nb/3.0);
//        for (int i=0; i<unTiers-1; i++)
//            cons[i] = new ConstraintInfEg(vars[i], vars [i+1]);
//        cons[unTiers - 1] = new ConstraintInfEg(vars[unTiers-1], vars[0]);
//
//        int deuxTiers = (int) Math.floor(2.0*nb/3.0);
//        for (int i=unTiers; i<deuxTiers - 1; i++)
//            cons[i] = new ConstraintInfEg(vars[i], vars[i+1]);
//        cons[deuxTiers - 1] = new ConstraintInfEg(vars[deuxTiers - 1], vars[unTiers]);
//
//        for (int i=deuxTiers; i<nb-1; i++)
//            cons[i] = new ConstraintInfEg(vars[i], vars[i+1]);
//        cons[nb-1] = new ConstraintInfEg(vars[nb-1], vars[deuxTiers]);
//
//        cons[nb] = new ConstraintInf(vars[0], vars[unTiers]);
//        cons[nb+1] = new ConstraintInf(vars[unTiers], vars[deuxTiers]);
//        cons[nb+2] = new ConstraintInf(vars[nb-1], vars[0]);
//
//        return new Csp (vars, cons);
//    }
//
//    public static void circuitsInferieurs2(int nb){
//        Csp csp = generateQ22(nb);
//        GenerateAndTest.resolutions(csp, nb, 10);
//    }
//
//    public static void testsQuestion22(){
//        circuitsInferieurs2(10); //0.0042s.
//        circuitsInferieurs2(100); //0.0086 s.
//        circuitsInferieurs2(1000); //0.076s.
//        circuitsInferieurs2(10000); //4.83s.
//        //circuitsInferieurs2(100000); //21 min 41s.
//    }
//
//
//
//    //-------------QUESTION 3-------------------------------------
//
//    public static Csp generateQ3(){
//        Variable x = new Variable ("x", 0, 1, 5);
//        Variable y = new Variable ("y", 1, 6, 10);
//        Variable m = new Variable ("m", 2, 2, 4);
//        Constraint c = new ConstraintMin(m, x, y);
//        Variable[] vars = new Variable[]{m, x, y};
//        Constraint[] cons = new Constraint[]{c};
//        return new Csp(vars, cons);
//    }
//
//    public static void testsQuestion3(){
//        Csp csp = generateQ3();
//        GenerateAndTest.resolutions(csp, 3, 10);
//    }
}
