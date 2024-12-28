package model.adt;

import model.exceptions.StackEmptyException;

import java.util.ArrayList;
import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack;
    
    public MyStack(){
        stack = new Stack<>();
    }

    @Override
    public T pop() throws StackEmptyException {
        if (this.isEmpty()) throw new StackEmptyException();
        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty(){
        return this.stack.isEmpty();
    }


    public ArrayList<T> getStackReversed() {
        ArrayList<T> reversed = new ArrayList<>();
        for (int i = this.stack.size() - 1; i >= 0; i--) {
            reversed.add(this.stack.get(i));
        }
        return reversed;
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T elem : this.getStackReversed()) {
            result.append("\n   ").append(elem.toString());
        }
        return result.toString();
    }

    @Override
    public Iterable<T> getAll() {
        return this.stack;
    }

    @Override
    public Iterable<T> getAllRev() {
        return this.getStackReversed();
    }
}
