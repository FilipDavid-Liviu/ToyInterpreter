package model.statements.lock;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.LockException;
import model.exceptions.SemaphoreException;
import model.statements.Statement;
import model.types.IntegerType;
import model.values.IntegerValue;
import model.values.Value;

import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements Statement {
    private final String id;
    private static final ReentrantLock lock = new ReentrantLock();

    public NewLockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        if (!state.getSymbolTable().isDefined(this.id)) {
            lock.unlock();
            throw new LockException(2, this.id);
        }
        if (!state.getSymbolTable().lookUp(this.id).getType().equals(new IntegerType())) {
            lock.unlock();
            throw new LockException(1, this.id);
        }
        //state.getSymbolTable().update(this.id, new IntegerValue(state.getLockTable().getFreeLocation().get()));
        //state.getLockTable().put();
        lock.unlock();
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NewLockStatement(this.id);
    }

    @Override
    public String toString() {
        return "newLock(" + this.id + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!typeDictionary.lookUp(this.id).equals(new IntegerType())) {
            throw new LockException(1, this.id);
        }
        return typeDictionary;
    }
}
