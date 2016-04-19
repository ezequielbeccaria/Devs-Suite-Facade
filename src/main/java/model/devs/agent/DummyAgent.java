
package model.devs.agent;

import GenCol.entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import model.devs.action.ActionEntity;
import model.modeling.atomic;
import model.modeling.content;
import model.modeling.devs;
import model.modeling.message;

/**
 *
 * @author ezequiel
 */
public class DummyAgent extends atomic implements StatefulEntity{
    private final List<devs> actionalbleDevsList;
    private final List<ActionEntity> actionsList;
    private final List<entity> inputEntities;

    public DummyAgent(List<devs> actionalbleDevsList) {
        super("DummyAgent");
        this.actionalbleDevsList = actionalbleDevsList;
        this.actionsList = createActionsList();
        this.sigma = 1D;
        this.inputEntities = new ArrayList<>();
        //Create input port
        addInport("input");        
        //Create outputs port (one for each actionable devs)
        for(devs ad : actionalbleDevsList){
            addOutport("out_"+ad.getName());
        }
    }    

    public List<devs> getActionalbleDevsList() {
        return actionalbleDevsList;
    }
    
    
    
    @Override
    public void initialize() {
        phase = "passive";
        super.initialize();
    }

    /**
     * Si recibe un estimulo externo, almacena los estimulos y continua hasta 
     * sigma = sigma - e (e -> 'elapsed time')
     * @param e
     * @param x 
     */
    @Override
    public void deltext(double e, message x) {
        Continue(e);        
        inputEntities.clear();
        for (int i = 0; i < x.getLength(); i++) {
            if (messageOnPort(x, "input", i)) {
                inputEntities.add(x.getValOnPort("input", i));                
            }
        }        
    }

    /**
     * Cuando se cumple el tiempo de trancisión interna, si NO posee estimulos externos previos,
     * crea un mensaje random para una entidad (DEVS) y lo almacena para envialo
     */
    @Override
    public void deltint() {
        if(inputEntities.isEmpty()){
            devs selectedDevs = getRandomActionalbleDevs();
            ActionEntity selectedAction = getRandomAction();
            content con = new content("out_"+selectedDevs.getName(), selectedAction);
            inputEntities.add(con);
        }            
        holdIn("passive", sigma);        
    }

    /**
     * Envia el contenido almacenado a los Devs sobre los que actua el agente
     * @return 
     */
    @Override
    public message out() {        
        message m = new message();
        inputEntities.stream().forEach((e) -> {   
            m.add(e);
        });
        return m;
    }
    
    /**
     * Get random action from the actions List
     * @return 
     */
    private ActionEntity getRandomAction(){
        int RandomActionIndex = ThreadLocalRandom.current().nextInt(0, actionsList.size());
        return actionsList.get(RandomActionIndex);
    }
    
    /**
     * Get random Devs form the Devs List
     * @return 
     */
    private devs getRandomActionalbleDevs(){
        int RandomDevsIndex = ThreadLocalRandom.current().nextInt(0, actionalbleDevsList.size());
        return actionalbleDevsList.get(RandomDevsIndex);
    }

    private List<ActionEntity> createActionsList() {
        List<ActionEntity> actions = new ArrayList(5);
        actions.add(new ActionEntity("UP"));
        actions.add(new ActionEntity("DOWN"));
        actions.add(new ActionEntity("RIGHT"));
        actions.add(new ActionEntity("LEFT"));
        actions.add(new ActionEntity("WAIT"));
        return actions;
    }

    @Override
    public Map getState() {       
        return null;
    }
}
