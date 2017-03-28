package definition.factories;

import definition.Variable;
import tools.Tools;

import java.util.stream.IntStream;

public class VariableFactory {
    public static Variable[] successive(int nbVars) {
        return IntStream.range(0, nbVars).mapToObj(i -> createOneVar(i, i, i)).toArray(Variable[]::new);
    }

    public static Variable[] decreasingDomains(int nbVars, int maxD) {
        return IntStream.range(0, nbVars)
                       .mapToObj(i -> createOneVar(i, 0, maxD - i))
                       .toArray(Variable[]::new);
    }

    public static Variable[] generateRandomVars(int nbVars, int minDom, int maxDom) {
        return IntStream.range(0, nbVars)
                       .mapToObj(
                               i -> {
                                   int[] dom = Tools.randomTwo(minDom, maxDom);
                                   return createOneVar(i, dom[0], dom[1]);
                               }
                       ).toArray(Variable[]::new);
    }


    public static Variable[] createVariables(int nbVars, int minD, int maxD) {
        return IntStream.range(0, nbVars)
                       .mapToObj(i -> createOneVar(i, minD, maxD))
                       .toArray(Variable[]::new);
    }

    public static Variable createOneVar(int index, int minD, int maxD) {
        return new Variable(index, minD, maxD);
    }
}
