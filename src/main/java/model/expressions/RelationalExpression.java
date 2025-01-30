package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.ArithmeticRelationalExpressionException;
import model.types.BooleanType;
import model.types.IntegerType;
import model.types.Type;
import model.values.BooleanValue;
import model.values.IntegerValue;
import model.values.Value;

public class RelationalExpression implements Expression{
    private final Expression left;
    private final Expression right;
    private final String operator;

    public RelationalExpression(String operator, Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(ISymbolTable symbolTable, IHeap heap){
        Value value_left = left.evaluate(symbolTable, heap);
        if (!value_left.getType().equals(new IntegerType())) {
            throw new ArithmeticRelationalExpressionException(1);
        }
        Value value_right = right.evaluate(symbolTable, heap);
        if (!value_right.getType().equals(new IntegerType())) {
            throw new ArithmeticRelationalExpressionException(2);
        }
        IntegerValue integer_left = (IntegerValue) value_left;
        IntegerValue integer_right = (IntegerValue) value_right;
        int int_left, int_right;
        int_left = integer_left.getValue();
        int_right = integer_right.getValue();
        switch (operator) {
            case "<" -> {
                return new BooleanValue(int_left < int_right);
            }
            case "<=" -> {
                return new BooleanValue(int_left <= int_right);
            }
            case ">" -> {
                return new BooleanValue(int_left > int_right);
            }
            case ">=" -> {
                return new BooleanValue(int_left >= int_right);
            }
            case "==" -> {
                return new BooleanValue(int_left == int_right);
            }
            case "!=" -> {
                return new BooleanValue(int_left != int_right);
            }
            default -> throw new ArithmeticRelationalExpressionException(3);
        }
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(operator, left.deepCopy(), right.deepCopy());
    }

    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

    @Override
    public Type typeCheck(TypeDictionary typeDictionary) {
        Type type_left = left.typeCheck(typeDictionary);
        Type type_right = right.typeCheck(typeDictionary);
        if (!type_left.equals(new IntegerType())) {
            throw new ArithmeticRelationalExpressionException(1);
        }
        if (!type_right.equals(new IntegerType())) {
            throw new ArithmeticRelationalExpressionException(2);
        }
        if (operator.equals("<") || operator.equals("<=") || operator.equals(">") || operator.equals(">=") || operator.equals("==") || operator.equals("!=")) {
            return new BooleanType();
        }
        throw new ArithmeticRelationalExpressionException(3);
    }
}
