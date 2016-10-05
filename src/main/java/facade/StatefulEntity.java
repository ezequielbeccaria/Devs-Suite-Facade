
package facade;

import java.util.Map;

/**
 * Interfaz que implementan todos los objetos que poseen un estado observable
 * @author ezequiel
 */
public interface StatefulEntity {
    /**
     * Map que contiene el estado relevante de la instancia en forma de pares <key,value>
     * @return 
     */
    public Map getState();
    /**
     * Nombre de la entidad que muestra su estado observable
     * @return 
     */
    public String getName();
}
