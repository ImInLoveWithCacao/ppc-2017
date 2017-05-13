package definition.factories;

import definition.Variable;
import tools.Tools;

import static java.util.stream.IntStream.range;

public class VariableFactory {
    public static Variable[] successive(int nbVars) {
        return range(0, nbVars).mapToObj(i -> oneVariable(i, i, i)).toArray(Variable[]::new);
    }

    public static Variable[] decreasingDomains(int nbVars, int maxD) {
        return range(0, nbVars)
            .mapToObj(i -> oneVariable(i, 0, maxD - i))
            .toArray(Variable[]::new);
    }

    public static Variable[] generateRandomVars(int nbVars, int minDom, int maxDom) {
        return range(0, nbVars).mapToObj(
            i -> {
                int[] dom = Tools.randomTwo(minDom, maxDom);
                return oneVariable(i, dom[0], dom[1]);
            }).toArray(Variable[]::new);
    }


    public static Variable[] createVariables(int nbVars, int minD, int maxD) {
        return range(0, nbVars)
            .mapToObj(i -> oneVariable(i, minD, maxD))
            .toArray(Variable[]::new);
    }

    public static Variable oneVariable(int index, int minD, int maxD) {
        return new Variable(index, minD, maxD);
    }
}
