# DEVS Suite Facade
Facade to use DEVSJAVA without graphical interface.
Additionally, the possibility of simulating the models by time limit is included, instead of only limiting the simulation by the number of iterations.

## Maven Dependencies
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-math3</artifactId>
        <version>3.6.1</version>
    </dependency>
    
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.5</version>
    </dependency>
    
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.1</version>
        <scope>test</scope>
    </dependency>

## Usage
The MyDEVSModel class can be either an atomic model (by extending DEVSJAVA's "atomic" class) or a coupled model (by extending DEVSJAVA's "digraph" class).

    MyDEVSModel model = new MyDEVSModel();
    DevsSuiteFacade facade = new DevsSuiteFacade(model);
    facade.reset() // forze initialize model
    facade.simulateToTime(MAX_SIMULATION_TIME);
    

