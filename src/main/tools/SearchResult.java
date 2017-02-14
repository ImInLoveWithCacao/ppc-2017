package tools;

import java.util.ArrayList;

public class SearchResult {
	private String functionName;
	private long time; 
	private int nodes;
	private int nbSols;
	private ArrayList<Solution> solutions;

    public SearchResult(String name) {
        functionName = name;
		time = 0; 
		nodes = 0;
		nbSols = 0;
		solutions = new ArrayList<Solution>();
	}

    public SearchResult(SearchResult tr) {
        functionName = tr.getName();
		time = tr.getTime();
		nodes = tr.getNodes();	
		nbSols = tr.getNbSols();	
		solutions = new ArrayList<Solution>(tr.getSols());
	}


    // ------------------------------------ Public ---------------------------------------------------------------------

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

    public int[][] serializedSolutions() {
        int nbSolutions = solutions.size();
        int[][] rep = new int[nbSolutions][];
        for (int i = 0; i < nbSolutions; i++) rep[i] = solutions.get(i).serialize();
        return rep;
    }

    // ------------------------------------- Private -------------------------------------------------------------------

    private String getName() {
        return functionName;
    }

    private long getTime() {
        return time;
    }

    private int getNodes() {
        return nodes;
    }

    private ArrayList<Solution> getSols() {
        return solutions;
    }

    private String data() {
        return "Function name : " + functionName + '\n'
				+ "Elapsed time : " + Tools.convertFromNano(time) + '\n'
				+ "Explored nodes : " + nodes + '\n'
				+ "Number of solutions : " + nbSols ;
	}
}
