package model.predictive;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.UncorrelatedRandomVectorGenerator;
import org.apache.commons.math3.random.UniformRandomGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.ArrayUtils;

/**
 *
 * @author ezequiel
 */
public class AtomicSVRTest {
    AtomicSVR instance; 
    double[][] X;
    double[] y;
    int n; 
    
    
    public AtomicSVRTest() {
        n = 100;
    }
    
    private double testFunction(double x){
        return 10 * Math.sin(7.8 * Math.log(1 + x)) / (1 + 0.1 * Math.pow(x, 2));
    }
    
    private double[] functionAppl(double[][] X){
        NormalDistribution nd = new NormalDistribution(0, 2);
        double[] y = new double[X.length];
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
        long seed = 17399225432L; // Fixed seed means same results every time
        UniformRandomGenerator randomGenerator = new UniformRandomGenerator(new MersenneTwister(seed));
        UncorrelatedRandomVectorGenerator randomVectorGenerator = new UncorrelatedRandomVectorGenerator(n, randomGenerator);        
        X[0] = randomVectorGenerator.nextVector();
        X = ArrayUtils.transposeMatrix(X);
        y = functionAppl(X);
    }
    
    @After
    public void tearDown() {
    }   

    /**
     * Test of fit method, of class AtomicSVR.
     */
    @Test
    public void testFit() throws Exception {
        System.out.println("fit test");
        double[][] Xtrain = null;
        double[][] Xtest = null;
        double[] ytrain = null;
        double[] ytest = null;
        
        instance.fit(Xtrain, Xtest, ytrain, ytest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    } 
    
}
