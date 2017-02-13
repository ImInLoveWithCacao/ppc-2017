package search;

import definition.Csp;

public class Filtered extends BackTrack {
    Filtered(Csp csp) {
        this("Filtered Search", csp);
    }

    Filtered(String name, Csp csp) {
        super(name, csp);
    }

    /**
     * Applique et propage les filtres, puis appelle coreSearch.
     */
    protected void coreSearch() {
        Propagator p = new Propagator(csp, currentNode);
        p.lauchPropagation(); // Propagation à partir de var.

        if (p.areArcsConsistent()) super.coreSearch(); // Contient l'appel récursif à search().
        p.restoreDomains();
    }
}
