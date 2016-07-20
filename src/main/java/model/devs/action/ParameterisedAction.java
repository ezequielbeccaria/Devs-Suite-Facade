
package model.devs.action;

/**
 *
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
