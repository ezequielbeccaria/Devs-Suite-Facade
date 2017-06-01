package model.predictive;

import facade.DevsSuiteFacade;
import java.util.Map;
import model.modeling.atomic;
import model.modeling.content;
import model.modeling.digraph;
import model.modeling.message;
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

    static AtomicSVR instance;
    static Double[][] X;
    static Double[] y;
    static int n; //number of samples

    public AtomicSVRTest() {
    }

    private static double testFunction(double x) {
        return 10 * Math.sin(7.8 * Math.log(1 + x)) / (1 + 0.1 * Math.pow(x, 2));
    }

    private static Double[] functionAppl(Double[][] X) {
        NormalDistribution nd = new NormalDistribution(0, 2);
        Double[] y = new Double[X.length];
        for (int i = 0; i < X.length; i++) {
            y[i] = testFunction(X[i][0]) + 0.1 * nd.sample() * X[i][0];
        }
        return y;
    }

    @BeforeClass
    public static void setUpClass() {
        n = 5000;
        X = new Double[n][1];
        y = new Double[n];

        //Devs Instance
        instance = new AtomicSVR();
        //Generate Dataset                
        for (int i = 0; i < n; i++) {
            X[i][0] = RandomUtils.nextDouble(0, 10);
        }
        y = functionAppl(X);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fit method, of class AtomicSVR.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testFit() throws Exception {
        System.out.println("fit test score>0.85");
        Map<String, Object> trainTestData = ModelSelection.trainTestSplit(X, y, 0.2, 42);
        Double[][] Xtrain = (Double[][]) trainTestData.get("xtrain");
        Double[][] Xtest = (Double[][]) trainTestData.get("xtest");
        Double[] ytrain = (Double[]) trainTestData.get("ytrain");
        Double[] ytest = (Double[]) trainTestData.get("ytest");

        instance.fit(Xtrain, Xtest, ytrain, ytest);

        Double[] metrics = instance.metrics();

        System.out.format("Model Score R2: %f\n\n", metrics[0]);
        assertTrue(metrics[0] > 0.8);
    }

    @Test
    public void testDevsAtomic() throws Exception {
        System.out.println("Test DEVS atomic");

        DevsSuiteFacade facade = new DevsSuiteFacade(new DevsCoupledEnviroment("Coupled Test", instance));
        facade.simulateToTime(10);
    }

    private class DevsCoupledEnviroment extends digraph {

        public DevsCoupledEnviroment(String nm, atomic intanceToTest) {
            super(nm);

            add(intanceToTest); //Predictive Devs added

            AtomicDoubleEntityGenerator generator = new AtomicDoubleEntityGenerator("TestGenerator");
            add(generator); //Test Generator added

            AtomicPrinter printer = new AtomicPrinter("TestPrinter");
            add(printer); //Test console printer

            //cuplings
            addCoupling(generator, "output", intanceToTest, "input");
            addCoupling(intanceToTest, "output", printer, "input");

        }

    }

    private class AtomicDoubleEntityGenerator extends atomic {

        public AtomicDoubleEntityGenerator(String name) {
            super(name);
            
            addOutport("output");

            this.initialize();
        }

        @Override
        public void initialize() {
            phase = "active";
            sigma = 1;

            super.initialize();
        }

        @Override
        public void deltint() {
            holdIn("active", sigma);
        }

        @Override
        public void deltext(double e, message x) {
            Continue(e); //internally make ta=sigma-e            
        }

        @Override
        public message out() {
            if (phaseIs("active")) { //phaseIs("Active")=true            
                message m = new message();
                Double value = RandomUtils.nextDouble(0, 10);
                System.out.println("input: " + value);
                content con = makeContent("output", new DoubleEntity(value));
                m.add(con);
                return m;
            }
            return null;
        }
    }

    private class AtomicPrinter extends atomic {

        public AtomicPrinter(String name) {
            super(name);
            
            addInport("input");        

            this.initialize();
        }

        @Override
        public void initialize() {
            phase = "passive";
            sigma = INFINITY;

            super.initialize();
        }

        @Override
        public void deltint() {
            holdIn("passive", sigma);
        }

        @Override
        public void deltext(double e, message x) {
            Continue(e); //internally make ta=sigma-e     
            for (int i = 0; i < x.getLength(); i++) {
                if (messageOnPort(x, "input", i)) {
                    DoubleEntity de = (DoubleEntity) x.getValOnPort("input", i);
                    System.out.println("Ouput: " + de.getValue());
                }
            }
        }

        @Override
        public message out() {
            return null;
        }
    }

}
