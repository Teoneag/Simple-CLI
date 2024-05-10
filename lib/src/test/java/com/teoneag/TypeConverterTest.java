package com.teoneag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TypeConverterTest {
    @Test
    public void testTypeMapInitialization() {
        assertTrue(TypeConverter.isSupported(int.class));
        assertTrue(TypeConverter.isSupported(double.class));
        assertTrue(TypeConverter.isSupported(String.class));
        assertFalse(TypeConverter.isSupported(float.class));
    }

    @Test
    public void testConvertToInt() {
        assertEquals(123, TypeConverter.convert("123", int.class));
    }

    @Test
    public void testConvertToDouble() {
        assertEquals(123.456, TypeConverter.convert("123.456", double.class));
    }

    @Test
    public void testConvertToString() {
        assertEquals("test", TypeConverter.convert("test", String.class));
    }

    @Test
    public void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> TypeConverter.convert("test", int.class));
    }

    @Test
    public void testUnsupportedType() {
        assertThrows(IllegalArgumentException.class, () -> TypeConverter.convert("123", float.class));
    }
}