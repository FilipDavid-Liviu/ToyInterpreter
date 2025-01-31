package model.dt;

import model.adt.IMyDictionary;
import model.adt.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface ISemaphoreTable {
    void put(Integer size);
    Pair<Integer, List<Integer>> lookUp(Integer key);
    boolean isDefined(Integer key);
    boolean acquire(Integer key, Integer id);
    void release(Integer key, Integer id);
    String toString();
    IMyDictionary<Integer, Pair<Integer, List<Integer>>> getData();
    AtomicInteger getFreeLocation();
}
