package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.exceptions.LockException;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTable implements ILockTable{
    IMyDictionary<Integer, Integer> table;
    AtomicInteger freeLocation = new AtomicInteger(1);

    public LockTable() {
        this.table = new MyDictionary<>();
    }

    @Override
    public void put() {
        synchronized (this) {
            this.table.put(freeLocation.get(), -1);
            this.setFreeLocation();
        }
    }

    @Override
    public Integer lookUp(Integer key) {
        synchronized (this) {
            if (this.isDefined(key)) {
                return this.table.lookUp(key);
            } else {
                throw new LockException(3, key.toString());
            }
        }
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.table.isDefined(key);
    }


    @Override
    public boolean lock(Integer key, Integer id) {
        synchronized (this) {
            if (this.table.lookUp(key) == -1) {
                this.table.update(key, id);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void unlock(Integer key, Integer id) {
        synchronized (this) {
            if (Objects.equals(this.table.lookUp(key), id)) {
                this.table.update(key, -1);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("LockTable:");
        for (Integer key : this.table.getKeys()) {
            result.append("\n   ").append(key.toString()).append(" -> [").append((this.table.lookUp(key)!=-1?this.table.lookUp(key).toString():"null")).append("]");
        }
        return result.toString();
    }

    @Override
    public IMyDictionary<Integer, Integer> getData() {
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
