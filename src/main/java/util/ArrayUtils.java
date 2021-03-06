package util;

/**
 *
 * @author ezequiel
 */
public class ArrayUtils {
    /** 
     * Method that transponse a matrix of MxN to a matrix of NxM.
     * @param m
     * @return 
     */
    public static double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
}
