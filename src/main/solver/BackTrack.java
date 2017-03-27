package solver;

import definition.Constraint;
import definition.Csp;

public class BackTrack extends BruteForce {
    BackTrack(String name, Csp csp) {
        super(name, csp);
    }

    /**
     * isNecessary
     */
    @Override
    protected boolean isNodeConsistent() {
        return csp.relatedConstraints(currentNode).allMatch(Constraint::isNecessary);
    }
}
