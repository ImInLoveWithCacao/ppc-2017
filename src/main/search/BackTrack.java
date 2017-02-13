package search;

import definition.Csp;

public class BackTrack extends BruteForce {
    BackTrack(Csp csp) {
        this("Back Track Search", csp);
    }

    BackTrack(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected boolean isNodeConsistent() {
        return csp.necessary(currentNode);
    }
}
