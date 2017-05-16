package definition;

import org.junit.jupiter.api.Test;

import static definition.factories.ConstraintFactory.*;
import static definition.factories.VariableFactory.createVariables;
import static definition.factories.VariableFactory.oneVariable;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CspTest {

    @Test
    void relatedConstraints() {
        Variable[] variables = createVariables(3, 0, 3);
        Variable v0 = variables[0];
        Variable v1 = variables[1];
        Constraint[] constraints = new Constraint[]{
            binaryConstraint(v0, INF, v1),
            binaryConstraint(v0, INF, variables[2])
        };
        Csp csp = new Csp(variables, constraints);
        Constraint[] expected1 = csp.streamRelatedConstraints(v0).toArray(Constraint[]::new);
        assertEquals(expected1[0], constraints[1]);
        assertEquals(expected1[1], constraints[0]);
        assertEquals(csp.streamRelatedConstraints(v1).findFirst().orElse(null), constraints[0]);
    }

    @Test
    void empty_csp() {
        Csp csp = new Csp();
        assertEquals(0, csp.getVars().size());
        assertEquals(0, csp.getConstraints().size());
    }

    @Test
    void it_creates_a_map_field_when_adding_a_variable() {
        Csp csp = new Csp();
        csp.addVariables(oneVariable(0, 1, 3));
        assertEquals(csp.getRelatedConstraints(oneVariable(0, 0, 0)).size(), 0);
    }

    @Test
    void it_updates_map_correctly_when_creating_multple_cariables() {
        Csp csp = new Csp();
        csp.addVariables(oneVariable(0, 0, 3));
        Variable[] vars = createVariables(1, 2, 0, 3);
        csp.addVariables(vars);
        csp.getRelatedConstraints(oneVariable(0, 0, 0));
        csp.getRelatedConstraints(oneVariable(1, 0, 0));
        csp.getRelatedConstraints(oneVariable(2, 0, 0));
    }

    @Test
    void hash_map_should_use_variable_index_as_hashcode() {
        Csp csp = new Csp();
        csp.addVariables(oneVariable(0, 1, 1));
        assertEquals(0, csp.getRelatedConstraints(oneVariable(0, 1, 1)).size());
        assertEquals(0, csp.getRelatedConstraints(oneVariable(0, 1, 2)).size());
    }


    @Test
    void it_should_throw_exception_if_variable_index_already_exists() {
        Csp csp = new Csp();
        csp.addVariables(oneVariable(0, 0, 1));
        assertThrows(
            IllegalArgumentException.class,
            () -> csp.addVariables(oneVariable(0, 1, 2))
        );
    }

    @Test
    void related_constraints_should_throw_exception_if_variable_doesnt_exist() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Csp().getRelatedConstraints(new Variable(0, 0, 0)));
    }

    @Test
    void it_can_add_a_constraint() {
        Csp csp = new Csp();
        Variable[] variables = createVariables(2, 0, 2);
        csp.addVariables(variables);
        csp.addConstraints(binaryConstraint(variables[0], INF, variables[1]));
        assertEquals(1, csp.getRelatedConstraints(variables[0]).size());
        assertEquals(1, csp.getRelatedConstraints(variables[1]).size());
    }

    @Test
    void it_shloud_not_allow_adding_a_constraint_if_all_of_its_variables_dont_exist_already() {
        Csp csp = new Csp();
        Variable[] variables = createVariables(2, 0, 2);
        csp.addVariables(variables);
        Variable out = oneVariable(2, 0, 3);
        assertThrows(
            IllegalArgumentException.class,
            () -> csp.addConstraints(binaryConstraint(out, INF, variables[0]))
        );
    }

    @Test
    void id_can_add_multiple_constraints() {
        Csp csp = new Csp();
        Variable[] vars = createVariables(3, 1, 2);
        csp.addVariables(vars);
        csp.addConstraints(binaryConstraint(vars[0], INF, vars[1]), binaryConstraint(vars[1], DIFF, vars[2]));
        assertEquals(1, csp.getRelatedConstraints(vars[0]).size());
        assertEquals(2, csp.getRelatedConstraints(vars[1]).size());
        assertEquals(1, csp.getRelatedConstraints(vars[2]).size());
    }

    @Test
    void it_can_add_constraint_from_string() {
        Csp csp = new Csp();
        Variable[] variables = createVariables(2, 0, 2);
        csp.addVariables(variables);
        csp.addBinaryConstraints("x0 < x1");
        assertEquals(csp.getRelatedConstraints(new Variable(0, 0, 0)).size(), 1);
        assertEquals(csp.getRelatedConstraints(new Variable(1, 0, 0)).size(), 1);
    }

    @Test
    void it_should_throw_exception_if_string_does_not_match_pattern() {
        Csp csp = new Csp();
        Variable[] variables = createVariables(2, 0, 2);
        csp.addVariables(variables);
        assertThrows(
            IllegalArgumentException.class,
            () -> csp.addBinaryConstraints("x<y")
        );
    }

    @Test
    void it_should_throw_an_exception_when_given_unexistant_variable_from_a_string() {
        Csp csp = new Csp();
        Variable[] variables = createVariables(2, 0, 2);
        csp.addVariables(variables);
        assertThrows(
            IllegalArgumentException.class,
            () -> csp.addBinaryConstraints("x1 < x2")
        );
    }

    @Test
    void it_can_add_multiple_constraints_from_strings() {
        Csp csp = new Csp();
        Variable[] variables = createVariables(3, 0, 2);
        csp.addVariables(variables);
        csp.addBinaryConstraints(
            "x0 < x1",
            "x1!=x2",
            "x2   <=x0"
        );
        range(0, 2).forEach(i -> assertEquals(2, csp.getRelatedConstraints(variables[i]).size()));
    }
}