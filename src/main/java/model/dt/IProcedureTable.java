package model.dt;

import model.adt.IMyDictionary;
import model.adt.Pair;
import model.statements.Statement;

import java.util.List;

public interface IProcedureTable {
    void addProcedure(String name, List<String> params, Statement body);
    boolean isDefined(String name);
    Pair<List<String>, Statement> callProcedure(String name);
    String toString();
    IMyDictionary<String, Pair<List<String>, Statement>> getData();
}
