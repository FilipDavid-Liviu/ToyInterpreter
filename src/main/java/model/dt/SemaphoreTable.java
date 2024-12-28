package model.dt;

import model.adt.MyDictionary;

import java.util.AbstractMap;
import java.util.List;

public class SemaphoreTable {
    MyDictionary<Integer, AbstractMap.SimpleEntry<Integer, List<Integer>>> semaphoreTable;
}
