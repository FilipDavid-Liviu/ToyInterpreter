package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.adt.Pair;
import model.exceptions.SemaphoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTable implements ISemaphoreTable {
    IMyDictionary<Integer, Pair<Integer, List<Integer>>> table;
    AtomicInteger freeLocation = new AtomicInteger(1);

    public SemaphoreTable() {
        this.table = new MyDictionary<>();
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.table.isDefined(key);
    }

    @Override
    public void put(Integer size) {
        synchronized (this) {
            this.table.put(freeLocation.get(), new Pair<Integer, List<Integer>>(size, new ArrayList<Integer>()));
            this.setFreeLocation();
        }
    }

    @Override
    public Pair<Integer, List<Integer>> lookUp(Integer key) {
        synchronized (this) {
            if (this.isDefined(key)) {
                return this.table.lookUp(key);
            } else {
                throw new SemaphoreException(4, key.toString());
            }
        }
    }

    @Override
    public boolean acquire(Integer key, Integer id) {
        synchronized (this) {
            Pair<Integer, List<Integer>> pair = this.table.lookUp(key);
            List<Integer> list = pair.getSecond();
            //if (list.contains(id))
            //    throw new SemaphoreException(1, key, id);
            //else {
            if (pair.getFirst() > list.size()) {
                list.add(id);
                return true;
            } else {
                return false;
            }
            //}
        }
    }

    @Override
    public void release(Integer key, Integer id) {
        synchronized (this) {
            Pair<Integer, List<Integer>> pair = this.table.lookUp(key);
            List<Integer> list = pair.getSecond();
            if (list.contains(id))
                list.remove(id);
            // else throw new SemaphoreException(2, key, id);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("SemaphoreTable:");
        for (Integer key : this.table.getKeys()) {
            result.append("\n   ").append(key.toString()).append(" -- ").append(this.table.lookUp(key).toString());
        }
        return result.toString();
    }

    @Override
    public IMyDictionary<Integer, Pair<Integer, List<Integer>>> getData() {
        return this.table;
    }

    private void setFreeLocation() {
        for(int i = 1; i < 100; i++){
            if(!table.getKeys().contains(i)){
                freeLocation.set(i);
                break;
            }
        }
    }

    @Override
    public AtomicInteger getFreeLocation() {
        return freeLocation;
    }
}
