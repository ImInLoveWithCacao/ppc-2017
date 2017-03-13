package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;

public class BackTrack extends BruteForce {
    BackTrack(Csp csp) {
        this("Back Track Search", csp);
    }

    BackTrack(String name, Csp csp) {
        super(name, csp);
    }

    BackTrack(String name, Variable[] vars, Constraint[] cons) {
        super(name, vars, cons);
    }

    @Override
    protected boolean isNodeConsistent() {
        return csp.necessary(currentNode);
    }
}
