package facade.model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.modeling.Coupled;
import model.modeling.DevsInterface;
import model.simulation.coordinator;
import util.Logging;

/**
 * Coordinador que extiende coupledCoordinator para incorporar funcionalidad
 * para simular hasta un limite de tiempo. El coordinador original solo podia
 * simular hasta que se cumple un determinado numero de iteraciones.
 *
 * @author ezequiel
 * @date 10/08/2016
 */
//public class TimeCoordinator extends coupledCoordinator implements SimulationResults{
public class TimeCoordinator extends coordinator {
    protected boolean stopSimFlag;
    protected List<Map> iterationsGlobalState; //var used to store every iteration state

    public TimeCoordinator(Coupled c) {
        super(c);
        this.iterationsGlobalState = new ArrayList<>();
    }

    public TimeCoordinator(Coupled c, boolean setSimulators) {
//        super(c, setSimulators); //Este contructor esta mal implementado en Devs-Suite
        super(c, setSimulators, setSimulators); //Este contructor se usa debido a lo descripto en la linea anterior
        this.iterationsGlobalState = new ArrayList<>();        
    }

    public void simulate(double maxSimTime) {
        stopSimFlag = false;
        int i = 1;
        tN = nextTN();
        while ((tN < DevsInterface.INFINITY) && (tN <= maxSimTime) && !stopSimFlag) {
            Logging.log("ITERATION " + i + " ,time: " + tN, Logging.full);
            computeInputOutput(tN);
            showOutput();
            wrapDeltfunc(tN);
            tL = tN;
            tN = nextTN();
            showModelState();
            i++;
        }
    }
    
    public void simulate(double initTime, double maxSimTime) {    
        initialize(initTime);
        simulate(maxSimTime);
    }   

    public void stopSimulation(){
        stopSimFlag = true;
    }
}
