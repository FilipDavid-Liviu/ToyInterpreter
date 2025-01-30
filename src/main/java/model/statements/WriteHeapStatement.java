package model.statements;

import model.ProgramState;
import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AddressOutOfBoundsException;
import model.exceptions.AssignException;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class WriteHeapStatement implements Statement {
    private final String varName;
    private final Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        if(!symbolTable.isDefined(varName)){
            throw new AssignException(2, varName);
        }
        if(!(symbolTable.lookUp(varName) instanceof ReferenceValue)) {
            throw new AssignException(4, varName);
        }
        int address = ((ReferenceValue) symbolTable.lookUp(varName)).getAddress();
        if(!heap.getData().isDefined(address)) {
            throw new AddressOutOfBoundsException(address);
        }
        Value value = expression.evaluate(symbolTable, heap);
        if(!value.getType().equals(((ReferenceType) symbolTable.lookUp(varName).getType()).getInnerType())) {
            throw new AssignException(5, varName);
        }
        state.getHeap().write(address, value);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new WriteHeapStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "writeHeap(" + varName + ", " + expression.toString() + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        Type expressionType = expression.typeCheck(typeDictionary);
        if(typeDictionary.lookUp(varName).equals(new ReferenceType(expressionType))){
            return typeDictionary;
        }
        throw new AssignException(1, varName);
    }
}
