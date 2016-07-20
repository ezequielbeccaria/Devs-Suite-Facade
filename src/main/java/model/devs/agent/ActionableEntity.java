
package model.devs.agent;

import java.util.Set;
import model.devs.action.ParameterisedAction;

/**
 * Interfaz que implementan todas las entidades que puede recibir acciones por parte
 * del agente del sistema
 * @author ezequiel
 * @date 20/07/2016  
 */
public interface ActionableEntity {
    /**
     * Metodo que devuelve la acciones posibles en base al estado actual de la instancia
     * @return 
     */
    public Set<ParameterisedAction> getAllowedActions();
}
