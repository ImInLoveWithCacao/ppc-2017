package definition;

public class ConstraintMin extends Constraint {

    public ConstraintMin(Variable m, Variable x, Variable y) {
        super(new Variable[]{m, x, y});
    }

    public Variable m() {
        return getVars()[0];
    }

    public Variable x() {
        return getVars()[1];
    }

    public Variable y() {
        return getVars()[2];
    }

    @Override
    public boolean isSatisfied() {
        return (areInstantiated() && m().getValue() == Math.min(x().getValue(), y().getValue()));
    }

    @Override
    public boolean isNecessary() {
        int supM = m().getSup();
        return supM >= x().getInf() || supM >= y().getInf();
    }

    @Override
    public boolean[] filter() {
        return null;
    }

    @Override
    public String toString() {
        return (m().getName() + " = min(" + x().getName() + ", " + y().getName() + ")");
    }

}
