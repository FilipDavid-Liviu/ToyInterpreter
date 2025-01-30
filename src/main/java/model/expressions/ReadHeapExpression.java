package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AddressOutOfBoundsException;
import model.exceptions.ReadHeapException;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class ReadHeapExpression implements Expression{
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(ISymbolTable symbolTable, IHeap heap) {
        Value value = expression.evaluate(symbolTable, heap);
        if(!(value.getType() instanceof ReferenceType))
            throw new ReadHeapException();
        if (!heap.getData().isDefined(((ReferenceValue)value).getAddress()))
            throw new AddressOutOfBoundsException(((ReferenceValue)value).getAddress());
        return heap.read(((ReferenceValue)value).getAddress());
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }

    @Override
    public Type typeCheck(TypeDictionary typeDictionary) {
        Type type = expression.typeCheck(typeDictionary);
        if(!(type instanceof ReferenceType))
            throw new ReadHeapException();
        return ((ReferenceType)type).getInnerType();
    }
}
