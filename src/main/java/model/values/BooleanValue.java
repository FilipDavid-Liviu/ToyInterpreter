package model.values;

import model.exceptions.InvalidOperationException;
import model.types.BooleanType;
import model.types.Type;

public class BooleanValue implements Value {
    private final boolean value;

    public BooleanValue(boolean val) {
        this.value = val;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }

    @Override
    public Value deepCopy() {
        return new BooleanValue(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof BooleanValue) {
            return value == ((BooleanValue) another).getValue();
        }
        return false;
    }

    @Override
    public Value compose(Value other, String operation) {
        if (!(other.getType() instanceof BooleanType)) {
            throw new InvalidOperationException();
        }
        BooleanValue otherValue = (BooleanValue) other;
        return switch (operation) {
            case "and" -> new BooleanValue(value && otherValue.getValue());
            case "or" -> new BooleanValue(value || otherValue.getValue());
            case "==" -> new BooleanValue(value == otherValue.getValue());
            case "!=" -> new BooleanValue(value != otherValue.getValue());
            default ->
                    throw new InvalidOperationException("BooleanValue", operation);
        };
    }

}
