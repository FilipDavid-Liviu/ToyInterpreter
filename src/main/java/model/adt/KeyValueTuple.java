package model.adt;

public class KeyValueTuple {
    private final String key;
    private final String value;
    private final String value2;

    public KeyValueTuple(String key, String value, String value2) {
        this.key = key;
        this.value = value;
        this.value2 = value2;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    public String getValue2() {
        return value2;
    }
    @Override
    public String toString() {
        return key + " -> " + value + " -> " + value2;
    }
}
