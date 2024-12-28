package model.types;

import model.values.BooleanValue;
import model.values.Value;

public class BooleanType implements Type {
    @Override
    public Value getDefaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public String toString() {
        return "BooleanType";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanType;
    }
}
