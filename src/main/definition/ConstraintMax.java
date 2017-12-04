package definition;

/**
 * Created by diab on 03/06/17.
 */
public class ConstraintMax extends Constraint {
    public ConstraintMax(Variable[] vars) {
        super(vars);
    }

    @Override
    public boolean isSatisfied() {
        int maxM = variables[0].getValue();
        return areInstantiated()
                && maxM >= variables[1].getValue()
                && maxM >= variables[2].getValue();
    }

    @Override
    public boolean isNecessary() {
        return variables[0].getSup() >= variables[1].getInf()
                && variables[0].getSup() >= variables[2].getInf();
    }

    @Override
    public boolean[] filter() {
        boolean[] rep = {false, false, false, false};
        Variable m = variables[0];
        Domain dm = m.getDomain();
        Domain dx = variables[1].getDomain();
        Domain dy = variables[2].getDomain();
        for (int i : dm) {
            if (i < dx.firstValue() || i < dy.firstValue()) {
                rep[1] = dm.remove(i);
            }
        }
        if (dm.size() == 0) {
            rep[0] = true;
            return rep;
        }
        for (int j : dx) {
            if (j > m.getSup()) {
                rep[2] = dx.remove(j);
            }
        }
        if (dx.size() == 0) {
            rep[0] = true;
            return rep;
        }
        for (int k : dy) {
            if (k > m.getSup()) {
                rep[3] = dy.remove(k);
            }
        }
        if (dy.size() == 0) {
            rep[0] = true;
        }
        return rep;
    }

    @Override
    public String toString() {
        return variables[0].toString()
                .concat(" = max(")
                .concat(variables[1].toString())
                .concat(", ")
                .concat(variables[2].toString())
                .concat(")");
    }
}
