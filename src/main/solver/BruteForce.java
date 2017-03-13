package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import tools.SearchResult;

public class BruteForce extends Solver {

    BruteForce(String name, Variable[] vars, Constraint... cons) {
        this(name, new Csp(vars, cons));
    }

    BruteForce(String name, Csp csp) {
        this.csp = csp;
        this.result = new SearchResult(name);
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
