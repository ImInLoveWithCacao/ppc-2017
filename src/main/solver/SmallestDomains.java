package solver;

import definition.Csp;
import definition.Variable;

public class SmallestDomains extends WithFilter {
    SmallestDomains(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected Variable choseNextVar() {
        return csp.smallestDomain();
    }
}
