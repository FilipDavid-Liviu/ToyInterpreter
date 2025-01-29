package controller;

import model.ProgramState;
import repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private boolean display_flag;
    private ExecutorService executor;
    public Controller(IRepository repo) {
        this.repo = repo;
        this.display_flag = true;
        this.executor = null;

        this.repo.cleanFile();
        if (this.display_flag) {
            //this.repo.getProgramList().forEach(program -> System.out.println(program.toString() + "\n"));
        }
        this.repo.getProgramList().forEach(e -> this.repo.logProgramState(e));
        executor = Executors.newFixedThreadPool(5);
        this.removeCompletedPrograms();
    }


    public void setDisplay_flag(boolean display_flag) {
        this.display_flag = display_flag;
    }

    public IRepository getRepo() {
        return repo;
    }

    public void oneStepForAllPrg(){
        this.removeCompletedPrograms();
        if (this.repo.getProgramList().isEmpty())
            return;
        List<Callable<ProgramState>> stepList = repo.getProgramList().stream()
                .map(program -> (Callable<ProgramState>) (program::oneStep))
                .toList();

        List<ProgramState> newPrograms = null;
        try {
            newPrograms = executor.invokeAll(stepList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }  catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        newPrograms.forEach(prg -> this.repo.add(prg));
        GarbageCollector.runGarbageCollectorPrograms(this.repo.getProgramList());
        if (this.display_flag) {
            if (this.repo.getProgramList().size() > 1) {
                this.repo.getProgramList().forEach(program -> System.out.println(program.toStringExecSym()));
                System.out.println(this.repo.getProgramList().getFirst().toStringRest() + "\n");
            }
            else this.repo.getProgramList().forEach(program -> System.out.println(program.toString() + "\n"));
        }
        this.repo.getProgramList().forEach(e -> this.repo.logProgramState(e));
    }

    public void allSteps(){
        while (true) {
            this.removeCompletedPrograms();
            if (this.repo.getProgramList().isEmpty())
                break;
            try {
                this.oneStepForAllPrg();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        executor.shutdown();
    }

    public void shutdownExecutor(){
        executor.shutdown();
    }

    
    public void removeCompletedPrograms(){
        this.repo.setProgramList(
                this.repo.getProgramList().stream()
                        .filter(ProgramState::isNotCompleted)
                        .collect(Collectors.toList()));
    }
}
