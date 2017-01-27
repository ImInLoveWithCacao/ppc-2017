package search;

import java.util.ArrayList;

import definition.Constraint;

public class Tools {

	public static int[] randomTwo (int minD, int maxD){
		int[] rep = new int[2];
		int len = maxD - minD;
		double rand1 = Math.floor(Math.random()*len);
		double rand2 = Math.floor(Math.random()*len);
		rep[0] = (int) (minD + Math.min(rand1, rand2));
		rep[1] = (int) (minD + Math.max(rand1, rand2));
		if (rand1 == rand2) return randomTwo(minD, maxD);
		else return rep;
	}

	public static Constraint[] removeOne (Constraint[] in, Constraint toRremove){
		ArrayList<Constraint> outInt = new ArrayList<Constraint>();
		for (Constraint c : in) if (!c.equals(toRremove)) outInt.add(c);
		return Tools.toArray(outInt);
	}

	public static Constraint[] toArray(ArrayList<Constraint> in) {
		int l = in.size();
		Constraint[] rep = new Constraint[l];
		for (int i=0; i<l; i++) rep[i] = in.get(i);
		return rep;
	}
	
	public static int[][] randomPairs(int nbCons, int nbVars){
		int[][] rep = new int[nbCons][2];
		for (int i=0; i<nbCons; i++){
			int[] pair = randomTwo (0, nbVars);
			boolean already = false; 
			for (int j=0; j<i; j++)
				if (pair[0] == rep[j][0] && pair[1] == rep[j][1]){
					i--;
					already = true;
				}
			if (!already) rep[i] = pair;
		}
		return rep; 
	}

	public static ArrayList<Constraint> toArrayList(Constraint[] in){
		ArrayList<Constraint> rep = new ArrayList<Constraint>();
		return rep;
	}
	
	public static String convertFromNano(long nanotime){
		String rep = "";
		double decimal = nanotime*Math.pow(10, -9);
		if (decimal >= 1){
			int nbSec = (int) Math.floor(decimal);
			if (nbSec > 60){
				int nbMin = nbSec/60;
				nbSec = Math.floorMod(nbSec, nbMin);
				rep += nbMin + " min ";
				decimal -= 60*nbMin;
			}
		}
		rep += decimal + " s";;
		return rep;
	}

}
