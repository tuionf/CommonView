package com.example.myapplication.utils; // Adjust package if necessary

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtilsTest {

    @Test
    public void formatDate_nullDate_returnsNull() {
        assertNull(DateTimeUtils.formatDate(null, DateTimeUtils.DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void formatDate_nullPattern_returnsNull() {
        assertNull(DateTimeUtils.formatDate(new Date(), null, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void formatDate_validDateAndPatternUTC_returnsFormattedString() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(2023, Calendar.JANUARY, 15, 10, 30, 45);
        calendar.set(Calendar.MILLISECOND, 0); // Clear milliseconds for exact match
        Date date = calendar.getTime();

        String expected = "2023-01-15 10:30:45";
        assertEquals(expected, DateTimeUtils.formatDate(date, DateTimeUtils.DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC")));
    }
    
    @Test
    public void formatDateTimeUTC_validDate_returnsFormattedString() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(2023, Calendar.JANUARY, 15, 10, 30, 45);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        String expected = "2023-01-15 10:30:45";
        assertEquals(expected, DateTimeUtils.formatDateTimeUTC(date));
    }

    @Test
    public void formatDate_validDateAndPatternSpecificTZ_returnsFormattedString() {
        // Test with a specific timezone, e.g., EST (UTC-5)
        TimeZone est = TimeZone.getTimeZone("America/New_York");
        Calendar calendar = Calendar.getInstance(est, Locale.US);
        calendar.set(2023, Calendar.JANUARY, 15, 10, 30, 45); // 10:30:45 EST
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime(); // This date object is a point in time, its representation depends on formatter's TZ

        // Format it back in EST
        String expectedEST = "2023-01-15 10:30:45";
        assertEquals(expectedEST, DateTimeUtils.formatDate(date, DateTimeUtils.DEFAULT_DATETIME_FORMAT, est));

        // Format the same date in UTC. 10:30:45 EST should be 15:30:45 UTC
        String expectedUTC = "2023-01-15 15:30:45";
        assertEquals(expectedUTC, DateTimeUtils.formatDate(date, DateTimeUtils.DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC")));
    }
    
    @Test
    public void formatDateTimeDefaultTZ_validDate_usesDefaultTimeZone() {
        Date date = new Date();
        // We can't easily assert the exact string due to varying default TZs in test environments.
        // But we can check it's not null and has the correct format structure.
        String formattedDate = DateTimeUtils.formatDateTimeDefaultTZ(date);
        assertNotNull(formattedDate);
        // Example: "2023-10-27 10:00:00" - length 19, contains "-", " ", ":"
        assertEquals(19, formattedDate.length());
        assertTrue(formattedDate.contains("-") && formattedDate.contains(" ") && formattedDate.contains(":"));
    }


    @Test
    public void parseDate_nullDateString_returnsNull() {
        assertNull(DateTimeUtils.parseDate(null, DateTimeUtils.DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void parseDate_nullPattern_returnsNull() {
        assertNull(DateTimeUtils.parseDate("2023-01-15 10:30:45", null, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void parseDate_invalidDateString_returnsNull() {
        assertNull(DateTimeUtils.parseDate("invalid-date", DateTimeUtils.DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void parseDate_mismatchedPattern_returnsNull() {
        assertNull(DateTimeUtils.parseDate("2023/01/15 10:30:45", DateTimeUtils.DEFAULT_DATETIME_FORMAT, TimeZone.getTimeZone("UTC")));
    }

    @Test
    public void parseDateTimeUTC_validString_returnsDate() {
        String dateString = "2023-01-15 10:30:45";
        Date parsedDate = DateTimeUtils.parseDateTimeUTC(dateString);
        assertNotNull(parsedDate);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.setTime(parsedDate);

        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(10, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, calendar.get(Calendar.MINUTE));
        assertEquals(45, calendar.get(Calendar.SECOND));
    }
    
    @Test
    public void parseDateTimeDefaultTZ_validString_returnsDateInDefaultTZ() {
        // Create a date string that represents a certain time in the default TZ
        TimeZone defaultTZ = TimeZone.getDefault();
        Calendar testCal = Calendar.getInstance(defaultTZ, Locale.US);
        testCal.set(2024, Calendar.MARCH, 10, 14, 20, 50);
        testCal.set(Calendar.MILLISECOND, 0);
        
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT, Locale.US);
        sdf.setTimeZone(defaultTZ);
        String dateStringInDefaultTZ = sdf.format(testCal.getTime());

        Date parsedDate = DateTimeUtils.parseDateTimeDefaultTZ(dateStringInDefaultTZ);
        assertNotNull(parsedDate);
        
        // The parsedDate object represents a specific instant.
        // Comparing its fields requires formatting it back or comparing its 'time' value.
        assertEquals(testCal.getTimeInMillis(), parsedDate.getTime());
    }

    @Test
    public void parseDate_stringInSpecificTZ_returnsCorrectDate() {
        String dateStringEST = "2023-01-15 10:30:45"; // Assume this is EST
        TimeZone est = TimeZone.getTimeZone("America/New_York");
        Date parsedDate = DateTimeUtils.parseDate(dateStringEST, DateTimeUtils.DEFAULT_DATETIME_FORMAT, est);
        assertNotNull(parsedDate);

        // To verify, format this date back to UTC and check the time shift
        // 10:30:45 EST on Jan 15 2023 is 15:30:45 UTC on Jan 15 2023
        SimpleDateFormat utcFormatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT, Locale.US);
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedInUTC = utcFormatter.format(parsedDate);

        assertEquals("2023-01-15 15:30:45", formattedInUTC);
    }
}
```
