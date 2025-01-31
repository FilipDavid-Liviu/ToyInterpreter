package model.dt;

import model.adt.IMyDictionary;

import java.util.concurrent.atomic.AtomicInteger;

public interface ILockTable {
    void put();
    Integer lookUp(Integer key);
    boolean isDefined(Integer key);
    boolean lock(Integer key, Integer id);
    void unlock(Integer key, Integer id);
    String toString();
    IMyDictionary<Integer, Integer> getData();
    AtomicInteger getFreeLocation();
}
