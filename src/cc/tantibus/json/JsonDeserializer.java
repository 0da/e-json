package cc.tantibus.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 0da - 11.02.2018.
 */
class JsonDeserializer {

    @SuppressWarnings("unchecked")
    private static <T> T primitive(JsonStruct victim, Class<T> destiny) {

        if (char.class == destiny) return (T) victim.asChar();
        if (boolean.class == destiny) return (T) victim.asBoolean();
        if (byte.class == destiny) return (T) victim.asByte();
        if (short.class == destiny) return (T) victim.asShort();
        if (int.class == destiny) return (T) victim.asInt();
        if (long.class == destiny) return (T) victim.asLong();
        if (float.class == destiny) return (T) victim.asFloat();
        if (double.class == destiny) return (T) victim.asDouble();

        return null;
    }

    static <T> T deserialize(JsonStruct victim, Class<T> destiny) throws IllegalAccessException, InstantiationException {

        if (victim == null) return null;

        //fixme enums unsupported
        if (destiny.isEnum()) return null;

        if (destiny.isPrimitive()) return primitive(victim, destiny);

        if (Object.class == destiny) return destiny.cast(victim);
        if (Character.class == destiny) return destiny.cast(victim.asChar());
        if (Boolean.class == destiny) return destiny.cast(victim.asBoolean());
        if (Byte.class == destiny) return destiny.cast(victim.asByte());
        if (Short.class == destiny) return destiny.cast(victim.asShort());
        if (Integer.class == destiny) return destiny.cast(victim.asInt());
        if (Long.class == destiny) return destiny.cast(victim.asLong());
        if (Float.class == destiny) return destiny.cast(victim.asFloat());
        if (Double.class == destiny) return destiny.cast(victim.asDouble());
        if (BigInteger.class == destiny) return destiny.cast(victim.asBigInteger());
        if (BigDecimal.class == destiny) return destiny.cast(victim.asBigDecimal());
        if (String.class == destiny) return destiny.cast(victim.toString());

        if (destiny.isArray()) {
            JsonStruct[] structures = victim.asArray();
            Object array = Array.newInstance(destiny.getComponentType(), structures.length);

            for (int i = 0; i < structures.length; i++)
                Array.set(array, i, deserialize(structures[i], destiny.getComponentType()));

            return destiny.cast(array);
        }

        if (!victim.isJsonObject()) return null;

        T o = destiny.newInstance();
        JsonObject jsonObject = (JsonObject) victim;

        Field[] fields = destiny.getDeclaredFields();
        for (Field field : fields) {
            JsonValue json = field.getAnnotation(JsonValue.class);
            if (json == null) continue;

            JsonStruct param = jsonObject.get(json.value());
            if (param == null) continue;

            Object temp = deserialize(param, field.getType());

            field.setAccessible(true);
            field.set(o, temp);

        }

        return o;
    }
}
