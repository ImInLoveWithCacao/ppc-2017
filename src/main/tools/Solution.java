package tools;

import definition.Variable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.Entry;

public class Solution {
    private Map<String, Integer> solutions;

    public Solution(Variable[] vars) {
        solutions = new HashMap<>();
        Arrays.stream(vars).forEach(var -> solutions.put(var.getName(), var.getValue()));
    }

    Integer[] serialize() {
        return solutions.values().stream().toArray(Integer[]::new);
    }

    public String toString() {
        return "{".concat(String.join("; ", solutions.entrySet().stream().map(Entry::toString).toArray(String[]::new)))
                       .concat("}");
    }
}
