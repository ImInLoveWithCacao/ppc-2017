package factories;

import definition.Variable;
import tools.Tools;

public class VariableFactory {
    public static Variable[] decreasingDomains(int nb, int maxD) {
        Variable[] res = new Variable[nb];
        for (int i = 0; i < nb; i++) {
            res[i] = new Variable("x" + i, i, 0, maxD - i);
        }
        return res;
    }

    public static Variable[] generateRandomVars(int nb, int minDom, int maxDom) {
        Variable[] res = new Variable[nb];
        for (int i = 0; i < nb; i++) {
            int[] dom = Tools.randomTwo(minDom, maxDom);
            res[i] = new Variable("x" + i, i, dom[0], dom[1]);
        }
        return res;
    }


    public static Variable[] createVariables(int nbVars, int minD, int maxD) {
        Variable[] res = new Variable[nbVars];
        for (int i = 0; i < nbVars; i++) {
            res[i] = createOneVar("x" + i, i, minD, maxD);
        }
        return res;
    }

    public static Variable createOneVar(String name, int index, int minD, int maxD) {
        return new Variable(name, index, minD, maxD);
    }
}
