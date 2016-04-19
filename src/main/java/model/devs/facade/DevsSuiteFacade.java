
package model.devs.facade;

import model.modeling.atomic;
import model.modeling.digraph;
import model.simulation.atomicSimulator;
import model.simulation.coupledCoordinator;

/**
 * Facade class to use Devs-Suite core. 
 * @author ezequiel
 */
public class DevsSuiteFacade {
    //Coupled Models
    private coupledCoordinator coordinator;
    //Atomic Models 
    private atomicSimulator simulator;

    /**
     * Constructor to simulate a DEVS Coupled Model
     * @param instanceModel 
     */
    public DevsSuiteFacade(digraph instanceModel) {
        this.coordinator = new coupledCoordinator(instanceModel, true); //SetSimulators = true 
    }
    
    /**
     * Constructor to simulate a DEVS Atomic Model  
     * @param instanceModel 
     */
    public DevsSuiteFacade(atomic instanceModel) {
        this.simulator = new atomicSimulator(instanceModel);
        this.simulator.initialize(0); //Inicialize at currentTime
    }
    
    /**
     * Method to start the model simulation
     * @param iterations 
     */
    public void simulate(Integer iterations){
        if(coordinator!=null)
            coordinator.simulate(iterations); //Siumalate coupled
        else
            simulator.simulate(iterations); //Siumalate atomic
    }
    
    
}
