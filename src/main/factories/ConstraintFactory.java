package factories;


import definition.*;

public class ConstraintFactory {
    public static final String INF = "<";
    public static final String INFEG = "<=";
    public static final String DIFF = "!=";

    public static Constraint binaryConstraint(Variable v1, String operator, Variable v2) {
        switch (operator) {
            case INF:
                return new ConstraintInf(v1, v2);
            case INFEG:
                return new ConstraintInfEg(v1, v2);
            case DIFF:
                return new ConstraintDiff(v1, v2);
            default:
                throw new IllegalArgumentException("Invalid operator provided");
        }
    }
}
