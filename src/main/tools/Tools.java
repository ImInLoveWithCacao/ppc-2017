package tools;

import java.text.SimpleDateFormat;

public class Tools {

    public static int[] randomTwo(int minD, int maxD) {
        int[] rep = new int[2];
        int len = maxD - minD;
        double rand1 = Math.floor(Math.random() * len);
        double rand2 = Math.floor(Math.random() * len);
        rep[0] = (int) (minD + Math.min(rand1, rand2));
        rep[1] = (int) (minD + Math.max(rand1, rand2));
        if (rand1 == rand2) return randomTwo(minD, maxD);
        else return rep;
    }

    static int[][] randomPairs(int nbCons, int nbVars) {
        int[][] rep = new int[nbCons][2];
        for (int i = 0; i < nbCons; i++) {
            int[] pair = randomTwo(0, nbVars);
            boolean already = false;
            for (int j = 0; j < i; j++)
                if (pair[0] == rep[j][0] && pair[1] == rep[j][1]) {
                    i--;
                    already = true;
                }
            if (!already) rep[i] = pair;
        }
        return rep;
    }

    static String convertFromNano(long nanotime) {
        return new SimpleDateFormat("mm'm', ss's', SSSS'ms'").format(nanotime / 1000000);
    }

}
