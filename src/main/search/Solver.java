package search;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    private Csp csp;
    private Queue<Constraint> activeConstraints;
    /**
     * Stocke les données relatives à la recherche jusqu'à ce stade.
     */
    private SearchResult result;


    // ---------------------------------------------- Construction -----------------------------------------------------

    public Solver(String name, Csp csp) {
        this.csp = csp;
        activeConstraints = new LinkedList<Constraint>();
        result = new SearchResult(name);
    }

    public Solver(Csp csp) {
        this("search", csp);
    }

    public Solver(String name, Variable[] vars, Constraint[] cons) {
        this(name, new Csp(vars, cons));
    }

    public Solver(Variable[] vars, Constraint[] cons) {
        this("search", vars, cons);
    }

    public Solver(String name, Variable[] vars) {
        this(name, new Csp(vars));
    }

    public Solver(Variable[] vars) {
        this("search", vars);
    }

    // ---------------------------------------------- Accessors --------------------------------------------------------
    private boolean canStillPropagate() {
        return !activeConstraints.isEmpty();
    }


    // ---------------------------------------------- API --------------------------------------------------------------

    /**
     * Execute wrapSearch en mode bruteforce et enregistre les resultats
     *
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    public SearchResult searchBasic() {
        return wrapSearch(0, false, 0);
    }

    /**
     * Execute wrapSearch avec les options les plus rapides et enregistre les resultats
     *
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    public SearchResult searchFastest() {
        return wrapSearch(2, true, 2);
    }

    /**
     * Methode generique pour lancer une recherche
     * @param level Spécifie le niveau d'affinage (bruteforce || satisfy || necessary)
     * @param filter true pour activer le filtrage
     * @param heuristic à chiosir
     * @return Un object contentant les données relatives à la recherche (temps d'execution, resultats, etc).
     */
    public SearchResult wrapSearch(int level, boolean filter, int heuristic) {
        result.timerStart();
        search(level, filter, heuristic);
        result.timerEnd();
        return result;
    }


    // ---------------------------------------------- Private ----------------------------------------------------------

    /**
     * Methode initiale lancée lors d'une recherche. Instancie une variable puis continue
     * en foncction des paramétres.
     */
    private void search(int level, boolean filter, int heuristic) {
        Variable var = choseNextVar(heuristic);

        if (var == null) checkSolution(level);
        else {
            Domain d = var.getDomain().clone();

            for (Integer i : d) { //Exploration à partir de var.
                var.instantiate(i);
                result.addNode();
                if (filter) coreWithFilter(var, level, heuristic);
                else coreSearch(var, level, false, heuristic);
            }
            var.setDomain(d);
        }
    }

    /**
     * Applique et propage les filtres, puis appelle coreSearch.
     */
    private void coreWithFilter(Variable var, int level, int heuristic) {
        Domain[] domaines = csp.cloneDomains(); // Sauvegarde des domaines avant filtrage

        boolean[] propagation = propagate(var); // Propagation à partir de var.
        if (!propagation[0]) coreSearch(var, level, true, heuristic); // Contient l'appel récursif à search().

        int nbVars = csp.getNbVars();
        for (int i = 0; i < nbVars; i++) // Restitution des domaines initiaux.
            if (propagation[i + 1]) csp.getVars()[i].setDomain(domaines[i]);
    }

    /**
     * Le coeur de la recherche qui teste si ce noeud permet de continuer la recherche
     * @param var la variable qui vient d'être instanciée.
     */
    private void coreSearch(Variable var, int level, boolean filter, int heuristic) {
        if (testThisNode(var, level)) {
            if (csp.allInstanciated()) checkSolution(level);
            else search(level, filter, heuristic); // Appel récursif.
        }
    }

    /**
     * @param var Le noeud (variable instanciée) sur lequel on se trouve actuellement
     * @return true ssi ce noeud passe le test.
     */
    private boolean testThisNode(Variable var, int level) {
        Constraint[] cons = csp.getConstraintsAsArray(var);
        boolean test;
        switch (level) {
            case 1:
                test = csp.satisfied(cons);
                break;
            case 2:
                test = csp.necessary(cons); break;
            default : test = true;
        }
        return test;
    }

    /**
     * Appelée quand toutes les cariables sont instanciées.
     */
    private void checkSolution(int level) {
        boolean sol = true;
        if (level == 0) sol = csp.hasSolution();
        if (sol) result.addSol(csp.solution()); // Ajout d'une solution.
    }

    /**
     * @return la prochaine variable à instancier en fonction de l'heuristique choisie
     */
    private Variable choseNextVar(int heuristc) {
        switch (heuristc) {
            case 1:
                return csp.smallestVar();
            case 2:
                return csp.smallestRatio();
            default:
                return csp.randomVar();
        }
    }

    /**
     * Lance le filtrage du Csp avec pour point de départ la variable var. Et effectue la
     * propagation à travers les contraintes qui concernet les variables dont le domaine
     * a été réduit par un filtrage.
     *
     * @param var La variable qui vient d'être instanciée.
     * @return un tableau de getNbVars() + 1 booléens. Le premier vaut false si le domaine
     * de l'une des variables a été vidé. Les suivants d'indice i+1 valent true si le domaine
     * de la ième variable a changé.
     */
    public boolean[] propagate(Variable var) {
        boolean[] res = prepareResp();
        activeConstraints.addAll(csp.getConstraintsAsArrayList(var));

        while (canStillPropagate() && res[0]) res = applyFirstFilter(res);
        return res;
    }

    /**
     * @return Un tableau de nb + 1 boolean. Le premier vaut true et les autres false.
     */
    private boolean[] prepareResp() {
        int nb = csp.getNbVars();
        boolean[] res = new boolean[nb + 1];
        res[0] = true;
        for (int i = 1; i < nb + 1; i++) res[i] = false;
        return res;
    }

    private boolean[] applyFirstFilter(boolean[] res) {
        Constraint c = activeConstraints.poll();
        boolean[] filter = c.filter();
        if (filter[0]) res[0] = false;
        else {
            int len = filter.length;
            for (int i = 1; i < len; i++)
                if (filter[i]) {
                    Variable vi = c.getVars()[i - 1];
                    res[vi.getInd() + 1] = true;
                    ArrayList<Constraint> cons1 = csp.getConstraintsAsArrayList(vi);
                    for (Constraint c1 : cons1)
                        if (!c1.equals(c) && !activeConstraints.contains(c1)) activeConstraints.add(c1);
                }
        }
        return res;
    }

}
