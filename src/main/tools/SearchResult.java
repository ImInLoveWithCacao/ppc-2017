package tools;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static java.util.stream.Collectors.joining;

public class SearchResult {
    private static final String NEW_LINE = "\n";
    private String functionName;
    private long time;
    private int nodes;
    private int nbSols;
    private Queue<Solution> solutions;

    public SearchResult(String name) {
        functionName = name;
        time = 0;
        nodes = 0;
        nbSols = 0;
        solutions = new LinkedList<>();
    }


    public String toString() {
        return data().concat(solutions.stream().map(Solution::toString).collect(joining("\n")));
    }

    public void timerStart() {
        time = System.nanoTime();
    }

    public void timerEnd() {
        time = System.nanoTime() - time;
    }

    public void addNode() {
        nodes += 1;
    }

    public int getNbSols() {
        return nbSols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return nodes == that.nodes &&
            getNbSols() == that.getNbSols() &&
            Objects.equals(solutions, that.solutions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, getNbSols(), solutions);
    }

    public void addSol(Solution sol) {
        solutions.add(sol);
        nbSols += 1;
    }

    public Integer[][] serializedSolutions() {
        return solutions.stream().map(Solution::serialize).toArray(Integer[][]::new);
    }

    public String data() {
        return "Function name : ".concat(functionName).concat(NEW_LINE)
            .concat("Elapsed time : ").concat(Tools.convertFromNano(time)).concat(NEW_LINE)
            .concat("Explored nodes : ").concat(nodes + NEW_LINE)
            .concat("Number of solutions : ").concat(nbSols + NEW_LINE);
    }
}
