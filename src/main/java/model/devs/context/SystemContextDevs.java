
package model.devs.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import model.devs.agent.DummyAgent;
import model.devs.agent.StatefulEntity;
import model.modeling.devs;
import model.modeling.digraph;

/**
 *
 * @author ezequiel
 */
public class SystemContextDevs extends digraph implements StatefulEntity{
    
    public SystemContextDevs(String name, DummyAgent agent) {
        super(name);
        
        //add agent component
        add(agent);
        //Add actionable entities compoenet
        for(devs actionableDevs : agent.getActionalbleDevsList()){
            add(actionableDevs);
            addCoupling(agent, "out_"+actionableDevs.getName(), actionableDevs, "in");
        }
        
        initialize(); //Inicialize all the models

    }

    @Override
    public Map getState() {
        Map<String, Object> state = new HashMap<>();
        for (Iterator it = this.components.iterator(); it.hasNext();) {
            StatefulEntity se = (StatefulEntity) it.next();
            state.put(se.getName(), se.getState());
        }
        return state;
    }
}
