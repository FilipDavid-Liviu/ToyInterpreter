package view.commands;

import controller.Controller;

public class RunExample extends Command {
    private Controller controller;
    public RunExample(String key, String desc, Controller controller){
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();
        }
        catch (RuntimeException e){
                System.out.println(e.getMessage());
                System.exit(1);
        }
    }
}
