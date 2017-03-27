package definition;

import tools.Solution;

import java.util.Arrays;
import java.util.stream.Stream;

import static definition.factories.VariableFactory.createOneVar;


public class Csp {
    /**
     * L'ensemble des variables du CSP. Note: les domaines sont connus au travers des variables
     */
    private Variable[] vars;

    /**
     * L'ensemble des contraintes du CSP
     */
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

	public int getNbVars() {
		return getVars().length;
	}

    private Constraint[] getConstraints() {
        return this.cons;
	}

    /**
     * @return la premiere variable non instanciee du csp.
     */
    public Variable randomVar() {
		for (Variable v : this.vars) if (!v.isInstantiated()) return v;
		return null;
	}

    public Variable smallestDomain() {
        Variable smallest = createOneVar(-1, 0, 99999);
        for (Variable v : getVars())
            if (!v.isInstantiated()
                    && v.getDomainSize() < smallest.getDomainSize())
                smallest = v;
        return smallest;
    }


    public Variable smallestRatio() {
        Variable smallest = createOneVar(-1, 0, 99999);
        for (Variable v : this.vars)
            if (!v.isInstantiated()
                    && ratio(v) < ratio(smallest))
                smallest = v;
        return smallest;
    }

    private double ratio(Variable var) {
        return var.getDomainSize() / ((double) getNbConstraints(var));
    }

    public Domain[] cloneDomains() {
        return Arrays.stream(getVars()).map(Variable::cloneDomain).toArray(Domain[]::new);
    }

	/**
	 * @return true ssi toutes les variables sont instanciees.
	 */
	public boolean allInstanciated() {
        for (Variable v : this.vars)
            if (!v.isInstantiated()) return false;
        return true;
	}

	/**
     * @return true vrai l'ensemble des contraintes du CSP est verifié.
     */
	public boolean hasSolution() {
        for (Constraint c : getConstraints())
            if (!c.isSatisfied()) return false;
        return allInstanciated();
	}

    private int getNbConstraints(Variable var) {
        return getConstraintsAsArray(var).length;
    }

	/**
     * @return un array contenant les constraintes du Csp concernées par var.
     */
    private Constraint[] getConstraintsAsArray(Variable var) {
        return relatedConstraints(var).toArray(Constraint[]::new);
    }

    public Stream<Constraint> relatedConstraints(Variable var) {
        return Arrays.stream(getConstraints()).filter(c -> c.affects(var));
    }


    /**
     * @param node reongzoeiroizrnvoizenzo
     * @return
     */
    public boolean necessary(Variable node) {
        Constraint[] cons = getConstraintsAsArray(node);
        for (Constraint c : cons)
            if (!c.isNecessary())
                return false;
        return true;
    }

    public String toString() {
        final StringBuilder s = new StringBuilder("---Variables : \n");
        Arrays.stream(getVars()).forEach(v -> s.append(v).append("\n"));
        s.append("---Contraintes \n");
        Arrays.stream(getConstraints()).forEach(c -> s.append(c).append("\n"));
        return s.toString();
    }

	public Solution solution() {
        return new Solution(getVars());
    }
}