package model.simulation;

import model.modeling.DevsInterface;
import model.modeling.IOBasicDevs;
import util.Logging;

/**
 *
 * @author ezequiel
 * @date 10/08/2016  
 */
public class AtomicTimedSimulator extends atomicSimulator {

    public AtomicTimedSimulator() {
    }

    public AtomicTimedSimulator(IOBasicDevs atomic) {
        super(atomic);
    }
    
    public void simulate(double time) {
        int i = 1;
        tN = nextTN();
        while ((tN < DevsInterface.INFINITY) && (i <= time)) {
            Logging.log("ITERATION " + i + " ,time: " + tN, Logging.full);
            computeInputOutput(tN);
            showOutput();
            DeltFunc(tN);
            tL = tN;
            tN = nextTN();
            showModelState();
            i++;
        }
        System.out.println("Terminated Normally at ITERATION " + i + " ,time: " + tN);
    }
}
