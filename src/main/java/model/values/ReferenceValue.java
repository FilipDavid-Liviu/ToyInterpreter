package model.values;

import model.exceptions.InvalidOperationException;
import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value{
    private int address;
    private Type locationType;

    public ReferenceValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return address;
    }

    public Type getLocationType(){
        return locationType;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(address, locationType);
    }

    @Override
    public Value compose(Value other, String operation) {
        throw new InvalidOperationException("reference");
    }

    @Override
    public String toString() {
        if (address == 0)
            return "(null, " + locationType.toString() + ")";
        return "(" + address + ", " + locationType.toString() + ")";
    }
}
