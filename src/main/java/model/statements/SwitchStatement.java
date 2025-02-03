package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.SwitchException;
import model.expressions.BinaryExpression;
import model.expressions.Expression;
import model.expressions.LogicExpression;
import model.expressions.RelationalExpression;
import model.types.IntegerType;

public class SwitchStatement implements Statement{
    private final Expression switchExpression;
    private final Statement defaultStatement;
    private final Expression caseExpression1;
    private final Statement caseStatement1;
    private final Expression caseExpression2;
    private final Statement caseStatement2;

    public SwitchStatement(Expression switchExpression, Expression caseExpression1, Statement caseStatement1, Expression caseExpression2, Statement caseStatement2, Statement defaultStatement) {
        this.switchExpression = switchExpression;
        this.defaultStatement = defaultStatement;
        this.caseExpression1 = caseExpression1;
        this.caseStatement1 = caseStatement1;
        this.caseExpression2 = caseExpression2;
        this.caseStatement2 = caseStatement2;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Expression cond1, cond2;
        if (this.switchExpression.evaluate(state.getSymbolTable(), state.getHeap()).getType().equals(new IntegerType())){
            cond1 = new RelationalExpression("==", this.switchExpression, this.caseExpression1);
            cond2 = new RelationalExpression("==", this.switchExpression, this.caseExpression2);
        }
        else {
            cond1 = new LogicExpression("==", this.switchExpression, this.caseExpression1);
            cond2 = new LogicExpression("==", this.switchExpression, this.caseExpression2);
        }
        Statement ifStatement = new IfStatement(cond1, this.caseStatement1, new IfStatement(cond2, this.caseStatement2, this.defaultStatement));
        state.getExecutionStack().push(ifStatement);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new SwitchStatement(this.switchExpression, this.caseExpression1, this.caseStatement1, this.caseExpression2, this.caseStatement2, this.defaultStatement);
    }

    @Override
    public String toString() {
        return "switch(" + this.switchExpression.toString() + ") {case (" + this.caseExpression1.toString() + ") : " + this.caseStatement1.toString() + "; case (" + this.caseExpression2.toString() + ") : " + this.caseStatement2.toString() + "; default: " + this.defaultStatement.toString() + "}";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {

        //Statement ifStatement = new IfStatement(new BinaryExpression("==", this.switchExpression, this.caseExpression1), this.caseStatement1, new IfStatement(new BinaryExpression("==", this.switchExpression, this.caseExpression2), this.caseStatement2, this.defaultStatement));
        //ifStatement.typeCheck(typeDictionary);
        if (!this.switchExpression.typeCheck(typeDictionary).equals(this.caseExpression1.typeCheck(typeDictionary))) {
            throw new SwitchException();
        }
        if (!this.switchExpression.typeCheck(typeDictionary).equals(this.caseExpression2.typeCheck(typeDictionary))) {
            throw new SwitchException();
        }
        this.caseStatement1.typeCheck(typeDictionary.deepCopy());
        this.caseStatement2.typeCheck(typeDictionary.deepCopy());
        this.defaultStatement.typeCheck(typeDictionary.deepCopy());
        return typeDictionary;
    }
}
