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

        try {
            p.propagate();
            super.coreSearch();
        } catch (EmptyVariableException ignored) {
        } finally {
            p.restoreDomains();
        }
    }
}
