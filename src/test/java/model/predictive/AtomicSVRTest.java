package model.predictive;

import java.util.Map;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.ModelSelection;

/**
 *
 * @author ezequiel
 */
public class AtomicSVRTest {
    AtomicSVR instance; 
    Double[][] X;
    Double[] y;
    int n; //number of samples
    
    
    public AtomicSVRTest() {
        n = 100;
        X = new Double[n][1];
        y = new Double[n];
    }
    
    private double testFunction(double x){
        return 10 * Math.sin(7.8 * Math.log(1 + x)) / (1 + 0.1 * Math.pow(x, 2));
    }
    
    private Double[] functionAppl(Double[][] X){
        NormalDistribution nd = new NormalDistribution(0, 2);
        Double[] y = new Double[X.length];
        for(int i=0;i<X.length;i++){
            y[i] = testFunction(X[i][0]) + 0.1 * nd.sample() * X[i][0];
        }
        return y;
    }    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //Devs Instance
        instance = new AtomicSVR();
        //Generate Dataset                
        for(int i=0; i<n; i++)
            X[i][0] = RandomUtils.nextDouble(0, 1);
        y = functionAppl(X);
    }
    
    @After
    public void tearDown() {
    }   

    /**
     * Test of fit method, of class AtomicSVR.
     * @throws java.lang.Exception
     */
    @Test
    public void testFit() throws Exception {
        System.out.println("fit test score>0.7");
        Map<String, Object> trainTestData = ModelSelection.trainTestSplit(X, y, 0.2, 42);
        Double[][] Xtrain = (Double[][]) trainTestData.get("xtrain");
        Double[][] Xtest = (Double[][]) trainTestData.get("xtest");
        Double[] ytrain = (Double[]) trainTestData.get("ytrain");
        Double[] ytest = (Double[]) trainTestData.get("ytest");
        
        instance.fit(Xtrain, Xtest, ytrain, ytest);
        
        Double[] metrics = instance.metrics();

        System.out.format("Model Score R2: %f\n", metrics[0]);
        assertTrue(metrics[0]>0.75);
    } 
    
}
