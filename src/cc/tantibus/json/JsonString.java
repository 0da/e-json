package cc.tantibus.json;

/**
 * 0da - 30.01.2018.
 */
public class JsonString implements JsonStruct {
    private final String value;

    JsonString(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
