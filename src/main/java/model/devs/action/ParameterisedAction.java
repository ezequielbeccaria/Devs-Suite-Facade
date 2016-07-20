
package model.devs.action;

/**
 * Clase utilizada para instancian acciones parametrizadas.
 * El parametro T determina el tipo T del parametro que se puede setear a la instancia
 * @author ezequiel
 * @param <T>
 * @date 20/07/2016  
 */
public class ParameterisedAction<T> extends Action{
    private T param;

    public ParameterisedAction(T param, String id) {
        super(id);
        this.param = param;       
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }   
    
}
