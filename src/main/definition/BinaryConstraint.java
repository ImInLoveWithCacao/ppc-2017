package definition;

abstract class BinaryConstraint extends Constraint {
    private String operator;

    public BinaryConstraint(Variable[] vars, String operator) {
        super(vars);
        this.operator = operator;
    }

    @Override
    public boolean isSatisfied() {
        return false;
    }
}
