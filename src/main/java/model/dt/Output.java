package model.dt;

import model.adt.IMyList;
import model.adt.MyList;

public class Output implements IOutput {
    IMyList<String> data;

    public Output() {
        this.data = new MyList<>() {
        };
    }

    @Override
    public void appendToOutput(String string) {
        this.data.add(string);
    }


    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        answer.append("Output:");
        for (String elem : this.data.getAll()) {
            answer.append("\n   ").append(elem);
        }
        return answer.toString();
    }

    @Override
    public IMyList<String> getData() {
        return this.data;
    }

}
