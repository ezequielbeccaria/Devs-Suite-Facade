
package model.devs.action;

import GenCol.entity;

/**
 * Clase generica utilizada para definir las acciones posibles sobre el entorno
 * @author ezequiel
 */
public class Action extends entity implements Comparable<Action>{    

    public Action(String name) {
        super(name);
    }

    @Override
    public int compareTo(Action aThat) {
        
        //this optimization is usually worthwhile, and can
        //always be added
        if (this == aThat) return 0;
        
        return this.name.compareTo(aThat.name);
    }
    
}
