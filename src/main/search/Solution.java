package search;

import definition.Variable;

public class Solution {
    private Variable[] variables;

    public Solution(Variable[] vars) {
        this.variables = vars;
    }

    public int[] serialize() {
        int nbVars = variables.length;
        int[] rep = new int[nbVars];
        for (int i=0; i<nbVars; i++) rep[i] = variables[i].getValue();
        return rep;
    }

    public String toString() {
        String s = "\n";
        for (Variable v : variables) s += v + "\n";
        return s;
    }
}
