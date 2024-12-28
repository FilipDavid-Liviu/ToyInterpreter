package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.expressions.Expression;

public class CaseStatement implements Statement {
    private Expression expression;
    private Statement statement;

    public CaseStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CaseStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "case " + this.expression.toString() + ": " + this.statement.toString();
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        this.statement.typeCheck(typeDictionary.deepCopy());
        return typeDictionary;
    }
}
