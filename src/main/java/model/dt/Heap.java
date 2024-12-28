package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.exceptions.AddressOutOfBoundsException;
import model.exceptions.KeyNotFoundMyDictionaryException;
import model.values.Value;

import java.util.concurrent.atomic.AtomicInteger;

public class Heap implements IHeap{
    IMyDictionary<Integer, Value> heap;
    AtomicInteger freeLocation = new AtomicInteger(1);

    public Heap() {
        heap = new MyDictionary<>();
    }

    @Override
    public int allocate(Value value) {
        heap.put(freeLocation.get(), value);
        int location = freeLocation.get();
        this.setFreeLocation();
        return location;
    }

    @Override
    public Value read(int address) throws AddressOutOfBoundsException {
        try {
            return heap.lookUp(address);
        } catch (KeyNotFoundMyDictionaryException e) {
            throw new AddressOutOfBoundsException(address);
        }
    }

    @Override
    public void write(int address, Value value) throws AddressOutOfBoundsException {
        if (!heap.getKeys().contains(address)) {
            throw new AddressOutOfBoundsException(address);
        }
        heap.put(address, value);
    }

    @Override
    public void deallocate(int address) throws AddressOutOfBoundsException {
        try {
            heap.remove(address);
            this.setFreeLocation();
        } catch (KeyNotFoundMyDictionaryException e) {
            throw new AddressOutOfBoundsException(address);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Heap:");
        for (Integer key : heap.getKeys()) {
            result.append("\n   ").append(key).append("->").append(this.heap.lookUp(key).toString());
        }
        return result.toString();
    }

    public IMyDictionary<Integer, Value> getData() {
        return heap;
    }

    private void setFreeLocation() {
        for(int i = 1; i < 100; i++){
            if(!heap.getKeys().contains(i)){
                freeLocation.set(i);
                break;
            }
        }
    }
}
