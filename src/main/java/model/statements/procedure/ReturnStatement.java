package model.statements.procedure;

import model.ProgramState;
import model.dt.TypeDictionary;
import model.statements.Statement;

public class ReturnStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        state.getSymbolTableStack().pop();
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ReturnStatement();
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return typeDictionary;
    }
}
