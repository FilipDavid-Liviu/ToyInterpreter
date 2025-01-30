package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.ArithmeticRelationalExpressionException;
import model.exceptions.AssignException;
import model.exceptions.InvalidOperationException;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.types.IntegerType;

public class ForStatement implements Statement{
    private String id;
    private Expression exp1;
    private Expression exp2;
    private Expression exp3;
    private Statement statement;

    public ForStatement(String id, Expression exp1, Expression exp2, Expression exp3, Statement statement) {
        this.id = id;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        state.getExecutionStack().push(new WhileStatement(new RelationalExpression("<", new VariableExpression(id), exp2), new CompoundStatement(statement, new AssignStatement(id, exp3))));
        state.getExecutionStack().push(new AssignStatement(id, exp1));
        state.getExecutionStack().push(new VariableDeclarationStatement(id, new IntegerType()));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ForStatement(id, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "for(" + id + " = " + exp1 + "; " + id + " < " + exp2 + "; " + id + " = " + exp3 + ") {" + statement + "}";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        typeDictionary.put(id, new IntegerType());
        if (!exp1.typeCheck(typeDictionary).equals(new IntegerType()))
            throw new AssignException(1, id);
        if (!exp2.typeCheck(typeDictionary).equals(new IntegerType()))
            throw new ArithmeticRelationalExpressionException(2);
        if (!exp3.typeCheck(typeDictionary).equals(new IntegerType()))
            throw new AssignException(1, id);

        statement.typeCheck(typeDictionary);
        return typeDictionary;
    }
}
