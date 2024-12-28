package model.dt;


import model.adt.IMyDictionary;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public interface IFileTable {
    void openFile(StringValue key) throws FileNotFoundException;
    void closeFile(StringValue key);
    boolean exists(StringValue key);
    BufferedReader get(StringValue  key);
    String toString();
    IMyDictionary<StringValue, BufferedReader> getData();
}
