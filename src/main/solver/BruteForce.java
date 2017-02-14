package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;

public class BruteForce extends Solver {
    BruteForce(String name, Variable[] vars) {
        this(name, vars, new Constraint[]{});
    }

    BruteForce(String name, Variable[] vars, Constraint[] cons) {
        super(name, vars, cons);
    }

    BruteForce(String name, Csp csp) {
        super("Smallest Ratio Heuristics : " + name, csp);
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
