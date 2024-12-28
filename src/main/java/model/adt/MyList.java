package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IMyList<T> {
    private List<T> data;

    public MyList(){
        this.data = new ArrayList<>();
    }

    @Override
    public void add(T element){
        this.data.add(element);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T element : data) {
            result.append("\n   ").append(element.toString());
        }
        return result.toString();
    }

    @Override
    public List<T> getAll(){
        return this.data;
    }

}
