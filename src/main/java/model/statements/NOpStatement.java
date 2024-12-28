package model.statements;

import model.ProgramState;
import model.dt.TypeDictionary;

public class NOpStatement implements Statement {
    public NOpStatement() {
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NOpStatement();
    }

    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return typeDictionary;
    }

}
