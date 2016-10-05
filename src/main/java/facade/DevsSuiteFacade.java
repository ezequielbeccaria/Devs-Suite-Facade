package facade;

import facade.model.simulation.TimeAtomicSimulator;
import java.util.List;
import java.util.Map;
import model.modeling.atomic;
import model.modeling.digraph;
import facade.model.simulation.TimeCoordinator;

/**
 * Facade class to use Devs-Suite core.
 *
 * @author ezequiel
 */
public class DevsSuiteFacade {
    //Model Simulator/coordinator 
    private final TimeAtomicSimulator atomicSimulator;
    private final TimeCoordinator coordinator;

    /**
     * Constructor to simulate a DEVSs Coupled Model
     *
     * @param instanceModel
     */
    public DevsSuiteFacade(digraph instanceModel) {
        this.atomicSimulator = null;
        this.coordinator = new TimeCoordinator(instanceModel, true); //SetSimulators = true 
    }

    /**
     * Constructor to simulate a DEVSs Atomic Model
     *
     * @param instanceModel
     */
    public DevsSuiteFacade(atomic instanceModel) {
        this.coordinator = null;
        this.atomicSimulator = new TimeAtomicSimulator(instanceModel);
        this.atomicSimulator.initialize(0D); //Inicialize at currentTime
    }

    /**
     * Method to start the model simulation
     *
     * @param iterations
     */
    public void simulateIterations(int iterations) {
        if(atomicSimulator!=null){
            atomicSimulator.simulate(iterations); //Start Simulation
        }else{
            coordinator.simulate(iterations); //Start Simulation
        }
    }
    
    /**
     * Method to start the model simulation
     *
     * @param time
     */
    public void simulateTime(double time) {
        if(atomicSimulator!=null){
            atomicSimulator.simulate(time); //Start Simulation
        }else{
            coordinator.simulate(time); //Start Simulation
        }
    }

    public List<Map> getSimulationResults() {
        if(atomicSimulator!=null){
            return atomicSimulator.getSimulationResults();
        }else{
            return coordinator.getSimulationResults();
        }
    }

}
