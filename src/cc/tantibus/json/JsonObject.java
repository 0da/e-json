package cc.tantibus.json;

import java.util.HashMap;

/**
 * 0da - 30.01.2018.
 */
public class JsonObject extends HashMap<String, JsonStruct> implements JsonStruct {

    @Override
    public boolean isJsonObject() {
        return true;
    }
}
