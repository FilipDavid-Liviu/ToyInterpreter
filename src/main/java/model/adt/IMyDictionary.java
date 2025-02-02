package model.adt;

import model.exceptions.KeyNotFoundMyDictionaryException;

import java.util.List;

public interface IMyDictionary<K, V> {
    void put(K key, V value);
    V lookUp(K key);
    boolean isDefined(K key);
    void update(K key, V value);
    List<K> getKeys();
    void remove(K key);
    MyDictionary<K,V> deepCopy();
}
