package cc.tantibus.json;

import java.io.InputStream;

/**
 * 0da - 30.01.2018.
 */
@SuppressWarnings("ALL")
public class Json {

    //todo  add validator

    //todo  add pretty print

    public static JsonStruct decode(CharSequence json) {
        JsonParser decoder = new JsonParser(json);
        return decoder.result();
    }

    public static JsonStruct decode(InputStream is) {
        JsonParser decoder = new JsonParser(is);
        return decoder.result();
    }

    public static <T> T decode(CharSequence json, Class<T> clazz) {
        JsonStruct decode = decode(json);
        try {
            return JsonDeserializer.deserialize(decode, clazz);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T decode(InputStream is, Class<T> clazz) {
        JsonStruct decode = decode(is);
        try {
            return JsonDeserializer.deserialize(decode, clazz);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String encode(Object object) {
        //todo deal with recursive objects
        try {
            return JsonSerializer.encode(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return "";
    }

}
