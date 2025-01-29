package model.statements.semaphore;

import model.ProgramState;
import model.adt.Pair;
import model.dt.TypeDictionary;
import model.exceptions.SemaphoreException;
import model.expressions.Expression;
import model.statements.Statement;
import model.types.IntegerType;
import model.values.IntegerValue;
import model.values.Value;

import java.util.ArrayList;

public class NewSemaphoreStatement implements Statement {
    private final String id;
    private final Expression value;

    public NewSemaphoreStatement(String id, Expression value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value val = this.value.evaluate(state.getSymbolTable(), state.getHeap());
        if (!(val.getType()).equals(new IntegerType())) {
            throw new SemaphoreException(3, this.id);
        }
        state.getSemaphoreTable().put(this.id, new Pair<>(((IntegerValue)(this.value.evaluate(state.getSymbolTable(), state.getHeap()))).getValue(), new ArrayList<>()));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NewSemaphoreStatement(this.id, this.value);
    }

    @Override
    public String toString() {
        return "newSemaphore(" + this.id + ", " + this.value + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!this.value.typeCheck(typeDictionary).equals(new IntegerType())) {
            throw new SemaphoreException(3, this.id);
        }
        return typeDictionary;
    }
}
