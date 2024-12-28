package model.values;

import model.types.Type;

public interface Value {

    Type getType();

    Value deepCopy();

    Value compose(Value other, String operation);
}
