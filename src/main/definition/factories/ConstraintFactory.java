package definition.factories;


import definition.*;

import java.util.stream.IntStream;

public class ConstraintFactory {
    public static final String INF = "<";
    public static final String INF_EQ = "<=";
    public static final String DIFF = "!=";

    public static Constraint[] chainInferior(Variable[] vars) {
        return chainInferior(vars, true);
    }

    public static Constraint[] chainInferior(Variable[] vars, boolean strict) {
        return IntStream.range(0, vars.length - 1)
                       .mapToObj(i -> binaryConstraint(vars[i], strict ? INF : INF_EQ, vars[i + 1]))
                       .toArray(Constraint[]::new);
    }

    public static Constraint binaryConstraint(Variable v1, String operator, Variable v2) {
        switch (operator) {
            case INF:
                return new ConstraintInf(v1, v2);
            case INF_EQ:
                return new ConstraintInfEq(v1, v2);
            case DIFF:
                return new ConstraintDiff(v1, v2);
            default:
                throw new IllegalArgumentException("Invalid operator provided");
        }
    }
}
