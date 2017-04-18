package solver;

import definition.Csp;
import definition.Variable;

import static java.util.Comparator.comparingDouble;

public class SmallestRatio extends WithFilter {
    SmallestRatio(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected Variable choseNextVar() {
        return csp.streamUninstantiated().min(comparingDouble(this::ratio)).orElse(null);
    }

    private double ratio(Variable var) {
        return var.getDomainSize() / ((double) csp.relatedConstraints(var).count());
    }
}