package solver;

import definition.Csp;
import definition.Variable;

import java.util.Comparator;

public class SmallestRatio extends WithFilter {
    SmallestRatio(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected Variable choseNextVar() {
        return csp.streamVars().filter(Variable::isNotInstantiated)
            .min(Comparator.comparingDouble(this::ratio)).get();
    }

    private double ratio(Variable var) {
        return var.getDomainSize() / ((double) csp.relatedConstraints(var).count());
    }
}
