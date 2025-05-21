package com.example.myapplication.utils; // Adjust package if necessary

import android.util.Patterns;
import java.util.regex.Pattern;

public class ValidatorUtils {

    // Basic phone number pattern: allows for digits, spaces, hyphens, parentheses, and plus sign.
    // Requires at least 7 digits, up to 15.
    // This is a simplified pattern and might need adjustment for specific international numbers.
    private static final Pattern BASIC_PHONE_PATTERN = Pattern.compile("^[+]?[0-9\s-()]{7,15}$");


    /**
     * Checks if the given string is a valid email address.
     *
     * @param email The string to validate.
     * @return true if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Checks if the given string is not empty after trimming whitespace.
     * This is similar to !StringUtils.isEmptyOrNull but specifically includes trimming.
     *
     * @param text The string to check.
     * @return true if the string is not null, not empty, and not just whitespace.
     */
    public static boolean isNotEmptyAfterTrim(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Checks if the given string could be a valid phone number.
     * This is a basic check for length and common characters.
     * For more robust validation, consider using Google's libphonenumber.
     *
     * @param phoneNumber The string to validate.
     * @return true if the string appears to be a phone number, false otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        // First, remove common visual separators to count actual digits for length check
        String digitsOnly = phoneNumber.replaceAll("[\s-()+]", "");
        if (digitsOnly.length() < 7 || digitsOnly.length() > 15) { // Arbitrary min/max length for digits
            return false;
        }
        // Then, validate the original string format with more allowed characters
        return BASIC_PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
    }

    /**
     * Checks if a password meets basic complexity requirements.
     * Example requirements:
     * - At least 8 characters long
     * - Contains at least one digit
     * - Contains at least one lowercase letter
     * - Contains at least one uppercase letter
     * - Contains at least one special character (e.g., @#$%^&+=)
     *
     * @param password The password string to validate.
     * @return true if the password meets the criteria, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;
        
        String specialChars = "@#$%^&+="; // Define your set of special characters

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (specialChars.indexOf(c) != -1) {
                hasSpecial = true;
            }
        }
        return hasDigit && hasLower && hasUpper && hasSpecial;
    }
}
