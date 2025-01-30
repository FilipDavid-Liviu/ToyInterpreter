package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.expressions.Expression;


public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        state.getOutput().appendToOutput((expression.evaluate(state.getSymbolTable(), state.getHeap())).toString());
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        expression.typeCheck(typeDictionary);
        return typeDictionary;
    }
}
