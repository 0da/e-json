package cc.tantibus.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 0da - 11.02.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonValue {
    String value();
}
