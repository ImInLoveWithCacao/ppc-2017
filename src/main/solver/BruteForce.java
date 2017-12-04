package solver;

import definition.Csp;
import definition.Variable;

public class BruteForce extends Solver {

    BruteForce(String name, Csp csp) {
        super(name, csp);
    }

    /**
     * Vérifie que les conditions nécessaires aux contraintes sont satisfaite pour ce noeud et pour ce type de solver.
     */
    @Override
    protected boolean isNodeConsistent() {
        return true;
    }

    /**
     * @return la premiere variable non instanciee du csp.
     */
    @Override
    protected Variable choseNextVar() {
        return csp.streamUninstantiated().findFirst().orElse(null);
    }
}
