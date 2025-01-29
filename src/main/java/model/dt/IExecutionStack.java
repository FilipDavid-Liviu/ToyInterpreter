package model.dt;

import model.adt.IMyStack;
import model.statements.Statement;

import java.util.List;

public interface IExecutionStack {
    void push(Statement statement);
    Statement pop();
    boolean isEmpty();
    String toString();
    int size();
    IExecutionStack deepCopy();
    IMyStack<Statement> getData();
    List<Statement> getAllInList();
    void clear();
}
