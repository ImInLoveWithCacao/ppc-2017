package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Test;

import static factories.ConstraintFactory.*;
import static factories.VariableFactory.createVariables;
import static solver.Solver.BACKTRACK;
import static solver.Solver.BRUTEFORCE;


public class Eval2016 {
    //---------------------QUESTION 1------------------------------------------------------

    static Csp generateQuestion1(int maxD){
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

        return new Csp (vars, cons);
    }

    @Test
    void question_1_n_egal_5() {
        TestTools.assertQueston1(5, 107, BRUTEFORCE, BACKTRACK);
    }

    @Test
    void question_1_n_egal_6() {
        TestTools.assertQueston1(6, 1468, BACKTRACK);
    }

//    public static void resolutionPb(int maxVal){
//        Csp csp = generateQuestion1(maxVal);
//        GenerateAndTest.resolutions(csp, 14, maxVal);
//    }
//
//
//    public static void testsQuestion1(){
//        TestsEval.resolutionPb(3);
//        TestsEval.resolutionPb(4);
//        TestsEval.resolutionPb(5); //J'obtiens 107 solutions.
//        TestsEval.resolutionPb(6); //1468 solutions.
//        TestsEval.resolutionPb(7); // 11024 solutions.
//    }
//
//
//
//    //------------------QUESTION 2---------------------------------------
//
//
//    public static Csp generateQ21(int nb){
//        Variable[] vars = new Variable[nb];
//        for (int i=0; i<nb; i++)
//            vars[i] = new Variable("x_"+i, i, 1, 10);
//
//
//
//        Constraint[] cons = new Constraint[nb+2];
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
//
//        return new Csp (vars, cons);
//    }
//
//    public static void circuitsInferieurs1(int nb){
//        Csp csp = generateQ21(nb);
//        GenerateAndTest.resolutions(csp, nb, 10);
//    }
//
//    public static void testsQuestion21(){
//        circuitsInferieurs1(10); //solutions trouv�es en 0.018s.
//        circuitsInferieurs1(100); //solutions trouv�es en 0.038s.
//        circuitsInferieurs1(1000); //solutions trouv�es en 1,29s.
//        circuitsInferieurs1(10000); //solutions trouv�es en 2 min 09s.
//    }
//
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
