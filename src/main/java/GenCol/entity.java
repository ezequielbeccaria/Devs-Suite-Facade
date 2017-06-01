/*     
 *    
 *  Author     : ACIMS(Arizona Centre for Integrative Modeling & Simulation)
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */
package GenCol;

import java.io.Serializable;

public class entity extends Object implements EntityInterface, Serializable {

    protected String name;

    public entity() {
        name = "anEntity";
    }

    public entity(String nm) {
        name = nm;
    }

    public boolean eq(String nm) {
        return getName().equals(nm);
    }

    @Override
    public Object equalName(String nm) {
        if (eq(nm)) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {    //overrides pointer equality of Object
        if (!(o instanceof entity)) {
            return false;
        } else {
            return eq(((entity) o).getName());
        }
    }

    @Override
    public ExternalRepresentation getExtRep() {
        return new ExternalRepresentation.ByteArray();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @param c
     */
    public synchronized void addSelf(ensembleCollection c) {
        c.add(this);
    }

    /**
     *
     * @param c
     */
    public synchronized void removeSelf(ensembleCollection c) {
        c.remove(this);
    }

    @Override
    public String toString() {
        return getName();
    }

    public void print() {
        System.out.println(name);
    }

}
