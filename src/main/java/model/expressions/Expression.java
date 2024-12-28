package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.types.Type;
import model.values.Value;

public interface Expression {
    Value evaluate(ISymbolTable symbolTable, IHeap heap);
    Expression deepCopy();
    String toString();
    Type typeCheck(TypeDictionary typeDictionary);

}
