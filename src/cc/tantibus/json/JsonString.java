package cc.tantibus.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 0da - 30.01.2018.
 */
public class JsonString implements JsonStruct {
    private final String value;

    JsonString(String value) {
        this.value = value;
    }

    @Override
    public Byte asByte() {
        try {
            return Byte.parseByte(value, 10);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Short asShort() {
        try {
            return Short.parseShort(value, 10);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Integer asInt() {
        try {
            return Integer.parseInt(value, 10);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Long asLong() {
        try {
            return Long.parseLong(value, 10);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public Float asFloat() {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0f;
        }
    }

    @Override
    public Double asDouble() {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0d;
        }
    }

    @Override
    public BigInteger asBigInteger() {
        try {
            return new BigInteger(value, 10);
        } catch (Exception e) {
            return BigInteger.ZERO;
        }
    }

    @Override
    public BigDecimal asBigDecimal() {
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return value;
    }


}
