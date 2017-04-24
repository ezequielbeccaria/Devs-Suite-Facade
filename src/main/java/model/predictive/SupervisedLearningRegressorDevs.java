package model.predictive;

/**
 * Interface that define the methods that a predictive DEVS must implement to 
 * support Supervised Learning Regressors.
 * @author ezequiel
 * @date 20/04/2017  
 */
public interface SupervisedLearningRegressorDevs {    
    /**
     * Fit a model with one independent variable and one dependent variable.
     * @param x: independent variable
     * @param y: dependent variable
     */
//    public void fit(Double[] x, Double[] y) throws Exception;
    /**
     * Fit a model with 'n' independent variables and one dependent variable.
     * @param Xtrain training independet variables matrix
     * @param Xtest validation independet variables matrix
     * @param ytrain: training dependent variable array
     * @param ytest: validation dependent variable array
     * @throws java.lang.Exception
     */
    public void fit(Double[][] Xtrain, Double[][] Xtest, Double[] ytrain, Double[] ytest) throws Exception;
    /**
     * Get stimartor metrics after fitting
     */
    public Double[] metrics();
}
