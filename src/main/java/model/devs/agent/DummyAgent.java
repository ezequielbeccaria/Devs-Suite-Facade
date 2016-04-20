package model.devs.agent;

import GenCol.entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import model.devs.action.ActionEntity;
import model.modeling.atomic;
import model.modeling.content;
import model.modeling.devs;
import model.modeling.message;

/**
 * Agente de prueba para entender como funciona el core de DEVS-Suite
 * Elige las entidades (otros devs atomicos) sobre las que actua aleatoriamente 
 * Las acciones posibles a realizar sobre las entidades son prestablecidas: 
 * UP,DOWN,LEFT,RIGHT,WAIT
 * Las cuales son elegidas aleatoreamente en cada interacción
 * @author ezequiel
 */
public class DummyAgent extends atomic implements StatefulEntity {

    private final List<devs> actionalbleDevsList; //Entidades sobre las que puede actuar
    private final List<ActionEntity> actionsList; //Posibles acciones a realizar
    private final List<entity> inputEntities; //Variable donde se almacenan los estimulos externos temporalmente

    public DummyAgent(List<devs> actionalbleDevsList) {
        super("DummyAgent"); //nombre identificatorio del devs atomico
        this.actionalbleDevsList = actionalbleDevsList; 
        this.actionsList = createActionsList();
        this.sigma = 7D; //seteado de manera arbitraria, sigma>0
        this.inputEntities = new ArrayList<>(); //inicializo la variable
        //Create unique input port
        addInport("input"); 
        //Create outputs port (one for each actionable devs)
        for (devs ad : actionalbleDevsList) {
            addOutport("out_" + ad.getName());
        }
    }

    public List<devs> getActionalbleDevsList() {
        return actionalbleDevsList;
    }

    @Override
    /**
     * Method that init the atomic devs
     */
    public void initialize() {
        phase = "passive"; //this inicial state/phase is used as flag
        super.initialize();
    }

    /**
     * Si recibe un estimulo externo, almacena los estimulos y continua hasta
     * sigma = sigma - e (e -> 'elapsed time')
     *
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
     * Función de transición interna
     * Actua de bandera ya que se ejecuta despues de out()
     * Cuando se ejecuta la primera vez (en el t=0.0) activa el componente para 
     * que pueda empezar a enviar datos.
     */
    @Override
    public void deltint() {
        if ("passive".equals(phase)) {
            holdIn("active", sigma);
        }
    }

    /**
     * si NO posee estimulos externos previos (almacenados en inputEntities), 
     * crea un mensaje random para una entidad (DEVS) y envia el contenido (el previo o el creado)
     * a los Devs sobre los que actua el agente
     *
     * @return
     */
    @Override
    public message out() {
        message m = new message();
        if (!"passive".equals(phase) && inputEntities.isEmpty()) {
            devs d = getRandomActionalbleDevs();
            ActionEntity ae = getRandomAction();
            content con = new content("out_" + d.getName(), ae);
            inputEntities.add(con);
            holdIn("out_" + d.getName() + "_" + ae.getName(), sigma);
        }
        inputEntities.stream().forEach((e) -> {
            m.add(e);
        });
        inputEntities.clear();
        return m;
    }

    /**
     * Get random action from the actions List
     *
     * @return
     */
    private ActionEntity getRandomAction() {
        int RandomActionIndex = ThreadLocalRandom.current().nextInt(0, actionsList.size());
        return actionsList.get(RandomActionIndex);
    }

    /**
     * Get random Devs form the Devs List
     *
     * @return
     */
    private devs getRandomActionalbleDevs() {
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
        Map<String, Object> state = new HashMap<>();
        state.put("name", name);
        state.put("id", phase);
        return state;
    }
}
