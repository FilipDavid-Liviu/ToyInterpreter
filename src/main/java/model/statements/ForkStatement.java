package model.statements;

import model.ProgramState;
import model.dt.ExecutionStack;
import model.dt.TypeDictionary;

public class ForkStatement implements Statement {
    private Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return new ProgramState(new ExecutionStack(), state.getSymbolTable().deepCopy(),
                state.getHeap(), state.getOutput(), state.getFileTable(), state.getSemaphoreTable(), statement);
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return statement.typeCheck(typeDictionary);
    }

}
