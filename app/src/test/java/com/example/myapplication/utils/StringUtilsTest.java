package com.example.myapplication.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void isEmptyOrNull_nullString_returnsTrue() {
        assertTrue(StringUtils.isEmptyOrNull(null));
    }

    @Test
    public void isEmptyOrNull_emptyString_returnsTrue() {
        assertTrue(StringUtils.isEmptyOrNull(""));
    }

    @Test
    public void isEmptyOrNull_stringWithContent_returnsFalse() {
        assertFalse(StringUtils.isEmptyOrNull("hello"));
    }

    @Test
    public void isEmptyOrNull_stringWithWhitespace_returnsFalse() {
        // This depends on the desired behavior. Standard isEmpty() treats whitespace as content.
        // If whitespace should be considered empty, the StringUtils.isEmptyOrNull method needs to be updated (e.g., with str.trim().isEmpty()).
        // For now, assuming standard behavior where whitespace is not empty.
        assertFalse(StringUtils.isEmptyOrNull("   "));
    }
}
