package tools;

import definition.Variable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    Map<String, Integer> solutions;

    public Solution(Variable[] vars) {
        solutions = new HashMap<String, Integer>();
        int nbVars = vars.length;
        Variable var;
        for (int i = 0; i < nbVars; i++) {
            var = vars[i];
            solutions.put("X" + var.getInd(), var.getValue());
        }
    }

    private int getNbVars() {
        return solutions.size();
    }

    int[] serialize() {
        int i = 0;
        int nbVars = getNbVars();
        int[] res = new int[nbVars];
        Collection<Integer> collection = solutions.values();
        for (Integer value : collection) {
            res[i] = value;
            i++;
        }
        return res;
    }

    public String toString() {
        String res = "";
        for (Map.Entry<String, Integer> s : solutions.entrySet())
            res += s.getKey() + " = " + s.getValue();
        return res;
    }
}
