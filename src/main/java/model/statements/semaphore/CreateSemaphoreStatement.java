package model.statements.semaphore;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.SemaphoreException;
import model.expressions.Expression;
import model.statements.Statement;
import model.types.IntegerType;
import model.values.IntegerValue;
import model.values.Value;

import java.util.concurrent.locks.ReentrantLock;


public class CreateSemaphoreStatement implements Statement {
    private final String id;
    private final Expression value;
    private static final ReentrantLock lock = new ReentrantLock();

    public CreateSemaphoreStatement(String id, Expression value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            Value val = this.value.evaluate(state.getSymbolTable(), state.getHeap());
            if (!state.getSymbolTable().isDefined(this.id)) {
                throw new SemaphoreException(2, this.id);
            }
            if (!state.getSymbolTable().lookUp(this.id).getType().equals(new IntegerType())) {
                throw new SemaphoreException(1, this.id);
            }
            if (!(val.getType()).equals(new IntegerType())) {
                throw new SemaphoreException(3, this.id);
            }
            state.getSymbolTable().update(this.id, new IntegerValue(state.getSemaphoreTable().getFreeLocation().get()));
            state.getSemaphoreTable().put(((IntegerValue)(val)).getValue());
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CreateSemaphoreStatement(this.id, this.value);
    }

    @Override
    public String toString() {
        return "newSemaphore(" + this.id + ", " + this.value + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!typeDictionary.lookUp(this.id).equals(new IntegerType())) {
            throw new SemaphoreException(1, this.id);
        }
        if (!this.value.typeCheck(typeDictionary).equals(new IntegerType())) {
            throw new SemaphoreException(3, this.id);
        }
        return typeDictionary;
    }
}
