package definition;

import search.Solution;
import search.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Csp {

	private Variable[] vars; // l'ensemble des variables du CSP. Note: les domaines sont connus au travers des variables
	private Constraint[] cons; // l'ensemble des contraintes du CSP

	public Csp(Variable[] vars, Constraint[] cons) {
		this.vars = vars;
		this.cons = cons;
	}

	public Variable[] getVars() {
		return vars;
	}

	public int getNbVars() {
		return getVars().length;
	}

	public Constraint[] getConstraints() {
		return this.cons;
	}

	// retourne la premiere variable non instanciee du csp
	public Variable randomVar() {
		for (Variable v : this.vars) if (!v.isInstantiated()) return v;
		return null;
	}

	/**
	 * @return true ssi toutes les variables sont instanciees.
	 */
	public boolean allInstanciated() {
		for (Variable v : this.vars) if (!v.isInstantiated()) return false;
		return true;
	}

	/**
	 * @return true vrai l'ensemble des contraintes du CSP est verifi�.
	 */
	public boolean hasSolution() {
		for (Constraint c : getConstraints()) if (!c.isSatisfied()) return false;
		return allInstanciated();
	}

	public Variable[] instanciated() {
		ArrayList<Variable> vars = new ArrayList<Variable>();
		for (Variable v : vars) if (v.isInstantiated()) vars.add(v);
		Variable[] rep = (Variable[]) vars.toArray();
		return rep;
	}

	/**
	 * @param var
     * @return un array contenant les constraintes du Csp concernées par var.
     */
    public Constraint[] getConstraintsAsArray(Variable var) {
        return Tools.toArray(getConstraintsAsArrayList(var));
    }

    public ArrayList<Constraint> getConstraintsAsArrayList(Variable var) {
        ArrayList<Constraint> rep = new ArrayList<Constraint>();
		for (Constraint c : getConstraints()) {
			Variable[] vars = c.getVars();
			for (Variable v : vars) if (v.equals(var)) rep.add(c);
		}
		return rep;
	}

	public boolean satisfied(Constraint[] cons) {
		for (Constraint c : cons)
			if (c.areInstanciated() && !c.isSatisfied())
				return false;
		return true;
	}

	public boolean necessary(Constraint[] cons) {
		for (Constraint c : cons)
			if (!c.isNecessary())
				return false;
		return true;
	}

	/**
     * Lance le filtrage du Csp avec pour point de départ la variable var. Et effectue la
     * propagation à travers les contraintes qui concernet les variables dont le domaine
     * a été réduit par un filtrage.
     *
     * @param var La variable qui vient d'être instanciée.
     * @return un tableau de getNbVars() + 1 booléens. Le premier vaut false si le domaine
     * de l'une des variables a été vidé. Les suivants d'indice i+1 valent true si le domaine
     * de la ième variable a changé.
     */
	public boolean[] propagate(Variable var) {
		int nb = getNbVars();
		boolean[] rep = new boolean[nb + 1];
		rep[0] = true;
		for (int i = 1; i < nb + 1; i++) rep[i] = false;
		Queue<Constraint> queue = new LinkedList<Constraint>();
        queue.addAll(getConstraintsAsArrayList(var));
        while (!queue.isEmpty()) {
			Constraint c = queue.poll();
			boolean[] filter = c.filter();
			int len = filter.length;
			if (filter[0]) {
				rep[0] = false;
				for (int i = 1; i < len; i++)
					if (filter[i])
						rep[c.getVars()[i - 1].getInd() + 1] = true;
				return rep;
            } else
                for (int i = 1; i < len; i++)
					if (filter[i]) {
						Variable vi = c.getVars()[i - 1];
						rep[vi.getInd() + 1] = true;
                        ArrayList<Constraint> cons1 = getConstraintsAsArrayList(vi);
                        for (Constraint c1 : cons1)
							if (!c1.equals(c) && !queue.contains(c1)) queue.add(c1);
					}
		}
		return rep;
	}

	public String toString() {
		String s = "---Variables : \n";
		for (Variable v : this.vars) s += v + "\n";
		s += "---Contraintes \n";
		for (Constraint c : this.cons) s += c + "\n";
		return s;
	}

	public Solution solution() {
		return new Solution(this.vars);
	}
}