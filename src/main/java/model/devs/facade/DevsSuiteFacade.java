package model.devs.facade;

import java.util.List;
import java.util.Map;
import model.modeling.atomic;
import model.modeling.digraph;
import model.simulation.AtomicTimedSimulator;
import model.simulation.CoupledTimedCoordinator;

/**
 * Facade class to use Devs-Suite core.
 *
 * @author ezequiel
 */
public class DevsSuiteFacade {
    //Model Simulator/coordinator 
    private final AtomicTimedSimulator atomicSimulator;
    private final CoupledTimedCoordinator coupledCoordinator;

    /**
     * Constructor to simulate a DEVSs Coupled Model
     *
     * @param instanceModel
     */
    public DevsSuiteFacade(digraph instanceModel) {
        this.atomicSimulator = null;
        this.coupledCoordinator = new CoupledTimedCoordinator(instanceModel, true); //SetSimulators = true 
    }

    /**
     * Constructor to simulate a DEVSs Atomic Model
     *
     * @param instanceModel
     */
    public DevsSuiteFacade(atomic instanceModel) {
        this.coupledCoordinator = null;
        this.atomicSimulator = new AtomicTimedSimulator(instanceModel);
        this.atomicSimulator.initialize(0D); //Inicialize at currentTime
    }

    /**
     * Method to start the model simulation
     *
     * @param iterations
     */
    public void simulate(int iterations) {
        if(atomicSimulator!=null){
            atomicSimulator.simulate(iterations); //Start Simulation
        }else{
            coupledCoordinator.simulate(iterations); //Start Simulation
        }
    }
    
    /**
     * Method to start the model simulation
     *
     * @param time
     */
    public void simulate(double time) {
        if(atomicSimulator!=null){
            atomicSimulator.simulate(time); //Start Simulation
        }else{
            coupledCoordinator.simulate(time); //Start Simulation
        }
    }

    public List<Map> getSimulationResults() {
        if(atomicSimulator!=null){
            return atomicSimulator.getSimulationResults();
        }else{
            return coupledCoordinator.getSimulationResults();
        }
    }

}
