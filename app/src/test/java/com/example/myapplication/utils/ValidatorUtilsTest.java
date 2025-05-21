package com.example.myapplication.utils; // Adjust package if necessary

import org.junit.Test;
import static org.junit.Assert.*;

// Import Patterns for mocking if needed, but Patterns.EMAIL_ADDRESS is a static final Pattern.
// For this test, we'll rely on the actual Patterns.EMAIL_ADDRESS.
// If android.util.Patterns is not available in standard JUnit, these tests might need Robolectric.
// Assuming it's available or will be run in an environment where it is (like Android JUnit tests).

public class ValidatorUtilsTest {

    @Test
    public void isValidEmail_nullEmail_returnsFalse() {
        assertFalse(ValidatorUtils.isValidEmail(null));
    }

    @Test
    public void isValidEmail_emptyEmail_returnsFalse() {
        assertFalse(ValidatorUtils.isValidEmail(""));
    }

    @Test
    public void isValidEmail_validEmailSimple_returnsTrue() {
        assertTrue(ValidatorUtils.isValidEmail("test@example.com"));
    }

    @Test
    public void isValidEmail_validEmailWithSubdomain_returnsTrue() {
        assertTrue(ValidatorUtils.isValidEmail("test@mail.example.com"));
    }

    @Test
    public void isValidEmail_validEmailWithPlusAlias_returnsTrue() {
        assertTrue(ValidatorUtils.isValidEmail("test+alias@example.com"));
    }

    @Test
    public void isValidEmail_invalidEmailNoAtSign_returnsFalse() {
        assertFalse(ValidatorUtils.isValidEmail("testexample.com"));
    }

    @Test
    public void isValidEmail_invalidEmailNoDomain_returnsFalse() {
        assertFalse(ValidatorUtils.isValidEmail("test@"));
    }

    @Test
    public void isValidEmail_invalidEmailNoUser_returnsFalse() {
        assertFalse(ValidatorUtils.isValidEmail("@example.com"));
    }
    
    @Test
    public void isValidEmail_invalidEmailEndsWithDot_returnsFalse() {
        assertFalse(ValidatorUtils.isValidEmail("test@example.com."));
    }

    @Test
    public void isNotEmptyAfterTrim_nullString_returnsFalse() {
        assertFalse(ValidatorUtils.isNotEmptyAfterTrim(null));
    }

    @Test
    public void isNotEmptyAfterTrim_emptyString_returnsFalse() {
        assertFalse(ValidatorUtils.isNotEmptyAfterTrim(""));
    }

    @Test
    public void isNotEmptyAfterTrim_whitespaceOnlyString_returnsFalse() {
        assertFalse(ValidatorUtils.isNotEmptyAfterTrim("   "));
    }

    @Test
    public void isNotEmptyAfterTrim_stringWithContent_returnsTrue() {
        assertTrue(ValidatorUtils.isNotEmptyAfterTrim("  hello  "));
    }

    @Test
    public void isValidPhoneNumber_nullNumber_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber(null));
    }

    @Test
    public void isValidPhoneNumber_emptyNumber_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber(""));
    }
    
    @Test
    public void isValidPhoneNumber_whitespaceNumber_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber("   "));
    }

    @Test
    public void isValidPhoneNumber_tooShort_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber("12345")); // Based on digit count
    }
    
    @Test
    public void isValidPhoneNumber_tooLong_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber("1234567890123456")); // Based on digit count
    }

    @Test
    public void isValidPhoneNumber_validSimpleNumber_returnsTrue() {
        assertTrue(ValidatorUtils.isValidPhoneNumber("1234567"));
        assertTrue(ValidatorUtils.isValidPhoneNumber("1234567890"));
    }

    @Test
    public void isValidPhoneNumber_validNumberWithSpaces_returnsTrue() {
        assertTrue(ValidatorUtils.isValidPhoneNumber("123 456 7890"));
    }

    @Test
    public void isValidPhoneNumber_validNumberWithHyphens_returnsTrue() {
        assertTrue(ValidatorUtils.isValidPhoneNumber("123-456-7890"));
    }

    @Test
    public void isValidPhoneNumber_validNumberWithParentheses_returnsTrue() {
        assertTrue(ValidatorUtils.isValidPhoneNumber("(123) 456-7890"));
    }

    @Test
    public void isValidPhoneNumber_validNumberWithPlus_returnsTrue() {
        assertTrue(ValidatorUtils.isValidPhoneNumber("+11234567890"));
        assertTrue(ValidatorUtils.isValidPhoneNumber("+44 123 456 7890"));
    }
    
    @Test
    public void isValidPhoneNumber_invalidChars_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber("123-456-7890abc"));
    }
    
    @Test
    public void isValidPhoneNumber_validNumberButTooManyDigitsAfterClean_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPhoneNumber("123 456 789 012 345 678")); // >15 digits
    }

    @Test
    public void isValidPassword_nullPassword_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword(null));
    }

    @Test
    public void isValidPassword_tooShort_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword("Pwd1@")); // 5 chars
    }

    @Test
    public void isValidPassword_validPassword_returnsTrue() {
        assertTrue(ValidatorUtils.isValidPassword("Password@1"));
        assertTrue(ValidatorUtils.isValidPassword("P@sswOrd123"));
    }

    @Test
    public void isValidPassword_missingDigit_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword("Password@"));
    }

    @Test
    public void isValidPassword_missingLowercase_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword("PASSWORD@1"));
    }

    @Test
    public void isValidPassword_missingUppercase_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword("password@1"));
    }

    @Test
    public void isValidPassword_missingSpecialChar_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword("Password123"));
    }
    
    @Test
    public void isValidPassword_meetsLengthButMissingOtherCriteria_returnsFalse() {
        assertFalse(ValidatorUtils.isValidPassword("longpassword")); // missing digit, upper, special
        assertFalse(ValidatorUtils.isValidPassword("LONGPASSWORD")); // missing digit, lower, special
        assertFalse(ValidatorUtils.isValidPassword("longpassword123")); // missing upper, special
        assertFalse(ValidatorUtils.isValidPassword("LONGPASSWORD123")); // missing lower, special
        assertFalse(ValidatorUtils.isValidPassword("LongPassword")); // missing digit, special
    }
}
```
