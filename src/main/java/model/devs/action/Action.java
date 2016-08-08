
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
    public int compareTo(Action that) {
        return this.name.compareTo(that.name);
    }
    
}
