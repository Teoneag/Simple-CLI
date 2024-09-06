package com.teoneag;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

enum TypeConverter {
    INT(int.class, Integer::parseInt),
    DOUBLE(double.class, Double::parseDouble),
    STRING(String.class, value -> value);

    private final Class<?> type;
    private final Function<String, ?> converter;
    private static final Map<Class<?>, TypeConverter> typeMap = Arrays.stream(values())
        .collect(Collectors.toMap(converter -> converter.type, converter -> converter));

    TypeConverter(Class<?> type, Function<String, ?> converter) {
        this.type = type;
        this.converter = converter;
    }

    /**
     * Check if the type is supported
     *
     * @param type to check
     * @return true if the type is supported
     */
    public static boolean isSupported(Class<?> type) {
        return typeMap.containsKey(type);
    }

    /**
     * Convert a string to the given type
     *
     * @param value to convert
     * @param type  to convert to
     * @return the converted value
     */
    public static Object convert(String value, Class<?> type) {
        TypeConverter converter = typeMap.get(type);
        if (converter != null) {
            try {
                return converter.converter.apply(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value for type " + type.getSimpleName() + ": " + value);
            }
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
