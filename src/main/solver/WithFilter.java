package solver;

import definition.Csp;

public class WithFilter extends BackTrack {
    WithFilter(String name, Csp csp) {
        super(name, csp);
    }

    /**
     * Applique et propage les filtres, puis appelle Solver.coreSearch.
     */
    protected void coreSearch() {
        Propagator p = new Propagator(csp, currentNode);
        p.propagateFromCurrentNode();

        if (p.areArcsConsistent()) super.coreSearch();
        p.restoreDomains();
    }
}
