package cc.tantibus.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 0da - 30.01.2018.
 */
public interface JsonStruct {
    JsonStruct NULL = new JsonString(null);
    JsonStruct TRUE = new JsonBoolean(true);
    JsonStruct FALSE = new JsonBoolean(false);


    //  Character.class
    default Character asChar() {
        return '\uFFFD'; // �
    }

    //  Boolean.class
    default Boolean asBoolean() {
        return false;
    }

    //  Byte.class
    default Byte asByte() {
        return 0;
    }

    //  Short.class
    default Short asShort() {
        return 0;
    }

    //  Integer.class
    default Integer asInt() {
        return 0;
    }

    //  Long.class
    default Long asLong() {
        return 0L;
    }

    //  Float.class
    default Float asFloat() {
        return 0f;
    }

    //  Double.class
    default Double asDouble() {
        return 0d;
    }

    //  BigInteger.class
    default BigInteger asBigInteger() {
        return BigInteger.ZERO;
    }

    //  BigDecimal.class
    default BigDecimal asBigDecimal() {
        return BigDecimal.ZERO;
    }

    default <T> T as(Class<T> clazz) {
        try {
            return JsonDeserializer.deserialize(this, clazz);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
    //  String.class
    //  toString()

    default JsonStruct[] asArray() {
        return new JsonStruct[]{this};
    }

    default boolean isJsonObject() {
        return false;
    }

    //  java.util.Date.class
    //  java.sql.Date.class
    //  java.sql.Time.class
    //  java.sql.Timestamp.class
    //  clazz.isPrimitive()
    //  clazz.isEnum();
}
