package solver;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;
import tools.SearchResult;

public abstract class Solver {
    public static final int BRUTEFORCE = 0;
    public static final int BACKTRACK = 1;
    public static final int WITHFILTER = 2;
    public static final int SMALLESTDOMAINS = 3;
    public static final int SMALLESTRATIO = 4;

    Csp csp;
    Variable currentNode;

    /**
     * Sauvegarde le nombre de noeuds visités, le temps écoulé, etc.
     */
    private SearchResult result;

    Solver(String name, Csp csp) {
        this.csp = csp;
        this.result = new SearchResult(name);
        result.timerStart();
    }

    public static Solver createSolver(int type, String name, Variable[] vars) {
        return createSolver(type, name, vars, new Constraint[]{});
    }

    public static Solver createSolver(int type, String name, Variable[] vars, Constraint... cons) {
        return createSolver(type, name, new Csp(vars, cons));
    }

    public static Solver createSolver(int type, String name, Csp csp) {
        switch (type) {
            case SMALLESTRATIO:
                return new SmallestRatio(name, csp);
            case SMALLESTDOMAINS:
                return new SmallestDomains(name, csp);
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
    public SearchResult solve() {
        search();
        result.timerEnd();
        return result;
    }

    protected void search() {
        Variable next;
        if ((next = choseNextVar()) != null)
            next.setDomain(saveDomainAndSearchBranch(next));
        else if (csp.hasSolution())
            saveSolution();
    }

    private void saveSolution() {
        result.addSol(csp.solution());
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

    /**
     * Instancie la variable à la valeur value, sauvegarde la visite d'un nouveau noeud,
     * puis modifie this.currentNode.
     */
    private void setCurrentNode(Variable var, Integer value) {
        var.instantiate(value);
        result.addNode();
        currentNode = var;
    }

    void coreSearch() {
        if (isNodeConsistent())
            search();
    }

    protected abstract boolean isNodeConsistent();
}
