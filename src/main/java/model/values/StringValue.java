package model.values;

import model.exceptions.InvalidOperationException;
import model.types.StringType;
import model.types.Type;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue) {
            return value.equals(((StringValue) another).getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public Value compose(Value other, String operation) {
        if (!(other.getType() instanceof StringType)) {
            throw new InvalidOperationException();
        }
        StringValue otherValue = (StringValue) other;
        return switch (operation) {
            case "+" -> new StringValue(value + otherValue.getValue());
            default ->
                    throw new InvalidOperationException("StringValue", operation);
        };
    }
}
