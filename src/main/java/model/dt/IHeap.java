package model.dt;

import model.adt.IMyDictionary;
import model.exceptions.AddressOutOfBoundsException;
import model.values.Value;

public interface IHeap {
    int allocate(Value value);
    Value read(int address) throws AddressOutOfBoundsException;
    void write(int address, Value value) throws AddressOutOfBoundsException;
    void deallocate(int address) throws AddressOutOfBoundsException;
    String toString();
    IMyDictionary<Integer, Value> getData();
}
