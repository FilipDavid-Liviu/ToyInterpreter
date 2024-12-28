package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type{
    private Type innerType;

    public ReferenceType(Type innerType){
        this.innerType = innerType;
    }

    public Type getInnerType(){
        return innerType;
    }

    @Override
    public Value getDefaultValue() {
        return new ReferenceValue(0, innerType);
    }

    @Override
    public String toString() {
        return "Reference(" + innerType.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ReferenceType) && ((ReferenceType)obj).innerType != null && ((ReferenceType) obj).innerType.equals(this.innerType);
    }
}
