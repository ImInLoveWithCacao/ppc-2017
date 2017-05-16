package definition;

import java.util.Arrays;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

abstract class BinaryConstraint extends Constraint {
    private String operator;

    BinaryConstraint(Variable[] vars, String operator) {
        super(vars);
        this.operator = operator;
    }

    public String toString() {
        return streamVars().map(Variable::getName).collect(joining(" ".concat(operator).concat(" "), "(", ")"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryConstraint)) return false;
        BinaryConstraint that = (BinaryConstraint) o;
        return Objects.equals(operator, that.operator) && Arrays.deepEquals(variables, that.variables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, variables[0], variables[1]);
    }

    @Override
    public abstract boolean isSatisfied();

}
