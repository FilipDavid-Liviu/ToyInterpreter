package model.dt;

import model.adt.IMyDictionary;
import model.adt.Pair;
import model.statements.Statement;

import java.util.List;

public class ProcedureTable implements IProcedureTable {
    IMyDictionary<String, Pair<List<String>, Statement>> table;

    @Override
    public void addProcedure(String name, List<String> params, Statement body) {
        table.put(name, new Pair<List<String>, Statement>(params, body));
    }

    @Override
    public Pair<List<String>, Statement> callProcedure(String name) {
        return table.lookUp(name);
    }

    @Override
    public IMyDictionary<String, Pair<List<String>, Statement>> getData() {
        return table;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Procedure Table:");
        for (String key : table.getKeys()) {
            result.append("\n   ").append(key).append(table.lookUp(key).getFirst().toString()).append(" {").append(table.lookUp(key).getSecond().toString()).append("}");
        }
        return result.toString();
    }
}
