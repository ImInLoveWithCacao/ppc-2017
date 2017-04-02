package solver;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;
import tools.SearchResult;

public abstract class Solver {
    static final int BRUTEFORCE = 0;
    static final int BACKTRACK = 1;
    static final int WITHFILTER = 2;

    Csp csp;
    Variable currentNode;

    /**
     * Sauvegarde le nombre de noeuds visités, le temps écoulé, etc. YOYOTEST
     */
    private SearchResult result;

    Solver(String name, Csp csp) {
        this.csp = csp;
        this.result = new SearchResult(name);
    }

    static Solver createSolver(int type, String name, Variable[] vars) {
        return createSolver(type, name, vars, new Constraint[]{});
    }

    static Solver createSolver(int type, String name, Variable[] vars, Constraint... cons) {
        return createSolver(type, name, new Csp(vars, cons));
    }

    static Solver createSolver(int type, String name, Csp csp) {
        switch (type) {
            case WITHFILTER:
                return new WithFilter(name, csp);
            case BACKTRACK:
                return new BackTrack(name, csp);
            case BRUTEFORCE:
                return new BruteForce(name, csp);
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

    protected void search() {
        if (!csp.allInstantiated())
            fromNewVariable();
        else if (csp.hasSolution())
            saveSolution();
    }

    private void saveSolution() {
        result.addSol(csp.solution());
    }

    /**
     * Choisit la prochaine variable à instancier puis parcourt son domaine.
     */
    private void fromNewVariable() {
        Variable var = choseNextVar();
        Domain d = saveDomainAndSearchBranch(var);
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
    private Domain saveDomainAndSearchBranch(Variable var) {
        Domain clone = var.cloneDomain();
        clone.forEach(i -> instantiateAndSearchBranch(var, i));
        return clone;
    }

    private void instantiateAndSearchBranch(Variable var, Integer value) {
        setCurrentNode(var, value);
        coreSearch();
    }

    abstract void coreSearch();

    /**
     * Instancie la variable à la valeur value, sauvegarde la visite d'un nouveau noeud,
     * puis modifie this.currentNode.
     */
    private void setCurrentNode(Variable var, Integer value) {
        var.instantiate(value);
        result.addNode();
        currentNode = var;
    }
}
