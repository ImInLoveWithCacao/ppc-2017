package solver;

import definition.Csp;
import definition.Variable;

public class BruteForce extends Solver {

    BruteForce(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected boolean isNodeConsistent() {
        return true;
    }

    /**
     * @return la premiere variable non instanciee du csp.
     */
    @Override
    protected Variable choseNextVar() {
        return csp.streamUninstantiated().findFirst().get();
    }

    @Override
    void coreSearch() {
        if (isNodeConsistent())
            search();
    }
}
