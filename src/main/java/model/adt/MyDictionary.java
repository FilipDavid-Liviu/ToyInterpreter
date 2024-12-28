package model.adt;

import model.exceptions.KeyNotFoundMyDictionaryException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MyDictionary<K, V> implements IMyDictionary<K, V>{
    private ConcurrentMap<K, V> map;

    public MyDictionary() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V lookUp(K key) throws KeyNotFoundMyDictionaryException {
        if (!this.isDefined(key)) {
            throw new KeyNotFoundMyDictionaryException(key.toString());
        }
        return this.map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return this.map.get(key) != null;
    }

    @Override
    public void update(K key, V value) {
        this.put(key, value);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

    @Override
    public List<K> getKeys() {
        return new ArrayList<>(this.map.keySet());
    }

    @Override
    public void remove(K key) {
        this.map.remove(key);
    }

    @Override
    public MyDictionary<K, V> deepCopy() {
        MyDictionary<K, V> copy = new MyDictionary<>();
        for (K key : this.map.keySet()) {
            copy.put(key, this.map.get(key));
        }
        return copy;
    }

}
