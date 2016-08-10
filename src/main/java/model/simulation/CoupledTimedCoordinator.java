
package model.simulation;

import model.modeling.Coupled;
import model.modeling.DevsInterface;
import util.Logging;

/**
 * Coordinador que extiende coupledCoordinator para incorporar funcionalidad para
 * simular hasta un limite de tiempo. El coordinador original solo podia simular hasta que 
 * se cumple un determinado numero de iteraciones.
 * @author ezequiel
 * @date 10/08/2016  
 */
public class CoupledTimedCoordinator extends coupledCoordinator{

    public CoupledTimedCoordinator(Coupled c) {
        super(c);
    }

    public CoupledTimedCoordinator(Coupled c, boolean setSimulators) {
        super(c, setSimulators);
    }
 
    public void simulate(double maxSimTime) {
        int i = 1;
        tN = nextTN();
        while ((tN < DevsInterface.INFINITY) && (tN <= maxSimTime)) {
            Logging.log("ITERATION " + i + " ,time: " + tN, Logging.full);
            System.out.println("ITERATION " + i + " ,time: " + tN); //added for testing
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
