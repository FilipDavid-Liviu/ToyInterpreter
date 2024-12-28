package model.statements;


import model.ProgramState;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AssignException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class AssignStatement implements Statement {
    private String id;
    Expression expression;

    public AssignStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        if (!symbolTable.isDefined(id))
            throw new AssignException(2, id);
        Value val = this.expression.evaluate(symbolTable, state.getHeap());
        Type typeId = (symbolTable.lookUp(id)).getType();
        if (!(val.getType()).equals(typeId))
            throw new AssignException(1, id);
        symbolTable.update(id, val);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AssignStatement(id, expression.deepCopy());
    }

    @Override
    public String toString() {
        return this.id + " = " + this.expression.toString();
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        Type typeVariable = typeDictionary.lookUp(id);
        Type typeExpression = expression.typeCheck(typeDictionary);
        if (!typeVariable.equals(typeExpression))
            throw new AssignException(1, id);
        return typeDictionary;
    }

}
