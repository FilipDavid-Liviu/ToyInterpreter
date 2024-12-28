package model.dt;

import model.adt.IMyList;


public interface IOutput {
    void appendToOutput(String string);
    String toString();
    IMyList<String> getData();
}
