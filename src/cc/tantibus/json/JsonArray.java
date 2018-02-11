package cc.tantibus.json;

import java.util.ArrayList;

/**
 * 0da - 30.01.2018.
 */

public class JsonArray extends ArrayList<JsonStruct> implements JsonStruct {

    @Override
    public JsonStruct[] asArray() {
        return this.toArray(new JsonStruct[size()]);
    }
}

