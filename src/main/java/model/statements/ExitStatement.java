package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;

public class ExitStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        while (!state.getExecutionStack().isEmpty()) {
            state.getExecutionStack().pop();
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ExitStatement();
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return typeDictionary;
    }
}
