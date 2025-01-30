package model.statements;

import model.ProgramState;
import model.dt.IExecutionStack;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.BooleanConditionException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.values.Value;

public class DoWhileStatement implements Statement {
    private final Expression condition;
    private final Statement body;

    public DoWhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        IExecutionStack stack = state.getExecutionStack();
        ISymbolTable symbolTable = state.getSymbolTable();
        Value value = this.condition.evaluate(symbolTable, state.getHeap());
        if (!value.getType().equals(new BooleanType())) {
            throw new BooleanConditionException(2);
        }
        stack.push(new WhileStatement(this.condition, this.body));
        stack.push(this.body);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new DoWhileStatement(this.condition.deepCopy(), this.body.deepCopy());
    }

    @Override
    public String toString() {
        return "do {" + this.body.toString() + "} while(" + this.condition.toString() + ")";
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
