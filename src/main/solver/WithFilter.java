package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;

public class WithFilter extends BackTrack {
    WithFilter(Csp csp) {
        this("WithFilter Search", csp);
    }

    WithFilter(String name, Csp csp) {
        super(name, csp);
    }

    WithFilter(String name, Variable[] vars, Constraint[] cons) {
        super(name, vars, cons);
    }

    /**
     * Applique et propage les filtres, puis appelle Solver.coreSearch.
     */
    protected void coreSearch(Variable var, Integer value) {
        Propagator p = new Propagator(csp, currentNode);
        p.propagateFromCurrentNode();

        if (p.areArcsConsistent()) super.coreSearch(var, value);
        p.restoreDomains();
    }
}
