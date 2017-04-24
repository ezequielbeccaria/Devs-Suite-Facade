

package model.predictive.metrics;

import org.apache.commons.math3.stat.StatUtils;

/**
 *
 * @author ezequiel
 * @date 20/04/2017  
 */
public class Scores {
    /**
     * R^2 (coefficient of determination) regression score function.
     * Best possible score is 1.0 and it can be negative (because the model can 
     * be arbitrarily worse). A constant model that always predicts the expected
     * value of y, disregarding the input features, would get a R^2 score of 0.0.
     * @param yreal
     * @param ypred
     * @return 
     */
    public static double r2score(Double[] yreal, Double[] ypred){
        double yrealMean = StatUtils.mean(org.apache.commons.lang3.ArrayUtils.toPrimitive(yreal, 0));        
        double dividend = 0;
        double divisor = 0;
        for(int i=0;i<yreal.length;i++){
            dividend += Math.pow(yreal[i]-ypred[i], 2);
            divisor += Math.pow(yreal[i]-yrealMean, 2);
        }    
        return 1-(dividend/divisor);
    }
}
