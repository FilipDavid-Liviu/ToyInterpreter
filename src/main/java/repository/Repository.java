package repository;

import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> repo;
    private String logFilePath;

    public Repository(String logFilePath) {
        this.repo = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void add(ProgramState s) {
        this.repo.add(s);
    }

    public void logProgramState(ProgramState state) {
        try {
            BufferedWriter logFile = new BufferedWriter(new FileWriter(logFilePath, true));
            logFile.write(state.toString());
            logFile.newLine();
            logFile.close();
        } catch (IOException ignored) {
        }
    }

    public void cleanFile() {
        try {
            BufferedWriter logFile = new BufferedWriter(new FileWriter(logFilePath, false));
            logFile.write("");
            logFile.close();
        } catch (IOException ignored) {

        }
    }

    @Override
    public List<ProgramState> getProgramList() {
        return repo;
    }


    @Override
    public void setProgramList(List<ProgramState> list) {
        this.repo = list;
    }

}
