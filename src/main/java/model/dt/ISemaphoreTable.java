package model.dt;

import model.adt.IMyDictionary;
import model.adt.Pair;

import java.util.List;

public interface ISemaphoreTable {
    void put(String key, Pair<Integer, List<Integer>> value);
    Pair<Integer, List<Integer>> lookUp(String key);
    boolean isDefined(String key);
    boolean acquire(String key, Integer id);
    void release(String key, Integer id);
    String toString();
    IMyDictionary<String, Pair<Integer, List<Integer>>> getData();
}
