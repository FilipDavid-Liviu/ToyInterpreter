package model.statements;

import model.ProgramState;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AssignException;
import model.types.IntegerType;
import model.types.Type;
import model.values.IntegerValue;
import model.values.Value;

public class IncrementStatement implements Statement{
    private final String id;
    private Boolean decrement = false;

    public IncrementStatement(String id) {
        this.id = id;
    }

    public IncrementStatement(String id, Boolean decrement) {
        this.id = id;
        this.decrement = decrement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        if (!symbolTable.isDefined(id))
            throw new AssignException(2, id);
        Type typeId = (symbolTable.lookUp(id)).getType();
        if (!typeId.equals(new IntegerType()))
            throw new AssignException(3, id);
        if (decrement) {
            Value val = symbolTable.lookUp(id).compose(new IntegerValue(1), "-");
            symbolTable.update(id, val);
            return null;
        }
        Value val = symbolTable.lookUp(id).compose(new IntegerValue(1), "+");
        symbolTable.update(id, val);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new IncrementStatement(id, decrement);
    }

    @Override
    public String toString() {
        if (decrement)
            return this.id + "--";
        return this.id + "++";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        Type typeVariable = typeDictionary.lookUp(id);
        if (!typeVariable.equals(new IntegerType()))
            throw new AssignException(3, id);
        return typeDictionary;
    }
}
