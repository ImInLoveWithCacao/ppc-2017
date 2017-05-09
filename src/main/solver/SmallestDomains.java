package solver;

import definition.Csp;
import definition.Variable;

import static java.util.Comparator.comparingInt;

public class SmallestDomains extends WithFilter {
    SmallestDomains(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected Variable choseNextVar() {
        return csp.streamUninstantiated().min(comparingInt(Variable::getDomainSize)).orElse(null);
    }
}
