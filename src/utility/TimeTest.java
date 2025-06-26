package utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TimeTest {
	
	@Test
	void validDate() {
		assertFalse(Time.isValidDate("ergje"));
		assertFalse(Time.isValidDate("01/05/2025"));
		assertFalse(Time.isValidDate("08 Marzo 2025"));
		assertTrue(Time.isValidDate("01-05-2025"));
		assertTrue(Time.isValidDate("15-08-2023"));
		assertTrue(Time.isValidDate("29-02-2024"));
		assertTrue(Time.isValidDate("01-01-2000"));
		assertTrue(Time.isValidDate("31-12-1999"));
		assertTrue(Time.isValidDate("10-10-2010"));
		assertTrue(Time.isValidDate("28-02-2021"));
		assertTrue(Time.isValidDate("30-04-2022"));
		assertTrue(Time.isValidDate("31-07-2019"));
		assertTrue(Time.isValidDate("25-12-2025"));
		assertTrue(Time.isValidDate("09-09-1995"));
		assertFalse(Time.isValidDate("32-01-2023"));
		assertFalse(Time.isValidDate("30-02-2023"));
		assertFalse(Time.isValidDate("31-04-2022"));
		assertFalse(Time.isValidDate("29-02-2023"));
		assertFalse(Time.isValidDate("00-06-2021"));
		assertFalse(Time.isValidDate("15-13-2022"));
		assertFalse(Time.isValidDate("31-06-2018"));
		assertFalse(Time.isValidDate("12-00-2020"));
		assertTrue(Time.isValidDate("25-12-1899"));
		assertFalse(Time.isValidDate("29-02-2100"));
	}
	
	@Test
	void comesBeforeCheck() {
		assertFalse(Time.comesBefore("08-09-2025", "08-08-2025"));
		assertTrue(Time.comesBefore("08-08-2025", "08-09-2025"));
		assertTrue(Time.comesBefore("08-08-1998", "08-09-2025"));
		assertFalse(Time.comesBefore("31-09-2025", "08-09-2025"));
	    // Date valide
	    assertTrue(Time.comesBefore("01-01-2020", "02-01-2020"));
	    assertFalse(Time.comesBefore("31-12-2025", "01-01-2025"));
	    assertTrue(Time.comesBefore("15-06-2010", "20-06-2010"));
	    assertFalse(Time.comesBefore("10-10-2010", "10-10-2010"));
	    assertTrue(Time.comesBefore("01-01-1990", "01-01-2000"));
	    assertFalse(Time.comesBefore("01-01-2050", "01-01-2049"));
	    assertTrue(Time.comesBefore("28-02-2024", "29-02-2024")); // Anno bisestile
	    assertFalse(Time.comesBefore("29-02-2024", "28-02-2024")); // Inversione
	    assertTrue(Time.comesBefore("30-04-2023", "01-05-2023"));
	    assertFalse(Time.comesBefore("31-07-2022", "01-07-2022"));
	    
	    // Date con mesi fuori range
	    assertFalse(Time.comesBefore("10-13-2025", "08-09-2025"));
	    assertFalse(Time.comesBefore("15-00-2025", "15-01-2025"));
	    
	    // Date con giorni fuori range
	    assertFalse(Time.comesBefore("32-01-2025", "01-02-2025"));
	    assertFalse(Time.comesBefore("00-06-2025", "01-06-2025"));
	    assertFalse(Time.comesBefore("31-04-2025", "01-05-2025"));
	    assertFalse(Time.comesBefore("29-02-2023", "01-03-2023")); // Non bisestile

	    // Confronto tra anni
	    assertTrue(Time.comesBefore("01-01-1999", "01-01-2000"));
	    assertFalse(Time.comesBefore("01-01-2100", "01-01-2099"));
	    
	    // Confronti tra stessi mesi/giorni
	    assertTrue(Time.comesBefore("07-07-2007", "08-07-2007"));
	    assertFalse(Time.comesBefore("08-07-2007", "07-07-2007"));
	}
	
	@Test
	void testGetAvailabilityWindow() {
	    // Case 1: Availability window falls entirely within open-close range
	    String[] result = Time.getAvailabilityWindow("01-06-2025", "30-09-2025", new int[]{7, 2025});
	    assertArrayEquals(new String[]{"01-07-2025", "31-07-2025"}, result);

	    // Case 2: Desired month is before the open date -> should return null
	    result = Time.getAvailabilityWindow("01-06-2025", "30-09-2025", new int[]{5, 2025});
	    assertNull(result);

	    // Case 3: Desired month is after the close date -> should return null
	    result = Time.getAvailabilityWindow("01-06-2025", "30-09-2025", new int[]{10, 2025});
	    assertNull(result);

	    // Case 5: Desired month is the same as close date -> end date should be the close date
	    result = Time.getAvailabilityWindow("01-06-2025", "20-07-2025", new int[]{7, 2025});
	    assertArrayEquals(new String[]{"01-07-2025", "20-07-2025"}, result);

	    // Case 6: Open and close dates span an entire year
	    result = Time.getAvailabilityWindow("01-01-2025", "31-12-2025", new int[]{3, 2025});
	    assertArrayEquals(new String[]{"01-03-2025", "31-03-2025"}, result);

	    // Case 7: Open date is exactly at the start of the desired month
	    result = Time.getAvailabilityWindow("01-05-2025", "31-12-2025", new int[]{5, 2025});
	    assertArrayEquals(new String[]{"01-05-2025", "31-05-2025"}, result);

	    // Case 8: Close date is exactly at the end of the desired month
	    result = Time.getAvailabilityWindow("01-01-2025", "30-09-2025", new int[]{9, 2025});
	    assertArrayEquals(new String[]{"01-09-2025", "30-09-2025"}, result);

	    // Case 9: Close date is in the middle of the desired month
	    result = Time.getAvailabilityWindow("01-01-2025", "15-09-2025", new int[]{9, 2025});
	    assertArrayEquals(new String[]{"01-09-2025", "15-09-2025"}, result);

	    // Case 11: Open date and close date are in different years
	    result = Time.getAvailabilityWindow("01-06-2024", "30-06-2026", new int[]{12, 2025});
	    assertArrayEquals(new String[]{"01-12-2025", "31-12-2025"}, result);

	    // Case 12: Close date is in February of a leap year
	    result = Time.getAvailabilityWindow("01-01-2024", "29-02-2024", new int[]{2, 2024});
	    assertArrayEquals(new String[]{"01-02-2024", "29-02-2024"}, result);

	    // Case 13: Close date is in February of a non-leap year
	    result = Time.getAvailabilityWindow("01-01-2023", "28-02-2023", new int[]{2, 2023});
	    assertArrayEquals(new String[]{"01-02-2023", "28-02-2023"}, result);

	    // Case 14: Desired month is in a different year than open and close
	    result = Time.getAvailabilityWindow("01-06-2024", "30-06-2026", new int[]{3, 2023});
	    assertNull(result);

	    // Case 15: Open and close cover an entire decade
	    result = Time.getAvailabilityWindow("01-01-2020", "31-12-2029", new int[]{5, 2025});
	    assertArrayEquals(new String[]{"01-05-2025", "31-05-2025"}, result);

	    // Case 17: Desired month is at the exact beginning of the range
	    result = Time.getAvailabilityWindow("01-03-2025", "31-12-2025", new int[]{3, 2025});
	    assertArrayEquals(new String[]{"01-03-2025", "31-03-2025"}, result);

	    // Case 18: Desired month is at the exact end of the range
	    result = Time.getAvailabilityWindow("01-03-2025", "31-10-2025", new int[]{10, 2025});
	    assertArrayEquals(new String[]{"01-10-2025", "31-10-2025"}, result);

	    // Case 19: Desired month starts within range but ends outside it
	    result = Time.getAvailabilityWindow("01-06-2025", "15-09-2025", new int[]{9, 2025});
	    assertArrayEquals(new String[]{"01-09-2025", "15-09-2025"}, result);

	    // Case 20: Open date is exactly at the start of the desired year
	    result = Time.getAvailabilityWindow("01-01-2025", "31-12-2025", new int[]{1, 2025});
	    assertArrayEquals(new String[]{"01-01-2025", "31-01-2025"}, result);
	}
	
	@Test
	void testTimeWithinRange() {
		assertTrue(Time.isTimeBetween("12:30", "10:00", "14:00"));
		assertTrue(Time.isTimeBetween("23:30", "22:00", "23:59"));
		assertTrue(Time.isTimeBetween("00:30", "23:00", "02:00"));
	}

	@Test
	void testTimeOutsideRange() {
		assertFalse(Time.isTimeBetween("09:30", "10:00", "14:00"));
		assertFalse(Time.isTimeBetween("14:30", "10:00", "14:00"));
		assertFalse(Time.isTimeBetween("03:00", "23:00", "02:00"));
	}

	@Test
	void testTimeAtBoundary() {
		assertTrue(Time.isTimeBetween("10:00", "10:00", "14:00"));
		assertTrue(Time.isTimeBetween("14:00", "10:00", "14:00"));
		assertTrue(Time.isTimeBetween("23:00", "23:00", "02:00"));
		assertTrue(Time.isTimeBetween("02:00", "23:00", "02:00"));
	}

	@Test
	void testMidnightCrossing() {
		assertTrue(Time.isTimeBetween("01:00", "23:00", "02:00"));
		assertFalse(Time.isTimeBetween("03:00", "23:00", "02:00"));
		assertTrue(Time.isTimeBetween("23:30", "23:00", "02:00"));
	}
	
	
	 @Test
	    void testExactlyThreeDaysBefore() {
	        assertTrue(Time.isThreeDaysOrLessBefore("28-03-2025", "31-03-2025"));
	    }

	    @Test
	    void testExactlyTwoDaysBefore() {
	        assertTrue(Time.isThreeDaysOrLessBefore("29-03-2025", "31-03-2025"));
	    }

	    @Test
	    void testExactlyOneDayBefore() {
	        assertTrue(Time.isThreeDaysOrLessBefore("30-03-2025", "31-03-2025"));
	    }

	    @Test
	    void testSameDay() {
	        assertTrue(Time.isThreeDaysOrLessBefore("31-03-2025", "31-03-2025"));
	    }

	    @Test
	    void testMoreThanThreeDaysBefore() {
	        assertFalse(Time.isThreeDaysOrLessBefore("27-03-2025", "31-03-2025"));
	    }

	    @Test
	    void testDateAfter() {
	        assertFalse(Time.isThreeDaysOrLessBefore("01-04-2025", "31-03-2025"));
	    }

	    @Test
	    void testInvalidDate1() {
	        assertFalse(Time.isThreeDaysOrLessBefore("invalid-date", "31-03-2025"));
	    }

	    @Test
	    void testInvalidDate2() {
	        assertFalse(Time.isThreeDaysOrLessBefore("31-03-2025", "invalid-date"));
	    }

	    @Test
	    void testBothInvalidDates() {
	        assertFalse(Time.isThreeDaysOrLessBefore("invalid-date1", "invalid-date2"));
	    }
	    
	    @Test
	    void testLeapYears() {
	        assertTrue(Time.isLeapYear(2000), "Year 2000 should be a leap year");
	        assertTrue(Time.isLeapYear(2024), "Year 2024 should be a leap year");
	        assertTrue(Time.isLeapYear(2400), "Year 2400 should be a leap year");
	    }

	    @Test
	    void testNonLeapYears() {
	        assertFalse(Time.isLeapYear(1900), "Year 1900 should NOT be a leap year");
	        assertFalse(Time.isLeapYear(2023), "Year 2023 should NOT be a leap year");
	        assertFalse(Time.isLeapYear(2100), "Year 2100 should NOT be a leap year");
	    }

	    @Test
	    void testEdgeCases() {
	        assertTrue(Time.isLeapYear(4), "Year 4 should be a leap year");
	        assertFalse(Time.isLeapYear(1), "Year 1 should NOT be a leap year");
	        assertTrue(Time.isLeapYear(400), "Year 400 should be a leap year");
	    }
}
