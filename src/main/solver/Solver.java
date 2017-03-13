package solver;


import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;
import tools.SearchResult;


abstract class Solver {
    public final static int BRUTEFORCE = 0;
    public static final int BACKTRACK = 1;
    public static final int WITHFILTER = 2;

    Csp csp;
    Variable currentNode;

    /**
     * Sauvegarde le nombre de noeuds visités, le temps écoulé, etc.
     */
    SearchResult result;

    static Solver createSolver(int type, String name, Variable[] vars) {
        return createSolver(name, type, vars);
    }

    static Solver createSolver(String name, int type, Variable[] vars, Constraint... cons) {
        switch (type) {
            case WITHFILTER:
                return new WithFilter(name, vars, cons);
            case BACKTRACK:
                return new BackTrack(name, vars, cons);
            case BRUTEFORCE:
                return new BruteForce(name, vars, cons);
            default:
                throw new IllegalArgumentException("type " + type + " is not valid");
        }
    }

    /**
     * Lance la recherche
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    SearchResult solve() {
        result.timerStart();
        search();
        result.timerEnd();
        return result;
    }

    private void search() {
        if (!csp.allInstanciated())
            fromNewVariable();
        else if (isSolution())
            saveSolution();
    }

    /**
     * Appelée quand toutes les Variables sont instanciées.
     * @return true si toutes les contraintes sont satisfaites.
     */
    private boolean isSolution() {
        return csp.hasSolution();
    }

    private void saveSolution() {
        result.addSol(csp.solution());
    }

    /**
     * Choisit la prochaine variable à instancier puis parcourt son domaine.
     */
    private void fromNewVariable() {
        Variable var = choseNextVar();
        Domain d = saveAndGoThroughDomain(var);
        var.setDomain(d);
    }

    /**
     * @return la prochaine variable à instancier en fonction de l'heuristique choisie.
     */
    protected abstract Variable choseNextVar();

    /**
     * Sauvegarde le domaine de la variable puis continue la recherche.
     * @return le domaine de var avant la suite de la recherche.
     */
    private Domain saveAndGoThroughDomain(Variable var) {
        Domain clone = var.getDomain().clone();
        for (Integer i : clone)
            coreSearch(var, i);
        return clone;
    }

    void coreSearch(Variable var, Integer value) {
        setCurrentNode(var, value);
        if (isNodeConsistent())
            search();
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

    /**
     * @return true ssi chaque variable a encore au moins une valeur réalisable (après instanciation).
     */
    protected abstract boolean isNodeConsistent();

}
