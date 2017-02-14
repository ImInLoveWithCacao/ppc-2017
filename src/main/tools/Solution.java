package tools;

import definition.Variable;

public class Solution {
    private Variable[] variables;

    public Solution(Variable[] vars) {
        int nbVars = vars.length;
        variables = new Variable[nbVars];
        for (int i = 0; i < nbVars; i++)
            variables[i] = new Variable(vars[i]);
    }

    int[] serialize() {
        int nbVars = variables.length;
        int[] rep = new int[nbVars];
        for (int i = 0; i < nbVars; i++)
            rep[i] = variables[i].getValue();
        return rep;
    }

    public String toString() {
        String s = "\n";
        for (Variable v : variables)
            s += v + "\n";
        return s;
    }
}
