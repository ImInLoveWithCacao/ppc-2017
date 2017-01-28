package definition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import search.SearchResult;
import search.Solution;
import search.Tools;

/**
 * Created by IntelliJ IDEA.
 * User: xlorca
 */
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
	 * @return un array contenant les constraintes du Csp concern�es par var.
	 */
	public Constraint[] getConstraints(Variable var) {
		return Tools.toArray(getConstraints1(var));
	}

	public ArrayList<Constraint> getConstraints1(Variable var) {
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
	 * Lance le filtrage du Csp avec pour point de d�part la variable var. Et effectue la
	 * propagation � travers les contraintes qui concernet les variables dont le domaine
	 * a �t� r�duit par un filtrage.
	 *
	 * @param var La variable qui vient d'�tre instanci�e.
	 * @return un tableau de getNbVars() + 1 bool�ens. Le premier vaut false si le domaine
	 * de l'une des variables a �t� vid�. Les suivants d'indice i+1 valent true si le domaine
	 * de la i�me variable a chang�.
	 */
	public boolean[] propagate(Variable var) {
		int nb = getNbVars();
		boolean[] rep = new boolean[nb + 1];
		rep[0] = true;
		for (int i = 1; i < nb + 1; i++) rep[i] = false;
		Queue<Constraint> queue = new LinkedList<Constraint>();
		queue.addAll(getConstraints1(var));
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
						ArrayList<Constraint> cons1 = getConstraints1(vi);
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