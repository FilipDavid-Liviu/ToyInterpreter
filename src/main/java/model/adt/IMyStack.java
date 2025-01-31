package model.adt;

public interface IMyStack<T>{
    T pop();
    T top();
    void push(T v);
    boolean isEmpty();
    int size();
    Iterable<T> getAll();
    Iterable<T> getAllRev();
    IMyStack<T> deepCopy();
}
