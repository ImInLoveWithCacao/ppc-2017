package definition;

public class Variable {

    private String name; // nom de la variable
    private int idx; // identifiant unique
    private Domain dom; // le domaine associe

    // construit une variable "name" definie entre les valeurs min et max (incluses)
    public Variable(String name, int idx, int min, int max) {
        this.name = name;
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
    
    public String getName(){
    	return name;
    }

    public Domain getDomain() {
        return this.dom;
    }

    public void setDomain(Domain d) {
        this.dom = d;
    }

    // retourne vrai ssi la variable est instanciee
    public boolean isInstantiated() {
        return this.dom.size() == 1;
    }

    // retourne vrai ssi le domaine de la variable contient la valeur v
    public boolean canBeInstantiatedTo(int v) {
        return this.dom.contains(v);
    }

    // retourne le nombre de valeurs dans le domaine de la variable
    public int getDomainSize() {
        return this.dom.size();
    }

    // supprime la valeur v du domaine de la variable
    public void remValue(int v) {
        this.dom.remove(v);
    }

    // instantie la variable a la valeur v
    public void instantiate(int v) {
        this.dom.fix(v);
    }

    // retourne la plus petite valeur du domaine
    public int getInf() {
        return getDomain().firstValue();
    }

    // retourne la plus grande valeur du domaine
    public int getSup() {
        return getDomain().lastValue();
    }

    // retourne la valeur affectee a la variable ssi la variable est effectivement instanciee, sinon -1
    public int getValue() {
    	if (isInstantiated()) return getInf();
    	else return -1;
    }

    // teste l'égalité des identifiants
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