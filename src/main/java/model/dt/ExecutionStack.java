package model.dt;

import model.adt.IMyStack;
import model.adt.MyStack;
import model.statements.Statement;

import java.util.ArrayList;
import java.util.List;

public class ExecutionStack implements IExecutionStack {
    private IMyStack<Statement> stack;

    public ExecutionStack() {
        stack = new MyStack<>();
    }

    @Override
    public void push(Statement statement) {
        stack.push(statement);
    }

    @Override
    public Statement pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    public String toString() {
        return "ExeStack:" + this.stack.toString();
    }

    public IExecutionStack deepCopy() {
        ExecutionStack newStack = new ExecutionStack();
        List<Statement> list = new ArrayList<>();
        for (Statement statement : stack.getAll()) {
            list.add(statement.deepCopy());
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            newStack.push(list.get(i));
        }
        return newStack;
    }

    public IMyStack<Statement> getData() {
        return stack;
    }

    @Override
    public List<Statement> getAllInList() {
        List<Statement> list = new ArrayList<>();
        for (Statement statement : stack.getAllRev()) {
            list.add(statement);
        }
        return list;
    }

    @Override
    public void clear() {
        while (!stack.isEmpty()) {
            stack.pop();
        }
    }
}
