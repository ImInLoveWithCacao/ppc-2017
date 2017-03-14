package definition;

public abstract class Constraint {
	private Variable[] variables = new Variable[2];
	
	public Constraint (Variable[] vars){
		variables = vars;
	}
	
	/**
	 * @return les variables de la contrainte.
	 */
	public Variable[] getVars() {
		return variables;
	}
	
	/**
     * @return vrai si toutes les variables de la contrainte sont instanciées.
     */
    boolean areInstanciated() {
        for (Variable v : getVars())
			if (!v.isInstantiated()) return false;
		return true;
	}

	/**
     * @return vrai ssi toutes les variables de la contrainte sont instanciees
     * et la contrainte est verifiée.
     */
	public abstract boolean isSatisfied();
	
	/**
	 * Une condition necessaire a la satisfaction de la contrainte
     * @return vrai ssi il existe encore un tuple satisfaisant la contrainte.
     */
	public abstract boolean isNecessary();
	
	/**
     * Modifie les domaines des variables de sorte à enlever les valeurs ne pouvant satisfaire
     * la contrainte.
     * @return Un array de trois boolean. Le premier vaut true si un domaine a été vidé,
     * le deuxiéme vaut true si le domaine de la premiére variable a changé, le troisiéme
     * vaut true si le domaine de la seconde variable a changé.
     */
	public abstract boolean[] filter();

    /**
     * @param var
     * @return True si var est concernée par la contrainte
     */
    boolean affects(Variable var) {
        return getVars()[0].equals(var) || getVars()[1].equals(var);
    }

    public abstract String toString();
}
