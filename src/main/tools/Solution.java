package tools;

import definition.Variable;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.Entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class Solution {
    private Map<String, Integer> solutions;

    public Solution(Stream<Variable> vars) {
        solutions = vars.collect(toMap(Variable::getName, Variable::getValue));
    }

    Integer[] serialize() {
        return solutions.values().toArray(new Integer[0]);
    }

    public String toString() {
        return this.solutions.entrySet().stream()
            .map(Entry::toString)
            .collect(joining("; ", "{", "}"));
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
