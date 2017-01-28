package search;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;

public class Solver {
    /**
     * Methode initiale lancée lors d'une recherche. Instancie une variable puis continue
     * en foncction des paramétres.
     * @param level Spécifie le niveau d'affinage (bruteforce, satisfaction, nécessité)
     * @param filter true pour activer le filtrage
     * @param heuristic à chiosir
     * @param csp problème à résoudre.
     * @param result les données relatives à la recherche jusqu'à ce stade.
     * @return les données acutalisées.
     */
    public static SearchResult search (int level, boolean filter, int heuristic,
                                       Csp csp, SearchResult result) {

        SearchResult res = new SearchResult(result);
        Variable var = csp.randomVar();//Choix heuristique ici?

        if (var == null) res = checkSolution(csp, level, res);
        else {
            Domain d = var.getDomain().clone();

            for (Integer i : d) { //Exploration à partir de var.
                var.instantiate(i);
                res.addNode();
                if (filter) res = coreWithFilter(var, level, heuristic, csp, res);
                else res = coreSearch(var, level, false, true, heuristic, csp, res);
            }
            var.setDomain(d);
        }

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
    private static SearchResult coreWithFilter(Variable var, int level, int heuristic,
                                              Csp csp, SearchResult result){

        int nbVars = csp.getNbVars();
        Domain[] domaines = new Domain[nbVars]; //Sauvegarde des domaines avant filtrage.
        for (int i=0; i<nbVars; i++) domaines[i] = csp.getVars()[i].getDomain().clone();

        boolean test = true;
        boolean[] propage = csp.propagate(var); //Propagation à partir de var.
        test = propage[0];

        //Contient l'appel récursif à search2().
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
    private static SearchResult coreSearch(Variable var, int level, boolean filter,
                                          boolean test, int heuristic, Csp csp, SearchResult result){

        SearchResult res = new SearchResult(result);
        Constraint[] cons = csp.getConstraintsAsArray(var);
        switch(level) {
            case 1  : test = test && csp.satisfied(cons); break;
            case 2  : test = test && csp.necessary(cons); break;
            default : test = true;
        }
        if (test) {
            if (csp.allInstanciated()) res = checkSolution(csp, level, res);
            else res = search(level, filter, heuristic, csp, res); //appel récursif.
        }
        return res;
    }

    private static SearchResult checkSolution(Csp csp, int level, SearchResult res) {
        SearchResult newRes = new SearchResult(res);
        boolean sol = true;
        if (level == 0) sol = csp.hasSolution();
        if (sol) newRes.addSol(csp.solution()); //ajout d'une solution.
        return newRes;
    }
}
