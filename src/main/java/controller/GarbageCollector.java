package controller;

import model.ProgramState;
import model.dt.*;
import model.exceptions.AddressOutOfBoundsException;
import model.values.ReferenceValue;
import model.values.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GarbageCollector {
    public static void runGarbageCollectorPrograms(List<ProgramState> states) {
        Set<Integer> reachable = new HashSet<>();
        IHeap heap = states.get(0).getHeap();
        for (ProgramState state : states) {
            for (String key : state.getSymbolTable().getData().getKeys()) {
                Value value = state.getSymbolTable().lookUp(key);
                collectReferences(value, reachable, heap);
            }
        }

        for (Integer address : new HashSet<>(heap.getData().getKeys())) {
            if (!reachable.contains(address)) {
                try {
                    System.out.println("Deallocated address: " + address);
                    heap.deallocate(address);
                } catch (AddressOutOfBoundsException e) {
                    System.err.println("Failed to deallocate address: " + address);
                }
            }
        }
    }


    public static void runGarbageCollector(ISymbolTable symbolTable, IHeap heap) {
        Set<Integer> reachable = new HashSet<>();

        for (String key : symbolTable.getData().getKeys()) {
            Value value = symbolTable.lookUp(key);
            collectReferences(value, reachable, heap);
        }

        for (Integer address : new HashSet<>(heap.getData().getKeys())) {
            if (!reachable.contains(address)) {
                try {
                    System.out.println("Deallocated address: " + address);
                    heap.deallocate(address);
                } catch (AddressOutOfBoundsException e) {
                    System.err.println("Failed to deallocate address: " + address);
                }
            }
        }
    }

    private static void collectReferences(Value value, Set<Integer> reachable, IHeap heap) {
        if (value instanceof ReferenceValue) {
            int address = ((ReferenceValue) value).getAddress();
            if (!reachable.contains(address)) {
                reachable.add(address);

                try {
                    Value heapValue = heap.read(address);
                    collectReferences(heapValue, reachable, heap);
                } catch (AddressOutOfBoundsException ignored) {
                }
            }
        }
    }
}

