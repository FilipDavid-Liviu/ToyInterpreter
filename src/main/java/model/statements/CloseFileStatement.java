package model.statements;

import model.ProgramState;
import model.dt.IFileTable;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.FileException;
import model.expressions.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;


public class CloseFileStatement implements Statement {
    Expression expression;

    public CloseFileStatement(Expression expression) {
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        IFileTable fileTable = state.getFileTable();
        Value value = this.expression.evaluate(symbolTable, state.getHeap());
        if (!(value.getType().equals(new StringType()))) {
            throw new FileException(1);
        }
        if (!fileTable.exists(((StringValue) value))) {
            throw new FileException(3);
        }
        fileTable.closeFile(((StringValue) value));
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CloseFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeFile(" + this.expression.toString() + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (this.expression.typeCheck(typeDictionary).equals(new StringType())) {
            return typeDictionary;
        }
        throw new FileException(1);
    }
}
