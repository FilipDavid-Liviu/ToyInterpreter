package model.statements.procedure;

import model.ProgramState;
import model.adt.Pair;
import model.dt.ISymbolTable;
import model.dt.SymbolTable;
import model.dt.TypeDictionary;
import model.exceptions.ProcedureException;
import model.expressions.Expression;
import model.statements.Statement;

import java.util.List;

public class CallStatement implements Statement {
    private final String procedureName;
    private final List<Expression> expressions;

    public CallStatement(String procedureName, List<Expression> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }

    @Override
    public ProgramState execute(ProgramState state) {
//        if (!state.getProcedureTable().isDefined(procedureName)) {
//            throw new ProcedureException(1, procedureName);
//        }
//        Pair<List<String>, Statement> procedure = state.getProcedureTable().callProcedure(procedureName);
//        List<String> params = procedure.getFirst();
//        if (params.size() != expressions.size()) {
//            throw new ProcedureException(2, procedureName);
//        }
//        ISymbolTable symbolTable = state.getSymbolTableStack().top();
//        ISymbolTable newSymbolTable = new SymbolTable();
//        for (int i = 0; i < params.size(); i++) {
//            newSymbolTable.put(params.get(i), expressions.get(i).evaluate(symbolTable, state.getHeap()));
//        }
//        state.getSymbolTableStack().push(newSymbolTable);
//        state.getExecutionStack().push(new ReturnStatement());
//        state.getExecutionStack().push(procedure.getSecond());
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CallStatement(procedureName, expressions);
    }

    @Override
    public String toString() {
        return "call " + procedureName + expressions.toString();
    }

    @Override
    public TypeDictionary typeCheck(TypeDictionary typeDictionary) {
        return typeDictionary;
    }
}
