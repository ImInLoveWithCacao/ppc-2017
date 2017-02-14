package solver;


import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;
import tools.SearchResult;

abstract class Solver {
    Csp csp;
    Variable currentNode;

    /**
     * Sauvegarde le nombre de noeuds visités, le temps écoulé, etc.
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
     * Lance la racherche
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    SearchResult searchWithTimer() {
        result.timerStart();
        search();
        result.timerEnd();
        return result;
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
     * @return true si toutes les contraintes sont satisfaites.
     */
    private boolean isSolution() {
        return csp.hasSolution();
    }

    /**
     * Instancie la variable à la valeur value, sauvegarde la visite d'un nouveau noeud,
     * puis modifie this.currentNode.
     */
    private void setCurrentNode(Variable var, Integer value) {
        var.instantiate(value);
        result.addNode();
        currentNode = var;
    }

    private void saveSolution() {
        result.addSol(csp.solution());
    }

    // ----------------------------------------------- Algo ------------------------------------------------------------

    /**
     * Methode initiale lancée lors d'une recherche.
     */
    private void search() {
        if (!csp.allInstanciated())
            fromNewVariable();
        else if (isSolution())
            saveSolution();
    }

    /**
     * Choisit la prochaine variable à instancier, puis parcourt le domaine de la variable.
     */
    private void fromNewVariable() {
        Variable var = choseNextVar();
        Domain d = saveAndGoThroughDomain(var);
        var.setDomain(d);
    }

    /**
     * Sauvegarde le domaine de la variable, puis continue la recherche.
     *
     * @return le domaine de var avant la suite de la recherche.
     */
    private Domain saveAndGoThroughDomain(Variable var) {
        Domain d = var.getDomain().clone();
        for (Integer i : d) {       // Exploration à partir de var.
            setCurrentNode(var, i);
            coreSearch();
        }
        return d;
    }

    /**
     * Continue la recherche si possible.
     */
    protected void coreSearch() {
        if (isNodeConsistent())
            search();
    }

}
