package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(ISymbolTable symbolTable, IHeap heap) {
        return symbolTable.lookUp(variable);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(variable);
    }

    public String toString() {
        return variable;
    }

    @Override
    public Type typeCheck(TypeDictionary typeDictionary) {
        return typeDictionary.lookUp(variable);
    }
}