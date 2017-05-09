package definition;

import definition.factories.ConstraintFactory;
import definition.factories.VariableFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CspTest {

    @Test
    void relatedConstraints() {
        Variable[] variables = VariableFactory.createVariables(3, 0, 3);
        Variable v0 = variables[0];
        Variable v1 = variables[1];
        Constraint[] constraints = new Constraint[]{
            ConstraintFactory.binaryConstraint(v0, "<", v1),
            ConstraintFactory.binaryConstraint(v0, "<", variables[2])
        };
        Csp csp = new Csp(variables, constraints);
        assertArrayEquals(csp.getRelatedConstraints(v0).toArray(Constraint[]::new), constraints);
        assertArrayEquals(csp.getRelatedConstraints(v1).toArray(Constraint[]::new), new Constraint[]{constraints[0]});
    }
}