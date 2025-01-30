package model.types;

import model.values.Value;

public interface Type {
    Value getDefaultValue();
    String toString();
    boolean equals(Object obj);
}
