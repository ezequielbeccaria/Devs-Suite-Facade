
package model.devs.action;

/**
 * Clase utilizada para instancian acciones parametrizadas.
 * El parametro T determina el tipo T del parametro que se puede setear a la instancia
 * @author ezequiel
 * @param <T>
 * @date 20/07/2016  
 */
public class ParameterisedAction<T> extends Action implements Comparable<Action>{
    private T param;

    /**
     * Constructor
     * @param param
     * @param id 
     */
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
       
    @Override
    public int compareTo(Action aThat) {
        final int BEFORE = -1;
        final int EQUAL = 0;        
       
        int rta = super.compareTo(aThat);
        if(rta!=0) return rta;
        
        return ((ParameterisedAction)aThat).getParam().equals(this.getParam())?EQUAL:BEFORE;
    } 
}
