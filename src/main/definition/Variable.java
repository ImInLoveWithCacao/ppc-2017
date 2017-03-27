package definition;

public class Variable {
    private int idx;
    private String name;
    private Domain dom;

    public Variable(int idx, int min, int max) {
        this.name = "x" + idx;
        this.idx = idx;
        this.dom = new DomainBitSet(min, max);
    }

    public Variable(Variable v) {
        this.name = v.name;
        this.idx = v.idx;
        this.dom = v.getDomain().clone();
    }

    public int getInd() {
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

    public boolean isInstantiated() {
        return this.dom.size() == 1;
    }

    public boolean isNotInstantiated() {
        return !isInstantiated();
    }

    public int getDomainSize() {
        return getDomain().size();
    }

    public void instantiate(int v) {
        this.dom.fix(v);
    }

    int getInf() {
        return getDomain().firstValue();
    }

    int getSup() {
        return getDomain().lastValue();
    }

    public int getValue() {
        return isInstantiated() ? getInf() : -1;
    }

    public boolean equals(Object o) {
        return ((o instanceof Variable)
            && ((Variable) o).getInd() == getInd()
            && ((Variable) o).getName().equals(getName()))
            && ((Variable) o).getDomain().equals(getDomain());
    }

    public String toString() {
        return this.name.concat(" := ").concat(this.dom.toString());
    }
}