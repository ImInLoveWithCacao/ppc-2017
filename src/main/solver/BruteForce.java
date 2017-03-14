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

    @Override
    protected Variable choseNextVar() {
        return csp.randomVar();
    }
}
