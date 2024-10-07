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
    @SuppressWarnings("unchecked")
    public static <T> T convert(String value, Class<T> type) {
        if (value == null) throw new IllegalArgumentException("Value cannot be null for conversion");

        final TypeConverter converter = typeMap.get(type);
        if (converter == null) throw new IllegalArgumentException("Unsupported type: " + type);

        try {
            return (T) converter.converter.apply(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value for type " + type.getSimpleName() + ": " + value);
        }
    }
}
