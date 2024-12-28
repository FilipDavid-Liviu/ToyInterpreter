package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.AssignException;
import model.types.Type;
import model.values.Value;

public class SwapStatement implements Statement{
    private String id1;
    private String id2;

    public SwapStatement(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public String toString() {
        return "swap(" + id1 + ", " + id2 + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (!state.getSymbolTable().isDefined(id1))
            throw new AssignException(2, id1);
        if (!state.getSymbolTable().isDefined(id2))
            throw new AssignException(2, id2);
        if (!state.getSymbolTable().lookUp(id1).getType().equals(state.getSymbolTable().lookUp(id2).getType()))
            throw new AssignException(5, id1 + "\" and \"" + id2);
        Value value1 = state.getSymbolTable().lookUp(id1);
        Value value2 = state.getSymbolTable().lookUp(id2);
        state.getSymbolTable().put(id1, value2);
        state.getSymbolTable().put(id2, value1);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new SwapStatement(id1, id2);
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        Type typeVariable1 = typeDictionary.lookUp(id1);
        Type typeVariable2 = typeDictionary.lookUp(id2);
        if (!typeVariable1.equals(typeVariable2))
            throw new AssignException(5, id1 + "\" and \"" + id2);
        return typeDictionary;
    }
}
