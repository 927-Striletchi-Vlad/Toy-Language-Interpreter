package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value{
    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    public boolean getValue() {
        return val;
    }

    @Override
    public String toString() {
        return "BoolValue{" +
                "val=" + val +
                '}';
    }
}
