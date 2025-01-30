package model.statements;

import model.ProgramState;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.FileException;
import model.exceptions.MyFileNotFoundException;
import model.expressions.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.FileNotFoundException;

public class OpenFileStatement implements Statement {
    Expression expression;

    public OpenFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        Value value = this.expression.evaluate(symbolTable, state.getHeap());
        if (!(value.getType().equals(new StringType()))) {
            throw new FileException(1);
        }
        if(state.getFileTable().exists(((StringValue) value))) {
            throw new FileException(2);
        }
        try {
            //BufferedReader reader = new BufferedReader(new FileReader(((StringValue) value).getValue()));
            state.getFileTable().openFile(((StringValue) value));
        } catch (FileNotFoundException e) {
            throw new MyFileNotFoundException(((StringValue) value).getValue());
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new OpenFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openFile(" + this.expression.toString() + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (this.expression.typeCheck(typeDictionary).equals(new StringType())) {
            return typeDictionary;
        }
        throw new FileException(1);
    }
}
