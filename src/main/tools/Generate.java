package tools;

import definition.*;
import factories.VariableFactory;

public class Generate {

    private static Csp generate31(){
        Variable[] vars = VariableFactory.generateRandomVars(3, 0, 2);
        Constraint c1 = new ConstraintInf(vars[0], vars[2]);
        return new Csp(vars, new Constraint[]{c1});
    }

    public static Csp generate53(){
        Variable x0 = new Variable ("x0", 0, 3, 4);
        Variable x1 = new Variable ("x1", 1, 4, 5);
        Variable x2 = new Variable ("x2", 2, 1, 4);
        Variable x3 = new Variable ("x3", 3, 3, 4);
        Variable x4 = new Variable ("x4", 4, 3, 4);
        Constraint c1 = new ConstraintInf(x0, x3);
        Constraint c2 = new ConstraintInf(x3, x4);
        Constraint c3 = new ConstraintDiff(x2, x4);

        Variable[] vars = {x0, x1, x2, x3, x4};
        Constraint[] cons = {c1, c2, c3};
        return new Csp (vars, cons);
    }

    /**
     * @param nbVar nombre de variables
     * @param nbCons nombre de contraintes
     * @param minD minimum des domaines des variables
     * @param maxD maximum des domaines des variables
     * @return un Csp avec les paramètres souhaités
     */
    private static Csp generateRandom(int nbVar, int nbCons, int minD, int maxD){
        Variable[] vars = new Variable[nbVar];
        for (int i=0; i<nbVar; i++){
            int[] dom = Tools.randomTwo(minD, maxD);
            vars[i] = new Variable("x" + i, i, dom[0], dom[1]);
        }
        Constraint[] cons = new Constraint[nbCons];
        int[][] pairs = Tools.randomPairs(nbCons, nbVar);
        for (int i=0; i<nbCons; i++){
            int[] indexes = pairs[i];
            Variable v1 = vars[indexes[0]];
            Variable v2 = vars[indexes[1]];
            int rand = (int) Math.floor(Math.random() * 4);
            if (rand < 3) cons[i] = new ConstraintInf(v1, v2);
            else cons[i] = new ConstraintDiff(v1, v2);
        }
        return new Csp(vars, cons);
    }

    public static Csp generateMonteeEnCharge(int nbVars){
        Variable[] vars = new Variable[nbVars];
        for (int i=0; i<nbVars; i++) vars[i] = new Variable("x"+i, i, 1, 10);
        Constraint[] cons = new Constraint[nbVars];
        for (int i=0; i<nbVars-1; i++) cons[i] = new ConstraintInf(vars[i], vars[i+1]);
        cons[nbVars - 1] = new ConstraintInf(vars[nbVars-1], vars[0]);
        return new Csp(vars, cons);
    }
}
