package model;

import model.adt.IMyStack;
import model.adt.MyStack;
import model.dt.*;
import model.exceptions.StackEmptyException;
import model.statements.*;

public class ProgramState {
    private static int nextId = 1;
    private static synchronized int generateId() {
        return nextId++;
    }
    private final int id;
    private IExecutionStack execStack;
    private ISymbolTable symbolTable;
    private IMyStack<ISymbolTable> symbolTableStack;
    private IOutput output;
    private IFileTable fileTable;
    private IHeap heap;
    private ISemaphoreTable semaphoreTable;
    private ILockTable lockTable;
    private IProcedureTable procedureTable;

    public int getId() {
        return id;
    }

    public IExecutionStack getExecutionStack() {
        return execStack;
    }

    public ISymbolTable getSymbolTable() {
        return symbolTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public IOutput getOutput() {
        return output;
    }

    public IFileTable getFileTable() {
        return fileTable;
    }

    public ISemaphoreTable getSemaphoreTable() {
        return semaphoreTable;
    }

    public ILockTable getLockTable() {
        return lockTable;
    }

    public IMyStack<ISymbolTable> getSymbolTableStack() {
        return symbolTableStack;
    }

    public IProcedureTable getProcedureTable() {
        return procedureTable;
    }


    public ProgramState(IExecutionStack execStack, ISymbolTable symbolTable, IHeap heap, IOutput output, IFileTable fileTable, Statement program) {
        this.id = generateId();
        this.execStack = execStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.output = output;
        this.fileTable = fileTable;
        this.semaphoreTable = new SemaphoreTable();
        this.lockTable = new LockTable();
        this.execStack.push(program);
    }

    public ProgramState(IExecutionStack execStack, ISymbolTable symbolTable, IHeap heap, IOutput output, IFileTable fileTable, ISemaphoreTable semaphoreTable, Statement program) {
        this.id = generateId();
        this.execStack = execStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.output = output;
        this.fileTable = fileTable;
        this.semaphoreTable = semaphoreTable;
        this.execStack.push(program);
    }

    public ProgramState(IExecutionStack execStack, IMyStack<ISymbolTable> symbolTableStack, IHeap heap, IOutput output, IFileTable fileTable, IProcedureTable procedureTable, Statement program) {
        this.id = generateId();
        this.execStack = execStack;
        this.symbolTableStack = symbolTableStack;
        this.heap = heap;
        this.output = output;
        this.fileTable = fileTable;
        this.procedureTable = procedureTable;
        this.execStack.push(program);
    }

    public ProgramState(IExecutionStack execStack, IMyStack<ISymbolTable> symbolTableStack, IHeap heap, IOutput output, IFileTable fileTable, IProcedureTable procedureTable, ISemaphoreTable semaphoreTable, ILockTable lockTable, Statement program) {
        this.id = generateId();
        this.execStack = execStack;
        this.symbolTableStack = symbolTableStack;
        this.heap = heap;
        this.output = output;
        this.fileTable = fileTable;
        this.procedureTable = procedureTable;
        this.semaphoreTable = semaphoreTable;
        this.lockTable = lockTable;
        this.execStack.push(program);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +this.execStack.toString() + "\n" + this.symbolTable.toString() + "\n" + this.heap.toString() + "\n" +
                this.output.toString() + "\n" + this.fileTable.toString() + "\n" + this.semaphoreTable.toString() + "\n\n";
    }

    public String toStringExecSym() {
        return "ID: " + id + "\n" +this.execStack.toString() + "\n" + this.symbolTable.toString();
    }

    public String toStringRest() {
        return this.heap.toString() + "\n" + this.output.toString() + "\n" + this.fileTable.toString() + "\n" + this.semaphoreTable.toString() + "\n\n";
    }

    public boolean isNotCompleted() {
        return !this.execStack.isEmpty();
    }

    public ProgramState oneStep() throws StackEmptyException {
        if (this.execStack.isEmpty())
            throw new StackEmptyException();
        Statement currentStatement = this.execStack.pop();
        try {
            return currentStatement.execute(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.execStack.clear();
            return null;
        }
    }
}
