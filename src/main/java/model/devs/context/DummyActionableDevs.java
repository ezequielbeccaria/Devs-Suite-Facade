
package model.devs.context;

import java.util.HashMap;
import java.util.Map;
import model.devs.agent.StatefulEntity;
import model.modeling.atomic;
import model.modeling.message;

/**
 *
 * @author ezequiel
 */
public class DummyActionableDevs extends atomic implements StatefulEntity{
    public DummyActionableDevs(Double sigma, String name) {
        super(name);
        this.sigma = sigma!=null?sigma:1;
        //Create input port
        addInport("input");        
        //Create output port
        addOutport("output");        
    }    
    
    @Override
    public void initialize() {
        phase = "passive";
        super.initialize();
    }

    /**
     * Si recibe un estimulo externo, se pone en estado "input_received"  y continua hasta 
     * sigma = sigma - e (e -> 'elapsed time')
     * @param e
     * @param x 
     */
    @Override
    public void deltext(double e, message x) {
        Continue(e);        
        phase = "input_received_"+x.getValOnPort("in", 0).toString();        
    }

    /**
     * Cuando se cumple el tiempo de trancisión interna, si pone en estado "passive"
     */
    @Override
    public void deltint() {
        holdIn("passive", sigma);        
    }

    /**
     * Envia un mensaje vacio
     * @return 
     */
    @Override
    public message out() {
        return new message();
    }

    @Override
    public Map getState() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", name);
        state.put("id", phase);
        state.put("goal", false);
        state.put("reward", 0D);
        return state;
    }
}
