package model.statements;

import model.ProgramState;
import model.dt.ISymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.AssignException;
import model.exceptions.FileException;
import model.expressions.Expression;
import model.types.IntegerType;
import model.types.StringType;
import model.values.IntegerValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ReadFileStatement implements Statement {
    Expression expression;
    String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable symbolTable = state.getSymbolTable();
        if (!symbolTable.isDefined(this.variableName)) {
            throw new AssignException(2, variableName);
        }
        if (!symbolTable.lookUp(this.variableName).getType().equals(new IntegerValue(0).getType())) {
            throw new AssignException(3, variableName);
        }
        Value value = this.expression.evaluate(symbolTable, state.getHeap());
        if (!(value.getType().equals(new StringType()))) {
            throw new FileException(1);
        }
        if (!state.getFileTable().exists((StringValue) value)) {
            throw new FileException(4);
        }
        BufferedReader object = state.getFileTable().get(((StringValue) value));
        try {
            String line = object.readLine();
            IntegerValue valueForVariable;
            if (line == null) {
                valueForVariable = new IntegerValue(0);
            } else {
                try {
                    int val = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    throw new AssignException(1, variableName);
                }
                valueForVariable = new IntegerValue(Integer.parseInt(line));
            }
            symbolTable.update(this.variableName, valueForVariable);
        } catch (Exception e) {
            throw new FileException(2);
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
       return new ReadFileStatement(this.expression.deepCopy(), this.variableName);
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + ", " + this.variableName + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        if (!this.expression.typeCheck(typeDictionary).equals(new StringType())) {
            throw new FileException(1);
        }
        if (!typeDictionary.lookUp(this.variableName).equals(new IntegerType())) {
            throw new AssignException(3, this.variableName);
        }
        return typeDictionary;
    }
}
