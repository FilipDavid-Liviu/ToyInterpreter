package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.adt.Pair;
import model.exceptions.SemaphoreException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTable implements ISemaphoreTable {
    IMyDictionary<String, Pair<Integer, List<Integer>>> table;
    ReentrantLock lock;

    public SemaphoreTable() {
        this.table = new MyDictionary<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public boolean isDefined(String key) {
        return this.table.isDefined(key);
    }

    @Override
    public void put(String key, Pair<Integer, List<Integer>> value) {
        if (this.isDefined(key)) {
            throw new SemaphoreException(1, key);
        } else {
            this.table.put(key, value);
        }
    }

    @Override
    public Pair<Integer, List<Integer>> lookUp(String key) {
        if (this.isDefined(key)) {
            return this.table.lookUp(key);
        } else {
            throw new SemaphoreException(2, key);
        }
    }

    @Override
    public boolean acquire(String key, Integer id) {
        if (this.isDefined(key)) {
            Pair<Integer, List<Integer>> pair = this.table.lookUp(key);
            List<Integer> list = pair.getSecond();
            if (list.contains(id)) {
                throw new SemaphoreException(1, key, id);
            } else {
                if (pair.getFirst() > list.size()) {
                    list.add(id);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            throw new SemaphoreException(2, key);
        }
    }

    @Override
    public void release(String key, Integer id) {
        if (this.isDefined(key)) {
            Pair<Integer, List<Integer>> pair = this.table.lookUp(key);
            List<Integer> list = pair.getSecond();
            if (list.contains(id)) {
                list.remove(id);
            } else {
                throw new SemaphoreException(2, key, id);
            }
        } else {
            throw new SemaphoreException(2, key);
        }

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("SemaphoreTable:");
        for (String key : this.table.getKeys()) {
            result.append("\n   ").append(key).append(" -- ").append(this.table.lookUp(key).toString());
        }
        return result.toString();
    }

    @Override
    public IMyDictionary<String, Pair<Integer, List<Integer>>> getData() {
        return this.table;
    }
}
