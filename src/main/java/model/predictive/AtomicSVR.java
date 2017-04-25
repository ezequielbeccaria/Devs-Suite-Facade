package model.predictive;

import model.modeling.atomic;
import model.modeling.message;
import libsvm.*;
import model.modeling.content;
import model.modeling.port;
import model.predictive.metrics.Scores;

/**
 * Class that implements 'SupervisedLearningRegressorDevs' interface. 
 * This class, first, must be trained using the method called 'fit' and after 
 * that, the intance could be use as any atomic DEVS. Internally, as predictor 
 * model, uses the Support Vector Machine of 'libsvm' library.
 * @author ezequiel
 * @date 20/04/2017
 */
public class AtomicSVR extends atomic implements SupervisedLearningRegressorDevs {

    private svm_model model; //Support Machine Model
    private double score; //Model score after traning
    private String[] states = new String[]{"passive","active"}; //states of atomic DEVS
    private Double[] xpoint; //new unseen input point
    private int featureCount;

    public AtomicSVR() {
        super();        
        
        addInport("start");
        addInport("stop");
        addInport("input");        
        addOutport("output");
        
        this.initialize();
    }

    @Override
    public void initialize() {
        phase = states[1];
        sigma = INFINITY;        
        
        super.initialize();
    }

    @Override
    public void deltint() {        
        sigma = INFINITY;
    }

    @Override
    public void deltext(double e, message x) {
        Continue(e); //internally make ta=sigma-e
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "start", i)) {
                phase = states[1];
            }
            if (messageOnPort(x, "stop", i)) {
                phase = states[0];
            }
            if (messageOnPort(x, "input", i)) {
                if(!phaseIs(states[0])){
                    xpoint = new Double[featureCount];
                    xpoint[0] = ((DoubleEntity) x.getValOnPort(new port("input"), x.read(i))).getValue();
                    setSigma(0);
                }               
            }
        }
    }

    @Override
    public message out() {
        if (phaseIs(states[1]) && xpoint!=null) { //phaseIs("Active")=true            
            message m = new message();
            content con = makeContent("output", new DoubleEntity(this.predict(xpoint)));
            m.add(con);
            return m;
        }
        return null;
    }

    //##### PREDICTIVE DEVS METHODS #####    
    @Override
    public void fit(Double[][] Xtrain, Double[][] Xtest, Double[] ytrain, Double[] ytest) throws Exception {
        svm_problem prob = new svm_problem();
        int recordCount = Xtrain.length;
        if (recordCount != ytrain.length) {
            throw new Exception("Input features examples must be equal to output examples.");
        }
        featureCount = Xtrain[0].length;

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
    
    /**
     * Method used to validate predictor's quality during traning stage.
     * @param Xtest
     * @param ytest 
     */
    private void evalModel(Double[][] Xtest, Double[] ytest){
        Double[] ypred = predict(Xtest); 
        
        this.score = Scores.r2score(ytest, ypred);
    }
    
    /**
     * Method used for the predictive model to predict (internally) new values 
     * for unseen new point.
     * @param xpoint
     * @return 
     */
    private Double predict(Double[] xpoint) {
        svm_node[] nodes = new svm_node[xpoint.length];
        for (int i = 0; i < xpoint.length; i++) {
            svm_node node = new svm_node();
            node.index = i;
            node.value = xpoint[i];
            nodes[i] = node;
        }

        return svm.svm_predict(model, nodes);
    }

    /**
     * Method used for the predictive model to predict (internally) new values 
     * for unseen new points. This method predicts many output at same time.
     * @param xtest
     * @return 
     */
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
