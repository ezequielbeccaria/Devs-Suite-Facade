package model.predictive;

import model.modeling.atomic;
import model.modeling.message;
import libsvm.*;
import model.predictive.metrics.Scores;

/**
 *
 * @author ezequiel
 * @date 20/04/2017
 */
public class AtomicSVR extends atomic implements SupervisedLearningRegressorDevs {

    private svm_model model;
    private double score;

    @Override
    public void initialize() {
        phase = "passive";
        sigma = INFINITY;

        super.initialize();
    }

    @Override
    public void deltint() {
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e); //internally make ta=sigma-e
        for (int i = 0; i < x.getLength(); i++) {

        }
    }

    @Override
    public message out() {
        if (phaseIs("active")) {

            message m = new message();
            return m;
        }
        return null;
    }

    @Override
    public void fit(Double[][] Xtrain, Double[][] Xtest, Double[] ytrain, Double[] ytest) throws Exception {
        svm_problem prob = new svm_problem();
        int recordCount = Xtrain.length;
        if (recordCount != ytrain.length) {
            throw new Exception("Input features examples must be equal to output examples.");
        }
        int featureCount = Xtrain[0].length;

        prob.y = new double[recordCount];
        prob.l = recordCount;
        prob.x = new svm_node[recordCount][featureCount];

        for (int i = 0; i < recordCount; i++) {
            Double[] features = Xtrain[i];
            prob.x[i] = new svm_node[features.length];
            for (int j = 0; j < features.length; j++) {
                svm_node node = new svm_node();
                node.index = j;
                node.value = features[j];
                prob.x[i][j] = node;
            }
            prob.y[i] = ytrain[i];
        }

        svm_parameter param = new svm_parameter();
        param.probability = 1;
        param.gamma = 0.5;
        param.nu = 0.5;
        param.C = 100;
        param.svm_type = svm_parameter.EPSILON_SVR;
        param.kernel_type = svm_parameter.RBF;
        param.cache_size = 20000;
        param.eps = 0.001;

        model = svm.svm_train(prob, param);
        
        evalModel(Xtest, ytest);
    }
    
    private void evalModel(Double[][] Xtest, Double[] ytest){
        Double[] ypred = predict(Xtest); 
        
        this.score = Scores.r2score(ytest, ypred);
    }

    private Double[] predict(Double[][] xtest) {
        Double[] yPred = new Double[xtest.length];
        for (int k = 0; k < xtest.length; k++) {
            Double[] fVector = xtest[k];

            svm_node[] nodes = new svm_node[fVector.length];
            for (int i = 0; i < fVector.length; i++) {
                svm_node node = new svm_node();
                node.index = i;
                node.value = fVector[i];
                nodes[i] = node;
            }
            
            yPred[k] = svm.svm_predict(model, nodes);
        }
        return yPred;
    }

    @Override
    public Double[] metrics() {
        Double[] scores = new Double[1];
        scores[0] = this.score;
        return scores;
    }

}
