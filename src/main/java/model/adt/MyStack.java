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
    public T pop() {
        if (this.isEmpty()) throw new StackEmptyException();
        return this.stack.pop();
    }

    @Override
    public T top() {
        if (this.isEmpty()) throw new StackEmptyException();
        return this.stack.peek();
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

    @Override
    public IMyStack<T> deepCopy() {
        IMyStack<T> newStack = new MyStack<>();
        for (T elem : this.stack) {
            newStack.push(elem);
        }
        return newStack;
    }
}
