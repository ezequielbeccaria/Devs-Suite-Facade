package model.predictive;

import GenCol.entity;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Class that extens entity to wraps a Double[]
 * @author Ezequiel Beccaria
 */
public class DoubleVectorEntity extends entity {

    Double[] array;

    public DoubleVectorEntity(Double[] d) {
        this.array = d;
    }

    public DoubleVectorEntity(double[] d) {
        array = new Double[d.length];
        for(int i=0; i<d.length; i++)
            this.array[i] = d[i];
    }

    public void setValue(Double[] v) {
        array = v;
    }

    public void setValue(double[] d) {
        array = new Double[d.length];
        for(int i=0; i<d.length; i++)
            this.array[i] = d[i];
    }

    public Double[] getValue() {
        return array;
    }

    @Override
    public void print() {
        System.out.print(Arrays.toString(array));
    }

    public boolean equal(entity ent) {
        return java.util.Objects.deepEquals(this.array, ((DoubleVectorEntity) ent).getValue());
    }

    @Override
    public boolean equals(Object ent) { //needed for Relation
        return equal((entity) ent);
    }

    public entity copy() {
        return (entity) new DoubleVectorEntity(getValue());
    }

    @Override
    public String getName() {
        return ArrayUtils.toString(array);
    }
}
