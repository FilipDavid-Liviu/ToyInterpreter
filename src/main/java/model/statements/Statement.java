package model.statements;
import model.ProgramState;
import model.dt.TypeDictionary;

public interface Statement {
    ProgramState execute(ProgramState state);
    Statement deepCopy();
    String toString();
    TypeDictionary typeCheck(TypeDictionary typeDictionary);
}
