package cc.tantibus.json;

import java.util.ArrayList;

/**
 * 0da - 30.01.2018.
 */
class JsonCase<O extends JsonStruct> {

    private final O inner;
    private final Action<O> action;

    private JsonCase(O inner, Action<O> action) {
        this.action = action;
        this.inner = inner;
    }

    static JsonCase<JsonArray> array() {
        return new JsonCase<>(new JsonArray(), ArrayList::add);
    }

    static JsonCase<JsonObject> object() {
        return new JsonCase<>(new JsonObject(), new Action<JsonObject>() {

            private String key;

            @Override
            public void push(JsonObject producer, JsonStruct value) {
                if (key == null) key = value.toString();
                else {
                    producer.put(key, value);
                    key = null;
                }
            }
        });
    }

    O getInner() {
        return inner;
    }

    void push(JsonStruct value) {
        action.push(inner, value);
    }

    private interface Action<O> {
        void push(O producer, JsonStruct value);
    }
}
