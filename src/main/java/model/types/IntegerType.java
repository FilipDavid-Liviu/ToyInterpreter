package model.types;

import model.values.IntegerValue;
import model.values.Value;

public class IntegerType implements Type {
    @Override
    public Value getDefaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public String toString() {
        return "IntegerType";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerType;
    }

}
