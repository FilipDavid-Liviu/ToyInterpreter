package model.statements;

import model.ProgramState;
import model.dt.IExecutionStack;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.BooleanConditionException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.values.BooleanValue;
import model.values.Value;

public class IfStatement implements Statement {
    private final Expression condition;
    private final Statement thenS;
    private final Statement elseS;

    public IfStatement(Expression condition, Statement thenS, Statement elseS) {
        this.condition = condition;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IExecutionStack stack = state.getExecutionStack();
        ISymbolTable symbolTable = state.getSymbolTable();
        Value value = this.condition.evaluate(symbolTable, state.getHeap());
        if (!value.getType().equals(new BooleanType())) {
            throw new BooleanConditionException(1);
        }
        if (((BooleanValue) value).getValue()) {
            stack.push(this.thenS);
        } else {
            stack.push(this.elseS);
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.condition.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }

    @Override
    public String toString() {
        return "if(" + this.condition.toString() + ") then {" + this.thenS.toString() + "} else {" + this.elseS.toString() + "}";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!this.condition.typeCheck(typeDictionary).equals(new BooleanType())) {
            throw new BooleanConditionException(1);
        }
        this.thenS.typeCheck(typeDictionary.deepCopy());
        this.elseS.typeCheck(typeDictionary.deepCopy());
        return typeDictionary;
    }
}
