package solver;

import definition.Csp;
import definition.Variable;

public class SmallestRatio extends WithFilter {
    SmallestRatio(Csp csp) {
        this("Smallest Ratio Heuristics", csp);
    }

    SmallestRatio(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected Variable choseNextVar() {
        return csp.smallestRatio();
    }
}
