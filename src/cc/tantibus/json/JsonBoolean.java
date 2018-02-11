package cc.tantibus.json;

/**
 * 0da - 10.02.2018.
 */
public class JsonBoolean implements JsonStruct {
    private final boolean value;

    JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean asBoolean() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
