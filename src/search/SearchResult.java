package search;

import java.util.ArrayList;

public class SearchResult {
	private String functionName;
	private long time; 
	private int nodes;
	private int nbSols;
	private ArrayList<String> solutions;
	
	public SearchResult (String name){
		functionName = name;
		time = 0; 
		nodes = 0;
		nbSols = 0;
		solutions = new ArrayList<String>();
	}
	
	public SearchResult (SearchResult tr){
		functionName = tr.getName();
		time = tr.getTime();
		nodes = tr.getNodes();	
		nbSols = tr.getNbSols();	
		solutions = new ArrayList<String>(tr.getSols());
	}
	
	public String getName(){
		return functionName;
	}
	
	public long getTime(){
		return time;
	}
	
	public int getNodes(){
		return nodes;
	}
	
	public int getNbSols(){
		return nbSols;
	}
	
	public ArrayList<String> getSols(){
		return solutions;
	}
	
	public void addNode(){
		nodes += 1;
	}
	
	public void addSol(String sol){
		solutions.add(sol);
		nbSols += 1;
	}
	
	public void timerStart(){
		time = System.nanoTime();
	}
	
	public void timerEnd(){
		if (time != 0) time = System.nanoTime() - time; 
	}
	
	
	public String data(){
		return "Function name : " + functionName + '\n'
				+ "Elapsed time : " + Tools.convertFromNano(time) + '\n' 
				+ "Explored nodes : " + nodes + '\n'
				+ "Number of solutions : " + nbSols ;
	}
	
	public String toString(){
		return data() + '\n' + solutions.toString();
	}
}
