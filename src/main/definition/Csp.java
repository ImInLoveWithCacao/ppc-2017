package definition;

import tools.Solution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Csp {
    private Variable[] vars;
    private Constraint[] cons;

	public Csp(Variable[] vars, Constraint[] cons) {
		this.vars = vars;
		this.cons = cons;
	}

    public Csp(Variable[] vars) {
        this(vars, new Constraint[]{});
    }

	public Variable[] getVars() {
		return vars;
	}

    private Constraint[] getConstraints() {
        return this.cons;
	}

    public Stream<Variable> streamVars() {
        return Arrays.stream(getVars());
    }

    public Stream<Variable> streamUninstantiated() {
        return streamVars().filter(Variable::isNotInstantiated);
    }

    public Domain[] cloneDomains() {
        return streamVars().map(Variable::cloneDomain).toArray(Domain[]::new);
    }

    public boolean allInstantiated() {
        return streamVars().allMatch(Variable::isInstantiated);
    }

	public boolean hasSolution() {
        return streamConstraints().allMatch(Constraint::isSatisfied);
    }

    public Stream<Constraint> relatedConstraints(Variable var) {
        return streamConstraints().filter(c -> c.affects(var));
    }

    private Stream<Constraint> streamConstraints() {
        return Arrays.stream(getConstraints());
    }

    public String toString() {
        final StringBuilder s = new StringBuilder("---Variables : \n");
        streamVars().forEach(v -> s.append(v).append("\n"));
        s.append("---Contraintes \n");
        streamConstraints().forEach(c -> s.append(c).append("\n"));
        return s.toString();
    }

	public Solution solution() {
        return new Solution(getVars());
    }
}