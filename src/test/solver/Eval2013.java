package solver;

import definition.Constraint;
import definition.Csp;
import definition.Variable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.SearchResult;

import java.util.Arrays;
import java.util.List;

import static definition.factories.ConstraintFactory.*;
import static definition.factories.VariableFactory.createVariables;
import static java.util.stream.Collectors.toList;
import static solver.Solver.WITHFILTER;

public class Eval2013 {

    @Test
    void montee_en_charge_n_egal_3() {
        assertMonteeEnCharge(3);
    }

    @Test
    void montee_en_charge_n_egal_4() {
        assertMonteeEnCharge(4);
    }

    void montee_en_charge_n_egal_5() {
        assertMonteeEnCharge(5);
    }

    void montee_en_charge_n_egal_6() {
        assertMonteeEnCharge(6);
    }

    void montee_en_charge_n_egal_7() {
        assertMonteeEnCharge(7);
    }


    private void assertMonteeEnCharge(int n) {
        Variable[] variables = createVariables((int) Math.pow(10, n), 1, 10);
        List<Constraint> constraints = Arrays.stream(chainInferior(variables)).collect(toList());
        constraints.add(binaryConstraint(variables[variables.length - 1], INF, variables[0]));
        SearchResult res = Solver.createSolver(
            WITHFILTER,
            "Circuit inférieur n = ".concat("" + n),
            new Csp(variables, constraints.toArray(new Constraint[constraints.size()]))
        ).solve();
        System.out.println(res.data());
        Assertions.assertEquals(0, res.getNbSols());
    }
}
