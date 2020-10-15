package facade;

import facade.exception.NoModelSettedException;
import facade.model.simulation.TimeAtomicSimulator;
import model.modeling.atomic;
import model.modeling.digraph;
import facade.model.simulation.TimeCoordinator;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Facade class to use Devs-Suite core.
 *
 * @author ezequiel
 */
public class DevsSuiteFacade {
    private boolean simulationDone;
    //Model Simulator/coordinator 
    private TimeAtomicSimulator atomicSimulator;
    private TimeCoordinator coordinator;

    /**
     * Default constructor. 
     * Requieres manual setting of model before start simulation.
     */
    public DevsSuiteFacade() {

    }
    
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
     * Constructor to simulate a coupled DEVS model with an arbitrary initial 
     * state. This method is usually used after cloning a coordinator.
     * @param coordinator 
     */
    public DevsSuiteFacade(TimeCoordinator coordinator) {
        this.atomicSimulator = null;
        this.coordinator = coordinator;
    }
    
    /**
     * Setter for atomic DEVS model
     * @param instanceModel 
     */
    public void setAtomicModel(atomic instanceModel){
        this.coordinator = null;
        this.atomicSimulator = new TimeAtomicSimulator(instanceModel);
        this.atomicSimulator.initialize(0D); //Inicialize at currentTime
    }
    
    /**
     * Setter for Coupled DEVS model
     * @param instanceModel 
     */
    public void setDigraphModel(digraph instanceModel){
        this.atomicSimulator = null;
        this.coordinator = new TimeCoordinator(instanceModel, true); //SetSimulators = true 
        this.coordinator.initialize(0D); //Inicialize at currentTime
    }

    /**
     * Method to start the model simulation
     *
     * @param iterations
     */
    public void simulateIterations(int iterations) throws NoModelSettedException {
        this.simulationDone = false;
        if(atomicSimulator == null && coordinator == null) 
            throw new NoModelSettedException("No model to start simulation setted.");
        if(atomicSimulator!=null){
            atomicSimulator.simulate(iterations); //Start Simulation
        }else{
            coordinator.simulate(iterations); //Start Simulation
        }
        this.simulationDone = true;
    }
    
    /**
     * Method to start the model simulation
     *
     * @param time
     */
    public void simulateToTime(double time) throws NoModelSettedException {
        this.simulationDone = false;
        if(atomicSimulator == null && coordinator == null) 
            throw new NoModelSettedException("No model to start simulation setted.");
        if(atomicSimulator!=null){
            atomicSimulator.simulate(time); //Start Simulation
        }else{
            coordinator.simulate(time); //Start Simulation
        }
        this.simulationDone = true;
    }
    
    /**
     * Method to start the model simulation from initTime to endTime
     *
     * @param initTime
     * @param endTime
     */
    public void simulateToTime(double initTime, double endTime) throws NoModelSettedException {
        this.simulationDone = false;
        if(atomicSimulator == null && coordinator == null) 
            throw new NoModelSettedException("No model to start simulation setted.");
        if(atomicSimulator!=null){
            atomicSimulator.simulate(initTime, endTime); //Start Simulation
        }else{
            coordinator.simulate(initTime, endTime); //Start Simulation
        }
        this.simulationDone = true;
    }
    
    /**
     * Method that returns a deep copy of current simulator usen apache commons
     * clone method that use serialization and deserialization to make a deep
     * cloning of the original object.
     * @return 
     */
    public Object deepCloning(){
        return SerializationUtils.clone(atomicSimulator!=null?atomicSimulator:coordinator);
    }
    
    public void reset(){
        if(atomicSimulator == null && coordinator == null) 
            throw new NoModelSettedException("No model to start simulation setted.");
        if(atomicSimulator!=null){
            atomicSimulator.initialize(0D); //Inicialize at currentTime
            atomicSimulator.getModel().initialize();
        }else{
            coordinator.initialize(0D); //Inicialize at currentTime
            coordinator.getModel().initialize();
        }
    }

    public boolean isSimulationDone() {
        return simulationDone;
    }
}
