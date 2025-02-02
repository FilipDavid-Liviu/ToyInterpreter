package model.statements.semaphore;

import model.ProgramState;
import model.adt.Pair;
import model.dt.ISemaphoreTable;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.SemaphoreException;
import model.statements.Statement;
import model.types.IntegerType;
import model.values.IntegerValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements Statement {
    private final String id;
    private static final Lock lock = new ReentrantLock();

    public AcquireStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            ISemaphoreTable semaphoreTable = state.getSemaphoreTable();
            ISymbolTable symbolTable = state.getSymbolTable();
            if (!symbolTable.isDefined(this.id)) {
                throw new SemaphoreException(2, this.id);
            }
            if (!symbolTable.lookUp(this.id).getType().equals(new IntegerType())) {
                throw new SemaphoreException(1, this.id);
            }
            Integer address = ((IntegerValue) symbolTable.lookUp(this.id)).getValue();
            if (!semaphoreTable.isDefined(address)) {
                throw new SemaphoreException(4, address.toString());
            }
            Pair<Integer, List<Integer>> pair = semaphoreTable.lookUp(address);
            List<Integer> list = pair.getSecond();
            if (list.contains(state.getId())) {
                //throw new SemaphoreException(1, this.id, state.getId());
                return null;
            } else {
                if (pair.getFirst() > list.size()) {
                    semaphoreTable.acquire(address, state.getId());
                } else {
                    state.getExecutionStack().push(this);
                }
            }
        }
        finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AcquireStatement(this.id);
    }

    @Override
    public String toString() {
        return "acquire(" + this.id + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!typeDictionary.lookUp(this.id).equals(new IntegerType())) {
            throw new SemaphoreException(1, this.id);
        }
        return typeDictionary;
    }
}
