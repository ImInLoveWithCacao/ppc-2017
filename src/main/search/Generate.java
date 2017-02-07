package search;

import definition.*;

public class Generate {
    // User variables
    public static final int BRUTEFORCE = 0;
    public static final int BACKTRACK = 1;
    public static final int BACKTRACK2 = 2;
    public static final boolean ON = true;
    public static final boolean OFF = false;
    public static final int RANDOM = 0;


    private static Csp generate31(){
        Variable x0 = new Variable ("x0", 0, 0, 2);
        Variable x1 = new Variable ("x1", 1, 0, 2);
        Variable x2 = new Variable ("x2", 2, 0, 2);
        Constraint c1 = new ConstraintInf(x0, x2);

        Variable[] vars = {x0, x1, x2};
        Constraint[] cons = {c1};
        return new Csp (vars, cons);
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

    private static void test(){
        int n = 3;
        Csp csp = generateRandom(n, n-2, 0, n);//generateMonteeEnCharge(n);//generate31();//generate53();//
        System.out.println("---------------PROLEME : \n" +  csp.toString());
        allSearchCases(csp, n);
    }

    public static void allSearchCases(Csp csp, int n) {
        String[] names = {"BruteForce", "BackTrack", "BackTrack2"};
        int l = names.length;
        Solver solver;
        SearchResult result;
        boolean filter = false;
        for (int i = 0; i < 2 * l; i++) {
            int j = i;
            if (i>=l){
                j = i-l;
                filter = true;
            }
            for (int k = 0; k < 3; k++) {
                solver = new Solver(names[j] + " n = " + n + "heuristic : " + k + ", filter : " + filter, csp);
                //result = solver.searchWithTimer(j, filter, k);
                // System.out.println(result.data() + "\n\n\n");
            }
        }
    }

    public static void testDomaines1(){
        Domain d1 = new DomainBitSet(0, 2);
        Domain d2 = d1.clone();
        d2.remove(1);
    }

    public static void testDomainIterator(){
        Domain d1 = new DomainBitSet(0, 2);
        for (Integer i : d1) System.out.println(i);
    }

    public static void main (String[] args) {
        test();
    }
}