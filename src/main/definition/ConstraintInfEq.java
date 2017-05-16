package definition;

import static definition.factories.ConstraintFactory.INF_EQ;

public class ConstraintInfEq extends BinaryConstraint {

    public ConstraintInfEq(Variable v1, Variable v2) {
        super(new Variable[]{v1, v2}, INF_EQ);
    }

    @Override
    public boolean isSatisfied() {
        return (areInstantiated() && (getVars()[0].getValue() <= getVars()[1].getValue()));
    }

    @Override
    public boolean isNecessary() {
        return (getVars()[1].getSup() >= getVars()[0].getInf());
    }

    @Override
    public boolean[] filter() {
        boolean rep1 = false;
        boolean rep2 = false;
        Variable v1 = getVars()[0];
        Variable v2 = getVars()[1];
        int infv1 = v1.getInf();
        int infv2 = v2.getInf();
        while (infv2 < infv1 && infv2 != -1) {
            rep2 = v2.getDomain().remove(infv2);
            infv2 = v2.getInf();
        }

        int supv1 = v1.getSup();
        int supv2 = v2.getSup();
        while (supv1 > supv2 && supv1 != -1) {
            rep1 = v1.getDomain().remove(supv1);
            supv1 = v1.getSup();
        }
        return new boolean[]{(supv1 == -1 || infv2 == -1), rep1, rep2};
    }
}