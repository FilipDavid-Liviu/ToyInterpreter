package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.BooleanConditionException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.values.Value;

public class ForLoopStatement implements Statement {
    private Statement initialization;
    private Expression condition;
    private Statement step;
    private Statement body;

    public ForLoopStatement(Statement initialization, Expression condition, Statement step, Statement body) {
        this.initialization = initialization;
        this.condition = condition;
        this.step = step;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = this.condition.evaluate(state.getSymbolTable(), state.getHeap());
        if (!value.getType().equals(new BooleanType())) {
            throw new BooleanConditionException(3);
        }
        state.getExecutionStack().push(new WhileStatement(condition, new CompoundStatement(body, step)));
        state.getExecutionStack().push(initialization);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ForLoopStatement(initialization.deepCopy(), condition.deepCopy(), step.deepCopy(), body.deepCopy());
    }

    @Override
    public String toString() {
        return "for (" + initialization + "; " + condition + "; " + step + ") " + "{" + this.body.toString() + "}";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!this.condition.typeCheck(typeDictionary).equals(new BooleanType())) {
            throw new BooleanConditionException(3);
        }
        this.initialization.typeCheck(typeDictionary);
        this.step.typeCheck(typeDictionary);
        this.body.typeCheck(typeDictionary);
        return typeDictionary;
    }
}
