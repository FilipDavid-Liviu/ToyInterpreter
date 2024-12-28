package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.exceptions.AssignException;
import model.exceptions.KeyNotFoundMyDictionaryException;
import model.types.Type;

public class TypeDictionary {
    IMyDictionary<String, Type> data;

    public TypeDictionary() {
        this.data = new MyDictionary<>();
    }

    public void put(String key, Type type) {
        this.data.put(key, type);
    }

    public Type lookUp(String key) {
        try {
            return this.data.lookUp(key);
        } catch (KeyNotFoundMyDictionaryException e) {
            throw new AssignException(2, key);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("TypeEnvironment:");
        for (String key : this.data.getKeys()) {
            result.append("\n   ").append(key).append("->").append(this.data.lookUp(key).toString());
        }
        return result.toString();
    }

    public TypeDictionary deepCopy() {
        TypeDictionary copy = new TypeDictionary();
        for (String key : this.data.getKeys()) {
            copy.put(key, this.data.lookUp(key));
        }
        return copy;
    }
}
