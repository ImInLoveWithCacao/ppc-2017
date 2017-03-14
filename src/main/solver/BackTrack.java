package solver;

import definition.Csp;

public class BackTrack extends BruteForce {
    BackTrack(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected boolean isNodeConsistent() {
        return csp.necessary(currentNode);
    }
}
