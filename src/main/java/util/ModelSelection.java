package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author ezequiel
 */
public class ModelSelection {

    /**
     * Split arrays or matrices into random train and test subsets
     *
     * @param arrays
     */
    public static Map<String, Object> trainTestSplit(Double[][] X, Double[] y, double testSize, double seed) throws Exception {
        if (X.length != y.length)
            throw new Exception("Incopatible arrays lengths.");        
        if(testSize==0 || testSize==1)
            throw new Exception("testSize must be greater than 0 and smaller than 1.");
        
        Map<String, Object> resultMap = new HashMap<>();
        int n = X.length;
        //indexes generation
        int[] indexes = generateIndexes(n);
        //Shuffle indexes
        shuffleArray(indexes);
        
        int testItems = (int) Math.ceil(testSize*n);
        
        Double[][] Xtest = new Double[testItems][X[0].length];
        Double[][] Xtrain = new Double[n-testItems][X[0].length];
        Double[] ytest = new Double[testItems];
        Double[] ytrain = new Double[n-testItems];
        
        for(int i=0; i<testItems; i++){
            Xtest[i]=X[indexes[i]];
            ytest[i]=y[indexes[i]];
        }
        resultMap.put("xtest", Xtest);
        resultMap.put("ytest", ytest);
        
        for(int i=0; i+testItems<n; i++){
            Xtrain[i]=X[indexes[i+testItems]];
            ytrain[i]=y[indexes[i+testItems]];
        }
        resultMap.put("xtrain", Xtrain);
        resultMap.put("ytrain", ytrain);    
        
        return resultMap;
    }

    /**
     * Method that generate an array of length 'len' with secuential numbers
     * from 0 to len-1.
     *
     * @param len
     * @return
     */
    private static int[] generateIndexes(int len) {
        int[] indexArray = new int[len];
        for (int i = 0; i < len; i++) {
            indexArray[i] = i;
        }
        return indexArray;
    }
    
    /**
     * Implementing Durstenfeld shuffle.
     * @param ar 
     */
    private static void shuffleArray(int[] ar) {
        shuffleArray(ar, null);
    }

    /**
     * Implementing Durstenfeld shuffle.
     * If seed if provided, it is used as random pseudo generator seed.
     *
     * @param ar
     * @param seed
     */
    private static void shuffleArray(int[] ar, Long seed) {
        Random rnd;
        if(seed==null)
            rnd = ThreadLocalRandom.current();
        else 
            rnd = new Random(seed);
        
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
