package model.predictive;

import GenCol.entity;
import java.util.Objects;

/**
 * Class that extens entity to wraps a Double
 * @author Ezequiel Beccaria
 */
public class DoubleEntity extends entity {

    Double value;

    public DoubleEntity(Double d) {
        this.value = d;
    }

    public DoubleEntity(double d) {
        this.value = d;
    }

    public boolean greaterThan(entity ent) {
        return (this.value > ((DoubleEntity) ent).getValue());
    }

    public void setValue(Double v) {
        value = v;
    }

    public void setValue(double v) {
        value = v;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    public boolean equal(entity ent) {
        return Objects.equals(this.value, ((DoubleEntity) ent).getValue());
    }

    @Override
    public boolean equals(Object ent) { //needed for Relation
        return equal((entity) ent);
    }

    public entity copy() {
        return (entity) new DoubleEntity(getValue());
    }

    @Override
    public String getName() {
        return Double.toString(value);
    }
}
