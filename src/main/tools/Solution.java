package tools;

import definition.Variable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

public class Solution {
    private Map<String, Integer> solutions;

    public Solution(Variable[] vars) {
        solutions = new HashMap<>();
        Arrays.stream(vars).forEach(var -> solutions.put(var.getName(), var.getValue()));
    }

    Integer[] serialize() {
        return solutions.values().toArray(new Integer[0]);
    }

    public String toString() {
        Set<String> solutions = this.solutions.entrySet().stream().map(Entry::toString).collect(Collectors.toSet());
        return "{".concat(String.join("; ", solutions)).concat("}");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        return solutions.equals(solution.solutions);
    }

    @Override
    public int hashCode() {
        return solutions.hashCode();
    }
}
