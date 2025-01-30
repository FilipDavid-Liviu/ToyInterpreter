package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;

public class SleepStatement implements Statement {
    private final int number;

    public SleepStatement(int number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (number > 1) {
            state.getExecutionStack().push(new SleepStatement(number - 1));
        }
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new SleepStatement(number);
    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return typeDictionary;
    }
}
