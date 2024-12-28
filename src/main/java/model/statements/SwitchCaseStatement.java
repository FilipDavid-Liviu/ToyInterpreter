package model.statements;

import model.ProgramState;
import model.dt.IExecutionStack;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.SwitchException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class SwitchCaseStatement implements Statement {
    private Expression switchExpression;
    private Statement defaultStatement;
    private CaseStatement[] caseStatements;

    public SwitchCaseStatement(Expression switchExpression, Statement defaultStatement, CaseStatement[] caseStatements){
        this.switchExpression = switchExpression;
        this.defaultStatement = defaultStatement;
        this.caseStatements = caseStatements;
    }

    @Override
    public ProgramState execute(ProgramState state){
        IExecutionStack stack = state.getExecutionStack();
        ISymbolTable symbolTable = state.getSymbolTable();
        Value switchValue = this.switchExpression.evaluate(symbolTable, state.getHeap());
        for (CaseStatement caseStatement : caseStatements) {
            Value caseValue = caseStatement.getExpression().evaluate(symbolTable, state.getHeap());
            if (switchValue.equals(caseValue)) {
                stack.push(caseStatement.getStatement());
                return null;
            }
        }
        stack.push(defaultStatement);
        return null;
    }


    @Override
    public Statement deepCopy(){
        CaseStatement[] newCaseStatements = new CaseStatement[caseStatements.length];
        for(int i = 0; i < caseStatements.length; i++){
            newCaseStatements[i] = new CaseStatement(caseStatements[i].getExpression().deepCopy(), caseStatements[i].getStatement().deepCopy());
        }
        return new SwitchCaseStatement(this.switchExpression.deepCopy(), this.defaultStatement.deepCopy(), newCaseStatements);
    }


    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("switch(" + this.switchExpression.toString() + ") {");
        for (CaseStatement caseStatement : caseStatements) {
            str.append("case ").append(caseStatement.getExpression().toString()).append(": ").append(caseStatement.getStatement().toString()).append(" ");
        }
        str.append("default: ").append(defaultStatement.toString()).append("}");
        return str.toString();
    }


    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary){
        Type switchType = this.switchExpression.typeCheck(typeDictionary);
        for (CaseStatement caseStatement : caseStatements) {
            Type caseType = caseStatement.getExpression().typeCheck(typeDictionary);
            if (!switchType.equals(caseType)) {
                throw new SwitchException();
            }
            caseStatement.typeCheck(typeDictionary.deepCopy());
        }
        defaultStatement.typeCheck(typeDictionary.deepCopy());

        return typeDictionary;
    }

}
