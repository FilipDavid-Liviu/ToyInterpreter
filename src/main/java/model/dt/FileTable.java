package model.dt;

import model.adt.IMyDictionary;
import model.adt.MyDictionary;
import model.exceptions.MyFileNotFoundException;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileTable implements IFileTable {
    IMyDictionary<StringValue, BufferedReader> files;

    public FileTable() {
        this.files = new MyDictionary<>();
    }

    @Override
    public void openFile(StringValue key) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(key.getValue()));
        this.files.put(key, reader);
    }

    @Override
    public void closeFile(StringValue key) throws MyFileNotFoundException {
        try {
            this.files.lookUp(key).close();
            this.files.remove(key);
        } catch (Exception e) {
            throw new MyFileNotFoundException(key.getValue());
        }
    }

    @Override
    public boolean exists(StringValue key) {
        return this.files.isDefined(key);
    }

    @Override
    public BufferedReader get(StringValue key) {
        return this.files.lookUp(key);
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        answer.append("FileTable:");
        for (StringValue name : files.getKeys()) {
            answer.append("\n   ").append(name.toString());
        }
        return answer.toString();
    }

    @Override
    public IMyDictionary<StringValue, BufferedReader> getData() {
        return this.files;
    }
}
