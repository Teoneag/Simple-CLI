package com.teoneag;

import java.util.function.Function;

public enum TypeConverter {
    INT(int.class, Integer::parseInt),
    DOUBLE(double.class, Double::parseDouble),
    STRING(String.class, value -> value);

    private final Class<?> type;
    private final Function<String, ?> converter;

    TypeConverter(Class<?> type, Function<String, ?> converter) {
        this.type = type;
        this.converter = converter;
    }

    public static boolean isSupported(Class<?> type) {
        for (TypeConverter converter : values()) {
            if (converter.type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static Object convert(String value, Class<?> type) {
        for (TypeConverter converter : values()) {
            if (converter.type.equals(type)) {
                try {
                    return converter.converter.apply(value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid value for type " + type.getSimpleName() + ": " + value);
                }
            }
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
