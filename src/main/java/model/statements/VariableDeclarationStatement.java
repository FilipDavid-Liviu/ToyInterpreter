package model.statements;


import model.ProgramState;
import model.dt.TypeDictionary;
import model.exceptions.VariableAlreadyDeclaredException;
import model.types.Type;

public class VariableDeclarationStatement implements Statement {

    private String id;
    Type type;

    public VariableDeclarationStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if(state.getSymbolTable().isDefined(id))
            throw new VariableAlreadyDeclaredException(id);

        state.getSymbolTable().put(id, type.getDefaultValue());
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(id, type);
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.id;
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        typeDictionary.put(id, type);
        return typeDictionary;
    }

}
