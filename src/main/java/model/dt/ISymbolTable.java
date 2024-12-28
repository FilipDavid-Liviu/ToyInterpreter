package model.dt;

import model.adt.IMyDictionary;
import model.values.Value;

public interface ISymbolTable {
    void put(String key, Value value);
    Value lookUp(String key);
    boolean isDefined(String key);
    void update(String key, Value value);
    String toString();
    IMyDictionary<String, Value> getData();
    ISymbolTable deepCopy();
}
