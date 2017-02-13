package search;


import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;

abstract class Solver {
    Csp csp;

    /**
     * Le noeud où se situe actuellement la recherche. Une variable instanciée.
     */
    Variable currentNode;

    /**
     * Stocke les données relatives à la recherche jusqu'à ce stade.
     */
    private SearchResult result;


    Solver(String name, Variable[] vars, Constraint[] cons) {
        this(name, new Csp(vars, cons));
    }

    Solver(String name, Csp csp) {
        this.csp = csp;
        result = new SearchResult(name);
    }

    // ---------------------------------------------- API --------------------------------------------------------------

    /**
     * Methode generique pour lancer une recherche
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    SearchResult searchWithTimer() {
        result.timerStart();
        search();
        result.timerEnd();
        return result;
    }


    /**
     * Methode initiale lancée lors d'une recherche. Instancie une variable puis continue
     * en foncction des paramétres.
     */
    void search() {
        if (!csp.allInstanciated())
            fromNewVariable();
        else if (isSolution())
            addSolution();
    }

    // ---------------------------------------------- Accessors --------------------------------------------------------

    /**
     * @return la prochaine variable à instancier en fonction de l'heuristique choisie
     */
    protected abstract Variable choseNextVar();

    /**
     * @return true ssi chaque variable a encore au moins une valeur réalisable (après instanciation).
     */
    protected abstract boolean isNodeConsistent();

    /**
     * Appelée quand toutes les Variables sont instanciées.
     *
     * @return true si toutes les contraintes sont satisfaites.
     */
    private boolean isSolution() {
        return csp.hasSolution();
    }

    // ----------------------------------------------- Algo ------------------------------------------------------------

    private void fromNewVariable() {
        Variable var = choseNextVar();
        Domain d = var.getDomain().clone();
        for (Integer i : d) fromNewNode(var, i); // Exploration à partir de var.
        var.setDomain(d);
    }

    /**
     * Continue la rechere à partir du noeud correspondant à var instanciée à la valeur i.
     */
    private void fromNewNode(Variable var, Integer value) {
        setCurrentNode(var, value);
        coreSearch();
    }

    private void setCurrentNode(Variable var, Integer value) {
        var.instantiate(value);
        result.addNode();
        currentNode = var;
    }

    /**
     * Continue la recherche si possible.
     */
    protected void coreSearch() {
        if (isNodeConsistent())
            search();
    }

    private void addSolution() {
        result.addSol(csp.solution());
    }
}
