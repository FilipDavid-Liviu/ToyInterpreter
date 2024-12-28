package model.expressions;

import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.LogicExpressionException;
import model.types.BooleanType;
import model.types.Type;
import model.values.BooleanValue;
import model.values.Value;

public class LogicExpression implements Expression {
    private Expression left;
    private Expression right;
    private String operator;

    public LogicExpression(String operator, Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(ISymbolTable symbolTable, IHeap heap) throws LogicExpressionException {
        Value value_left, value_right;
        value_left = left.evaluate(symbolTable, heap);
        value_right = right.evaluate(symbolTable, heap);
        if (!value_left.getType().equals(new BooleanType())) {
            throw new LogicExpressionException(1);
        }
        if (!value_right.getType().equals(new BooleanType())) {
            throw new LogicExpressionException(2);
        }
        switch (operator) {
            case "and", "&&" -> {
                return new BooleanValue(((BooleanValue) value_left).getValue() && ((BooleanValue) value_right).getValue());
            }
            case "or", "||" -> {
                return new BooleanValue(((BooleanValue) value_left).getValue() || ((BooleanValue) value_right).getValue());
            }
            case "!=" -> {
                return new BooleanValue(((BooleanValue) value_left).getValue() != ((BooleanValue) value_right).getValue());
            }
            case "==" -> {
                return new BooleanValue(((BooleanValue) value_left).getValue() == ((BooleanValue) value_right).getValue());
            }
            default -> throw new LogicExpressionException(3);
        }
    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(operator, left.deepCopy(), right.deepCopy());
    }

    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

    @Override
    public Type typeCheck(TypeDictionary typeDictionary) {
        Type firstType = left.typeCheck(typeDictionary);
        Type secondType = right.typeCheck(typeDictionary);

        if (!firstType.equals(new BooleanType())) {
            throw new LogicExpressionException(1);
        }
        if (!secondType.equals(new BooleanType())) {
            throw new LogicExpressionException(2);
        }
        if (operator.equals("and") || operator.equals("&&") || operator.equals("or") || operator.equals("||") || operator.equals("!=") || operator.equals("==")) {
            return new BooleanType();
        }
        throw new LogicExpressionException(3);
    }
}
