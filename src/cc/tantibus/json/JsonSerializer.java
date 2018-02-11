package cc.tantibus.json;

import java.lang.reflect.*;

/**
 * 0da - 11.02.2018.
 */
class JsonSerializer {

    static String encode(Object victim) throws IllegalAccessException {
        if (victim == null) return null;

        if (isNumber(victim) || isBoolean(victim)) return String.valueOf(victim);

        Class<?> clazz = victim.getClass();

        //string should be wrapped(\" \\ \t \n etc.).
        if (isSimple(clazz)) return wrap(victim);


        StringBuilder builder = new StringBuilder();
        boolean first = true;


        if (clazz.isArray()) {
            builder.append('[');
            for (int i = 0, l = Array.getLength(victim); i < l; i++) {
                Object o = encode(Array.get(victim, i));
                if (o == null) continue;
                if (first) first = false;
                else builder.append(',');
                builder.append(o);

            }
            return builder.append(']').toString();
        }


        builder.append('{');
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            JsonValue json = field.getAnnotation(JsonValue.class);
            if (json == null) continue;


            field.setAccessible(true);
            Object o = encode(field.get(victim));

            if (o == null) continue;
            if (first) first = false;
            else builder.append(',');
            builder
                    .append(wrap(json.value()))
                    .append(":")
                    .append(o);
        }
        builder.append('}');
        return builder.toString();
    }


    private static boolean isBoolean(Object o) {
        return o instanceof Boolean;
    }

    private static boolean isNumber(Object o) {
        return o instanceof Number;
    }


    private static String wrap(Object o) {
        //  [ \ b f n r t " / ]
        return '"' + String.valueOf(o)
                .replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\b", "\\\\b")
                .replaceAll("\f", "\\\\f")
                .replaceAll("\n", "\\\\n")
                .replaceAll("\r", "\\\\r")
                .replaceAll("\t", "\\\\t")
                .replaceAll("\"", "\\\\\"")
                .replaceAll("/", "\\\\\\/")
                + '"';
    }


    private static boolean isSimple(Class<?> clazz) {

        return clazz == Character.class
                // || clazz == Boolean.class
                // || clazz == Byte.class
                // || clazz == Short.class
                // || clazz == Integer.class
                // || clazz == Long.class
                // || clazz == Float.class
                // || clazz == Double.class
                // || clazz == BigInteger.class
                // || clazz == BigDecimal.class
                || clazz == String.class
                || clazz == java.util.Date.class
                || clazz == java.sql.Date.class
                || clazz == java.sql.Time.class
                || clazz == java.sql.Timestamp.class
                // || clazz.isPrimitive()
                || clazz.isEnum();
    }
}
