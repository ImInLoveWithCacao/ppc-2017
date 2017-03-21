package definition;

public class Variable {

    /**
     * Nom de la variable.
     */
    private String name;

    /**
     * Identifiant unique.
     */
    private int idx;

    /**
     * Le domaine associé.
     */
    private Domain dom;

    /**
     * Construit une variable "name" definie entre les valeurs min et max (incluses).
     */
    public Variable(int idx, int min, int max) {
        this.name = "x" + idx;
        this.idx = idx;
        this.dom = new DomainBitSet(min,max);
    }

    public Variable(Variable v) {
    	this.name = v.name;
    	this.idx = v.idx;
    	this.dom = v.getDomain().clone();
    }
    
    public int getInd(){
    	return idx;
    }

    public String getName() {
        return name;
    }

    public Domain getDomain() {
        return this.dom;
    }

    public void setDomain(Domain d) {
        this.dom = d;
    }

    public Domain cloneDomain() {
        return getDomain().clone();
    }

    /**
     * @return true ssi la variable est instanciée.
     */
    boolean isInstantiated() {
        return this.dom.size() == 1;
    }

    /**
     * @return le nombre de valeurs dans le domaine de la variable.
     */
    int getDomainSize() {
        return getDomain().size();
    }

    /**
     * instantie la variable a la valeur v.
     */
    public void instantiate(int v) {
        this.dom.fix(v);
    }

    /**
     * @return la plus petite valeur du domaine.
     */
    int getInf() {
        return getDomain().firstValue();
    }

    /**
     * @return la plus grande valeur du domaine.
     */
    int getSup() {
        return getDomain().lastValue();
    }

    /**
     * @return la valeur affectee a la variable ssi la variable est effectivement instanciee, sinon -1.
     */
    public int getValue() {
        return isInstantiated() ? getInf() : -1;
    }

    /**
     * @return égalité des identifiants
     */
    public boolean equals(Object o) {
        return ((o instanceof Variable)
                && ((Variable) o).getInd() == getInd()
                && ((Variable) o).getName().equals(getName()))
                && ((Variable) o).getDomain().equals(getDomain());
    }

    public String toString() {
        return this.name + " := " + this.dom;
    }
}