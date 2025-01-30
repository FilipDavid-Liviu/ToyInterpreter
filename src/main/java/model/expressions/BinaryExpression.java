package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.InvalidOperationException;
import model.types.BooleanType;
import model.types.IntegerType;
import model.types.StringType;
import model.types.Type;
import model.values.Value;

public class BinaryExpression implements Expression{
    private final Expression left;
    private final Expression right;
    private final String operator;

    public BinaryExpression(String operator, Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(ISymbolTable symbolTable, IHeap heap) {
        return left.evaluate(symbolTable, heap).compose(right.evaluate(symbolTable, heap), operator);
    }

    @Override
    public Expression deepCopy() {
        return new BinaryExpression(operator, right.deepCopy(), left.deepCopy());
    }

    public String toString() {
        return left.toString() + " " + operator + " " + right.toString() ;
    }

    @Override
    public Type typeCheck(TypeDictionary typeDictionary) throws InvalidOperationException {
        Type firstType = left.typeCheck(typeDictionary);
        Type secondType = right.typeCheck(typeDictionary);

        if (firstType == null || !firstType.equals(secondType)) {
            throw new InvalidOperationException();
        }

        return switch (operator) {
            case "+" -> {
                if (firstType.equals(new IntegerType())) {
                    yield new IntegerType();
                }
                else {
                    yield new StringType();
                }
            }
            case "-", "*", "/" -> new IntegerType();
            case "<", "<=", ">", ">=", "==", "!=", "&&", "||", "or", "and" -> new BooleanType();
            default -> firstType;
        };
    }
}
