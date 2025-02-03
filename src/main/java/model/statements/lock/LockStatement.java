package model.statements.lock;

import model.ProgramState;
import model.dt.ILockTable;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.LockException;
import model.statements.Statement;
import model.types.IntegerType;
import model.values.IntegerValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements Statement {
    private final String id;
    private static final Lock lock = new ReentrantLock();

    public LockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) {
//        lock.lock();
//        try {
//            ILockTable lockTable = state.getLockTable();
//            ISymbolTable symbolTable = state.getSymbolTable();
//            if (!symbolTable.isDefined(this.id)) {
//                throw new LockException(2, this.id);
//            }
//            if (!symbolTable.lookUp(this.id).getType().equals(new IntegerType())) {
//                throw new LockException(1, this.id);
//            }
//            Integer address = ((IntegerValue) symbolTable.lookUp(this.id)).getValue();
//            if (!lockTable.isDefined(address)) {
//                throw new LockException(4, address.toString());
//            }
//            Integer locked = lockTable.lookUp(address);
//            if (locked == -1) {
//                lockTable.lock(address, state.getId());
//            } else {
//                state.getExecutionStack().push(this);
//            }
//        } finally {
//            lock.unlock();
//        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new LockStatement(this.id);
    }

    @Override
    public String toString() {
        return "lock(" + this.id + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!typeDictionary.lookUp(this.id).equals(new IntegerType())) {
            throw new LockException(1, this.id);
        }
        return typeDictionary;
    }
}
