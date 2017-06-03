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
        return areInstantiated()
                && variables[0].getValue() > variables[1].getValue()
                && variables[0].getValue() > variables[2].getValue();
    }

    @Override
    public boolean isNecessary() {
        return false;
    }

    @Override
    public boolean[] filter() {
        return new boolean[0];
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
