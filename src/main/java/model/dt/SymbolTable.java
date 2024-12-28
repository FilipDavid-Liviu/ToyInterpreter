package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.exceptions.AssignException;
import model.exceptions.KeyNotFoundMyDictionaryException;
import model.values.Value;

public class SymbolTable implements ISymbolTable {
    IMyDictionary<String, Value> data;

    public SymbolTable() {
        this.data = new MyDictionary<>();
    }

    public void put(String key, Value value) {
        this.data.put(key, value);
    }

    @Override
    public Value lookUp(String key) {
        try {
            return this.data.lookUp(key);
        } catch (KeyNotFoundMyDictionaryException e) {
            throw new AssignException(1, key);
        }
    }

    @Override
    public boolean isDefined(String key) {
        return this.data.isDefined(key);
    }

    @Override
    public void update(String key, Value value) {
        this.data.update(key, value);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("SymbolTable:");
        for (String key : this.data.getKeys()) {
            result.append("\n   ").append(key).append("->").append(this.data.lookUp(key).toString());
        }
        return result.toString();
    }

    public IMyDictionary<String, Value> getData() {
        return data;
    }

    @Override
    public ISymbolTable deepCopy() {
        SymbolTable copy = new SymbolTable();
        for (String key : this.data.getKeys()) {
            copy.put(key, this.data.lookUp(key).deepCopy());
        }
        return copy;
    }
}
