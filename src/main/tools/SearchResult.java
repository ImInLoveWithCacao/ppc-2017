package tools;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	private String functionName;
	private long time; 
	private int nodes;
	private int nbSols;
    private List<Solution> solutions;

    public SearchResult(String name) {
        functionName = name;
		time = 0; 
		nodes = 0;
		nbSols = 0;
        solutions = new ArrayList<>();
    }


    public String toString() {
        return data() + '\n' + solutions.toString();
    }

    public void timerStart() {
        time = System.nanoTime();
    }

    public void timerEnd() {
        if (time != 0) time = System.nanoTime() - time;
    }

    public void addNode() {
        nodes += 1;
    }

    public int getNbSols() {
        return nbSols;
    }

    public void addSol(Solution sol) {
        solutions.add(sol);
        nbSols += 1;
    }

    public Integer[][] serializedSolutions() {
        return solutions.stream()
                       .map(Solution::serialize)
                       .toArray(Integer[][]::new);
    }

    private String data() {
        return "Function name : ".concat(functionName).concat("\n")
                       .concat("Elapsed time : ").concat(Tools.convertFromNano(time)).concat("\n")
                       .concat("Explored nodes : ").concat("" + nodes).concat("\n")
                       .concat("Number of solutions : ").concat("" + nbSols);
    }
}
