package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(ISymbolTable symbolTable, IHeap heap) {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    public String toString() {
        return this.value.toString();
    }

    @Override
    public Type typeCheck(TypeDictionary typeDictionary) {
        return value.getType();
    }
}
