
package facade.model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.modeling.DevsInterface;
import model.modeling.IOBasicDevs;
import model.simulation.atomicSimulator;
import util.Logging;

/**
 *
 * @author ezequiel
 * @date 05/10/2016  
 */
public class TimeAtomicSimulator extends atomicSimulator {
    
    protected List<Map> iterationsGlobalState; //var used to store every iteration state

    public TimeAtomicSimulator(IOBasicDevs atomic) {
        super(atomic);
        this.iterationsGlobalState = new ArrayList<>();
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
    
    public void simulate(double initTime, double endtime) {
        initialize(initTime);
        simulate(endtime);
    }

}
