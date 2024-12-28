package model.adt;

import model.exceptions.StackEmptyException;

public interface IMyStack<T>{
    T pop() throws StackEmptyException;
    void push(T v);
    boolean isEmpty();
    int size();
    Iterable<T> getAll();
    Iterable<T> getAllRev();
}
