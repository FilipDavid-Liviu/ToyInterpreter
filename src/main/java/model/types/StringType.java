package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type{
    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "StringType";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }
}
