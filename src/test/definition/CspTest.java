package definition;

import definition.factories.ConstraintFactory;
import definition.factories.VariableFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void empty_csp() {
        Csp csp = new Csp();
        assertEquals(0, csp.streamVars().count());
        assertEquals(0, csp.streamConstraints().count());
    }

    @Test
    void addOneVariable() {
        Csp csp = new Csp();
        csp.addOneVariable(1, 3);
        assertEquals(csp.getRelatedConstraints(new Variable(0, 0, 0)).count(), 0);
    }

    @Test
    void indexes_increment() {
        Csp csp = new Csp();
        csp.addOneVariable(1, 2);
        csp.addOneVariable(3, 4);
        assertEquals(1, csp.getVars().get(1).getInd());
    }

    @Test
    void addVariables() {
        Csp csp = new Csp();
        csp.addOneVariable(0, 1);
        csp.addVariables(3, 0, 2);
        assertEquals(3, csp.getVars().get(3).getInd());
    }

    @Test
    void addConstraint() {
        Csp csp = new Csp();
        csp.addVariables(2, 0, 2);
        csp.addBinaryConstraint("x0 < x1");
        assertEquals(csp.getRelatedConstraints(new Variable(0, 0, 0)).count(), 1);
        assertEquals(csp.getRelatedConstraints(new Variable(1, 0, 0)).count(), 1);
    }
}