package search;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;

class Solver {
    private Csp csp;

    /**
     * Stocke les données relatives à la recherche jusqu'à ce stade.
     */
    private SearchResult result;

    /**
     * Le noeud où se situe actuellement la recherche. Une variable instanciée.
     */
    private Variable currentNode;

    /**
     * Le niveau d'affinage (bruteforce || satisfy || necessary)
     */
    private int level;
    private boolean filter;
    private int heuristic;


    // ---------------------------------------------- Constructors -----------------------------------------------------

    Solver(String name, Csp csp) {
        this.csp = csp;
        result = new SearchResult(name);
    }

    Solver(Csp csp) {
        this("search", csp);
    }

    Solver(String name, Variable[] vars, Constraint[] cons) {
        this(name, new Csp(vars, cons));
    }

    Solver(Variable[] vars, Constraint[] cons) {
        this("search", vars, cons);
    }

    Solver(String name, Variable[] vars) {
        this(name, new Csp(vars));
    }

    Solver(Variable[] vars) {
        this("search", vars);
    }


    // ---------------------------------------------- API --------------------------------------------------------------

    /**
     * Execute searchWithTimer en mode bruteforce et enregistre les resultats
     *
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    SearchResult bruteForce() {
        return searchWithTimer(0, false, 0);
    }

    /**
     * Execute searchWithTimer avec les options les plus rapides et enregistre les resultats
     *
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    SearchResult searchFastest() {
        return searchWithTimer(2, true, 1);
    }


    // ---------------------------------------------- Accessors --------------------------------------------------------

    private void setCurrentNode(Variable var, Integer value) {
        var.instantiate(value);
        result.addNode();
        currentNode = var;
    }


    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Methode generique pour lancer une recherche
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    private SearchResult searchWithTimer(int level, boolean filter, int heuristic) {
        this.level = level;
        this.filter = filter;
        this.heuristic = heuristic;

        result.timerStart();
        search();
        result.timerEnd();
        return result;
    }

    /**
     * Methode initiale lancée lors d'une recherche. Instancie une variable puis continue
     * en foncction des paramétres.
     */
    private void search() {
        Variable var = choseNextVar();

        if (var == null) checkSolution();
        else {
            Domain d = var.getDomain().clone();
            for (Integer i : d) searchFromNode(var, i); // Exploration à partir de var.
            var.setDomain(d);
        }
    }

    /**
     * @return la prochaine variable à instancier en fonction de l'heuristique choisie
     */
    private Variable choseNextVar() {
        switch (heuristic) {
            case 1:
                return csp.smallestVar();
            case 2:
                return csp.smallestRatio();
            default:
                return csp.randomVar();
        }
    }

    /**
     * Continue la rechere à partir du noeud correspondant à var instanciée à la valeur i.
     */
    private void searchFromNode(Variable var, Integer value) {
        setCurrentNode(var, value);
        if (filter) coreWithFilter();
        else coreSearch();
    }

    /**
     * Applique et propage les filtres, puis appelle coreSearch.
     */
    private void coreWithFilter() {
        Propagator p = new Propagator(csp, currentNode);
        boolean[] propagation = p.lauchPropagation(); // Propagation à partir de var.

        if (!propagation[0]) coreSearch(); // Contient l'appel récursif à search().
        p.restoreDomains();
    }

    /**
     * Le coeur de la recherche qui teste si ce noeud permet de continuer la recherche
     */
    private void coreSearch() {
        if (testThisNode()) {
            if (csp.allInstanciated()) checkSolution();
            else search();
        }
    }

    /**
     * @return true ssi ce noeud passe le test.
     */
    private boolean testThisNode() {
        switch (level) {
            case 1:
                return csp.satisfied(currentNode);
            case 2:
                return csp.necessary(currentNode);
            default:
                return true;
        }
    }

    /**
     * Appelée quand toutes les cariables sont instanciées.
     */
    private void checkSolution() {
        boolean sol = true;
        if (level == 0) sol = csp.hasSolution();
        if (sol) result.addSol(csp.solution()); // Ajout d'une solution.
    }

}
