package search;

import definition.*;
import testsEval.*;

/**
 * Created by IntelliJ IDEA.
 * User: xlorca
 */
public class GenerateAndTest {
	public static final int BRUTEFORCE = 0;
	public static final int BACKTRACK = 1;
	public static final int BACKTRACK2 = 2;
	public static final boolean ON = true;
	public static final boolean OFF = false;
	public static final int RANDOM = 0;
	
	
	/**
	 * Methode initiale lancée lors d'une recherche. Instancie une variable puis continue
	 * en foncction des paramètres.
	 * @param level Spécifie le niveau d'affinage (bruteforce, satisfaction, nécessité)
	 * @param filter 
	 * @param heuristic 
	 * @param csp problème à résoudre.
	 * @param result les données relatives à la recherche jusqu'à ce stade.
	 * @return les données acutalisées.
	 */
	public static SearchResult search (int level, boolean filter, int heuristic, 
			Csp csp, SearchResult result) {
		
		SearchResult res = new SearchResult(result);
	
		Variable var = csp.randomVar();//Choix heuristique?
		Domain d = var.getDomain().clone();
	
		for (Integer i : d) { //Exploration à partir de var.
			var.instantiate(i);
			res.addNode();
			if (filter) res = coreWithFilter(var, level, heuristic, csp, res);
			else res = coreSearch(var, level, false, true, heuristic, csp, res);
		}
		
		var.setDomain(d);
		return res;
	}

	/**
	 * Applique et propage le filtrage, puis appelle coreSearch
	 * @param var
	 * @param level
	 * @param heuristic
	 * @param csp
	 * @param result
	 * @return
	 */
	public static SearchResult coreWithFilter(Variable var, int level, int heuristic,
			Csp csp, SearchResult result){
		
		int nbVars = csp.getNbVars();
		Domain[] domaines = new Domain[nbVars]; //Sauvegarde des domaines avant filtrage.
		for (int i=0; i<nbVars; i++) domaines[i] = csp.getVars()[i].getDomain().clone();
		
		boolean test = true;
		boolean[] propage = csp.propagate(var); //Propagation à partir de var.
		test = propage[0];
		
		//Contient l'appel récursif à search().
		SearchResult res = coreSearch(var, level, true, test, heuristic, csp, result);
		
		for (int i=0; i<nbVars; i++)//Restitution des domaines initiaux.
				 if (propage[i+1]) csp.getVars()[i].setDomain(domaines[i]);
		return res;
	}

	/**
	 * 
	 * @param var la variable qui vient d'être instanciée.
	 * @param level 
	 * @param filter 
	 * @param test false ssi filter && un ensemble a été vidé.
	 * @param heuristic
	 * @param csp 
	 * @param result 
	 * @return 
	 */
	public static SearchResult coreSearch(Variable var, int level, boolean filter, 
			boolean test, int heuristic, Csp csp, SearchResult result){
		
		SearchResult res = new SearchResult(result);
		Constraint[] cons = csp.getConstraints(var);
		switch(level) {
			case 1  : test = test && csp.satisfied(cons); break;
			case 2  : test = test && csp.necessary(cons); break;
			default : test = true;
		}
		if (test) {
			if (csp.allInstanciated()) {
				boolean sol = true;
				if (level == 0) sol = csp.hasSolution();
				if (sol) res.addSol(csp.solution()); //ajout d'une solution.
			}
			else res = search(level, filter, heuristic, csp, res);//appel récursif.
		}
		return res;
	}
	
	
	
	
	
	public static Csp generate31(){
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
	 * @returnun Csp avec les paramètres souhaités
	 */
	public static Csp generateRandom(int nbVar, int nbCons, int minD, int maxD){
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
		Csp csp = new Csp(vars, cons);
		return csp;
	}

	public static Csp generateMonteeEnCharge(int nbVars){
		Variable[] vars = new Variable[nbVars];
		for (int i=0; i<nbVars; i++) vars[i] = new Variable("x"+i, i, 1, 10);
		Constraint[] cons = new Constraint[nbVars];
		for (int i=0; i<nbVars-1; i++) cons[i] = new ConstraintInf(vars[i], vars[i+1]);
		cons[nbVars - 1] = new ConstraintInf(vars[nbVars-1], vars[0]);
		return new Csp(vars, cons);
	}
	
	public static void test(){
		int n = 3;
		Csp csp = generateRandom(n, n-2, 0, n);//generateMonteeEnCharge(n);//generate31();//generate53();//
		System.out.println("---------------PROLEME : \n" +  csp.toString());
		resolutions(csp, n, 10);
	}
	
	public static void resolutions (Csp csp, int n, int maxVal){
		System.out.println("ok génération");
		String[] names = {"BruteForce", "BackTrack", "BackTrack2"};
		int l = names.length;
		SearchResult result;
		boolean filter = false;
		for (int i = l+2; i<2*l; i++){
			int j = i; 
			if (i>=l){
				j = i-l;
				filter = true;
			}
			result = new SearchResult (names[j] + " n = " + n + ", maxVal = " + maxVal 
					+ ", filter : " + filter);
			result.timerStart();
			result = search(j, filter, 0, csp, result);
			result.timerEnd();
			System.out.println(result.data() + "\n\n\n");	
		}
	}

	public static void testFilter(){
		Csp csp = generate31();
		System.out.println(csp.toString());
		Variable var = csp.getVars()[0];
		var.instantiate(5);
		csp.propagate(csp.getVars()[0]);
		System.out.println(csp.solution());
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

	public static void main (String[] args){
		//test();
		//TestsEval.testsQuestion1();
		TestsEval.testsQuestion21();
		//TestsEval.testsQuestion22();
		//TestsEval.testsQuestion3();
	}

}
