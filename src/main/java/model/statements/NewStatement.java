package model.statements;

import model.ProgramState;
import model.dt.IHeap;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AssignException;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class NewStatement implements Statement {
    String varName;
    Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        if(!symbolTable.isDefined(varName)){
            throw new AssignException(2, varName);
        }
        if(!(symbolTable.lookUp(varName).getType() instanceof ReferenceType)){
            throw new AssignException(4, varName);
        }
        Value value = expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(((ReferenceValue) symbolTable.lookUp(varName)).getLocationType())){
            throw new AssignException(1, varName);
        }
        int address = state.getHeap().allocate(expression.evaluate(state.getSymbolTable(), state.getHeap()));
        symbolTable.update(varName, new ReferenceValue(address, ((ReferenceType) symbolTable.lookUp(varName).getType()).getInnerType()));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NewStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression.toString() + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        Type expressionType = expression.typeCheck(typeDictionary);
        if(typeDictionary.lookUp(varName).equals(new ReferenceType(expressionType))){
            return typeDictionary;
        }
        throw new AssignException(1, varName);
    }
}
