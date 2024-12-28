package model.statements;

import model.ProgramState;
import model.dt.IExecutionStack;
import model.dt.TypeDictionary;
import model.exceptions.BooleanConditionException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.values.BooleanValue;
import model.values.Value;

public class WhileStatement implements Statement {
    private Expression condition;
    private Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IExecutionStack stack = state.getExecutionStack();
        Value value = this.condition.evaluate(state.getSymbolTable(), state.getHeap());
        if (!value.getType().equals(new BooleanType())) {
            throw new BooleanConditionException(2);
        }
        if (((BooleanValue) value).getValue()) {
            stack.push(this);
            stack.push(this.body);
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(this.condition.deepCopy(), this.body.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + this.condition.toString() + ") {" + this.body.toString() + "}";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!this.condition.typeCheck(typeDictionary).equals(new BooleanType())) {
            throw new BooleanConditionException(2);
        }
        this.body.typeCheck(typeDictionary);
        return typeDictionary;
    }
}
