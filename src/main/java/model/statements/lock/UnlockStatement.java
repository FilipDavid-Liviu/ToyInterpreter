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

public class UnlockStatement implements Statement {
    private final String id;
    private static final Lock lock = new ReentrantLock();

    public UnlockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        //ILockTable lockTable = state.getLockTable();
        ISymbolTable symbolTable = state.getSymbolTable();
        if (!symbolTable.isDefined(this.id)){
            lock.unlock();
            throw new LockException(2, this.id);
        }
        if (!symbolTable.lookUp(this.id).getType().equals(new IntegerType())) {
            lock.unlock();
            throw new LockException(1, this.id);
        }
        Integer address = ((IntegerValue)symbolTable.lookUp(this.id)).getValue();
//        if (!lockTable.isDefined(address)) {
//            lock.unlock();
//            throw new LockException(4, address.toString());
//        }
//        Integer locked = lockTable.lookUp(address);
//        if (locked == state.getId())
//            lockTable.unlock(address, state.getId());
        lock.unlock();
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new UnlockStatement(this.id);
    }

    @Override
    public String toString() {
        return "unlock(" + this.id + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!typeDictionary.lookUp(this.id).equals(new IntegerType())) {
            throw new LockException(1, this.id);
        }
        return typeDictionary;
    }
}
