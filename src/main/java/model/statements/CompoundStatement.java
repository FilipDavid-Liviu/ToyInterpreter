package model.statements;
import model.ProgramState;
import model.dt.IExecutionStack;
import model.dt.TypeDictionary;

public class CompoundStatement implements Statement {

    private final Statement first, second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IExecutionStack execStack = state.getExecutionStack();
        execStack.push(second);
        execStack.push(first);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    public String toString() {
        return "(" + this.first + ";   " + this.second + ")";
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return second.typeCheck(first.typeCheck(typeDictionary));
    }
}
