package model.statements;

import model.ProgramState;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AssignException;
import model.exceptions.BooleanConditionException;
import model.expressions.Expression;
import model.types.BooleanType;
import model.values.BooleanValue;
import model.values.Value;

public class ConditionalAssignmentStatement implements Statement{
    private final String id;
    private final Expression condition;
    private final Expression thenE;
    private final Expression elseE;

    public ConditionalAssignmentStatement(String id, Expression condition, Expression thenS, Expression elseS) {
        this.id = id;
        this.condition = condition;
        this.thenE = thenS;
        this.elseE = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        if (!symbolTable.isDefined(id))
            throw new AssignException(2, id);
        Value value = this.condition.evaluate(symbolTable, state.getHeap());
        Value thenValue = this.thenE.evaluate(symbolTable, state.getHeap());
        Value elseValue = this.elseE.evaluate(symbolTable, state.getHeap());
        if (!value.getType().equals(new BooleanType())) {
            throw new BooleanConditionException(1);
        }
        if (!thenValue.getType().equals(symbolTable.lookUp(id).getType())) {
            throw new AssignException(1, id);
        }
        if (!elseValue.getType().equals(symbolTable.lookUp(id).getType())) {
            throw new AssignException(1, id);
        }
        if (((BooleanValue) value).getValue()) {
            symbolTable.update(id, this.thenE.evaluate(symbolTable, state.getHeap()));
        } else {
            symbolTable.update(id, this.elseE.evaluate(symbolTable, state.getHeap()));
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ConditionalAssignmentStatement(id, condition.deepCopy(), thenE.deepCopy(), elseE.deepCopy());
    }

    @Override
    public String toString() {
        return id + " = (" + condition.toString() + ") ? " + thenE.toString() + " : " + elseE.toString();
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!this.condition.typeCheck(typeDictionary).equals(new BooleanType())) {
            throw new BooleanConditionException(1);
        }
        if (!typeDictionary.lookUp(id).equals(this.thenE.typeCheck(typeDictionary))) {
            throw new AssignException(1, id);
        }
        if (!typeDictionary.lookUp(id).equals(this.elseE.typeCheck(typeDictionary))) {
            throw new AssignException(1, id);
        }
        return typeDictionary;
    }
}
