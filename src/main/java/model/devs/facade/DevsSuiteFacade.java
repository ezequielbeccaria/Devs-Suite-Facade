package model.devs.facade;

import java.util.List;
import java.util.Map;
import model.modeling.atomic;
import model.modeling.digraph;
import model.simulation.atomicSimulator;
import model.simulation.coupledCoordinator;

/**
 * Facade class to use Devs-Suite core.
 *
 * @author ezequiel
 */
public class DevsSuiteFacade {
    //Model Simulator/coordinator 
    private final atomicSimulator simulator;

    /**
     * Constructor to simulate a DEVSs Coupled Model
     *
     * @param instanceModel
     */
    public DevsSuiteFacade(digraph instanceModel) {
        this.simulator = new coupledCoordinator(instanceModel, true); //SetSimulators = true 
    }

    /**
     * Constructor to simulate a DEVSs Atomic Model
     *
     * @param instanceModel
     */
    public DevsSuiteFacade(atomic instanceModel) {
        this.simulator = new atomicSimulator(instanceModel);
        this.simulator.initialize(0); //Inicialize at currentTime
    }

    /**
     * Method to start the model simulation
     *
     * @param iterations
     */
    public void simulate(Integer iterations) {
        simulator.simulate(iterations); //Start Simulation
    }

    public List<Map> getSimulationResults() {
        return simulator.getSimulationResults();
    }

}
