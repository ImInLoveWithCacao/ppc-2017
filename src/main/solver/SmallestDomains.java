package solver;

import definition.Csp;
import definition.Variable;

import java.util.Comparator;

public class SmallestDomains extends WithFilter {
    SmallestDomains(String name, Csp csp) {
        super(name, csp);
    }

    @Override
    protected Variable choseNextVar() {
        return csp.streamVars().filter(Variable::isNotInstantiated)
            .min(Comparator.comparingInt(Variable::getDomainSize)).get();
    }
}
