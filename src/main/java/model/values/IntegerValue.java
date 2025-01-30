package model.values;

import model.exceptions.DivisionByZeroException;
import model.exceptions.InvalidOperationException;
import model.types.IntegerType;
import model.types.Type;

public class IntegerValue implements Value{
    private final int value;

    public IntegerValue(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new IntegerType();
    }

    @Override
    public Value deepCopy() {
        return new IntegerValue(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntegerValue) {
            return value == ((IntegerValue) another).getValue();
        }
        return false;
    }

    @Override
    public Value compose(Value other, String operation) {
        if (!(other.getType() instanceof IntegerType)) {
            throw new InvalidOperationException();
        }
        IntegerValue otherValue = (IntegerValue) other;
        return switch (operation) {
            case "+" -> new IntegerValue(value + otherValue.getValue());
            case "-" -> new IntegerValue(value - otherValue.getValue());
            case "*" -> new IntegerValue(value * otherValue.getValue());
            case "/" -> {
                if (otherValue.getValue() == 0) {
                    throw new DivisionByZeroException();
                }
                yield new IntegerValue(value / otherValue.getValue());
            }
            case "==" -> new BooleanValue(value == otherValue.getValue());
            case "!=" -> new BooleanValue(value != otherValue.getValue());
            case "<" -> new BooleanValue(value < otherValue.getValue());
            case "<=" -> new BooleanValue(value <= otherValue.getValue());
            case ">" -> new BooleanValue(value > otherValue.getValue());
            case ">=" -> new BooleanValue(value >= otherValue.getValue());
            default ->
                    throw new InvalidOperationException("IntegerValue", operation);
        };
    }

}
