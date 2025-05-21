package com.example.myapplication.utils;

public class StringUtils {

    /**
     * Checks if a string is null or empty.
     *
     * @param str The string to check.
     * @return true if the string is null or empty, false otherwise.
     */
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
