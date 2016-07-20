
package model.devs.agent;

import java.util.Set;
import model.devs.action.ParameterisedAction;

/**
 *
 * @author ezequiel
 * @date 20/07/2016  
 */
public interface ActionableEntity {
    public Set<ParameterisedAction> getAllowedActions();
}
