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
        boolean rep0 = false;
        boolean rep1 = false;
        boolean rep2 = false;
        boolean rep3 = false;

        Variable m = m();
        Variable x = x();
        Variable y = y();

        int supM = m.getSup();
        int supX = x.getSup();
        while (supM > supX && supM != -1) {
            rep1 = m.getDomain().remove(supM);
            supM = m.getSup();
        }
        if (supM == -1) return new boolean[]{true, true, false, false};
        else {
            int supY = y.getSup();
            while (supM > supY && supM != -1) {
                rep1 = m.getDomain().remove(supM);
                supM = m.getSup();
            }

            if (supM == -1) return new boolean[]{true, true, false, false};
            else {
                int minM = m.getInf();
                int minX = x.getInf();
                while (minX < minM && minX != -1) {
                    rep2 = x.getDomain().remove(minX);
                    minX = x.getInf();
                }
                if (minX == -1) return new boolean[]{true, false, true, false};
                else {
                    int minY = y.getInf();
                    while (minY < minM && minY != -1) {
                        rep3 = y.getDomain().remove(minY);
                        minY = y.getInf();
                    }
                    if (minY == -1) return new boolean[]{true, false, false, true};
                }
            }
        }
        return new boolean[]{rep0, rep1, rep2, rep3};
    }

    @Override
    public String toString() {
        return (m().getName() + " = min(" + x().getName() + ", " + y().getName() + ")");
    }

}
