package repository;
import model.ProgramState;

import java.util.List;

public interface IRepository {
    void add(ProgramState s);
    void cleanFile();
    void logProgramState(ProgramState state);
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> list);
}
