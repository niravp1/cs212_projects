/**
 * Represents a date with year, month, and day components.
 * Dates are created from strings in YYYYMMDD format.
 * Provides comparison functionality for sorting dates chronologically.
 * 
 * @author Nirav Persaud
 * @version 1.0
 */
public class Date212 implements Comparable<Date212> {
	/** The year component of the date */
	private int year;
	
	/** The month component of the date (1-12) */
	private int month;
	
	/** The day component of the date (1-31) */
	private int day;
	
	/** Days in each month (non-leap year) */
	private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/**
	 * Constructs a Date212 object from a string in YYYYMMDD format.
	 * Parses the string to extract year, month, and day values.
	 * Throws IllegalDate212Exception if the date string is invalid.
	 * 
	 * @param a a string representing a date in YYYYMMDD format (e.g., "20211117")
	 * @throws IllegalDate212Exception if the date string is not 8 digits or contains invalid values
	 */
	public Date212(String a) {
		// Check if string is exactly 8 digits
		if (a == null || a.length() != 8) {
			throw new IllegalDate212Exception("Invalid date format: " + a + " (must be 8 digits)");
		}
		
		// Check if all characters are digits
		for (int i = 0; i < a.length(); i++) {
			if (!Character.isDigit(a.charAt(i))) {
				throw new IllegalDate212Exception("Invalid date format: " + a + " (must contain only digits)");
			}
		}
		
		int y = Integer.parseInt(a.substring(0, 4));
		int m = Integer.parseInt(a.substring(4, 6));
		int d = Integer.parseInt(a.substring(6, 8));
		
		// Validate month (1-12)
		if (m < 1 || m > 12) {
			throw new IllegalDate212Exception("Invalid month: " + m + " in date " + a + " (must be 1-12)");
		}
		
		// Validate day for the given month
		int maxDay = DAYS_IN_MONTH[m];
		
		// Adjust for leap year in February
		if (m == 2 && isLeapYear(y)) {
			maxDay = 29;
		}
		
		if (d < 1 || d > maxDay) {
			throw new IllegalDate212Exception("Invalid day: " + d + " for month " + m + " in date " + a + " (must be 1-" + maxDay + ")");
		}
		
		year = y;
		month = m;
		day = d;
	}
	
	/**
	 * Checks if a year is a leap year.
	 * 
	 * @param year the year to check
	 * @return true if the year is a leap year, false otherwise
	 */
	private boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * Returns the year component of this date.
	 * 
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Returns the month component of this date.
	 * 
	 * @return the month (1-12)
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Returns the day component of this date.
	 * 
	 * @return the day (1-31)
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Sets the year component of this date.
	 * 
	 * @param y the new year value
	 */
	public void setYear(int y) {
		year = y;
	}
	
	/**
	 * Sets the month component of this date.
	 * 
	 * @param m the new month value (1-12)
	 */
	public void setMonth(int m) {
		month = m;
	}
	
	/**
	 * Sets the day component of this date.
	 * 
	 * @param d the new day value (1-31)
	 */
	public void setDay(int d) {
		day = d;
	}
	
	/**
	 * Returns the name of the month for a given month number.
	 * 
	 * @param month the month number (1-12)
	 * @return the name of the month (e.g., "January", "February")
	 */
	public static String monthName(int month) {
		String[] monthName = {"", "January", "February", "March", "April",
							"May", "June", "July", "August", "September",
							"October", "November", "December"};
		return monthName[month];
	}
	
	/**
	 * Calculates the day of the week using Zeller's Congruence algorithm.
	 * Zeller's Congruence is an algorithm to calculate the day of the week
	 * for any Gregorian calendar date.
	 * 
	 * @return the day of the week as a number (0 = Saturday, 1 = Sunday, ..., 6 = Friday)
	 */
	private int getDayOfWeek() {
		int q = day;
		int m = month;
		int y = year;
		
		// January and February are treated as months 13 and 14 of the previous year
		if (m == 1 || m == 2) {
			m += 12;
			y--;
		}
		
		int k = y % 100;  // Year of the century
		int j = y / 100;  // Zero-based century
		
		// Zeller's Congruence formula for Gregorian calendar
		int h = (q + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 + 5 * j) % 7;
		
		return h;
	}
	
	/**
	 * Returns the name of the day of the week for a given day number.
	 * 
	 * @param dayNum the day number (0 = Saturday, 1 = Sunday, ..., 6 = Friday)
	 * @return the name of the day (e.g., "Monday", "Tuesday")
	 */
	private static String dayName(int dayNum) {
		String[] dayNames = {"Saturday", "Sunday", "Monday", "Tuesday", 
							 "Wednesday", "Thursday", "Friday"};
		return dayNames[dayNum];
	}
	
	/**
	 * Returns a string representation of this date in "Day, Month Day, Year" format.
	 * 
	 * @return a formatted string (e.g., "Friday, December 5, 2025")
	 */
	public String toString() {
		return dayName(getDayOfWeek()) + ", " + monthName(month) + " " + day + ", " + year;
	}
	
	/**
	 * Compares this date to another date chronologically.
	 * Returns a negative integer if this date is earlier,
	 * zero if the dates are equal, or a positive integer if this date is later.
	 * 
	 * @param other the Date212 object to compare to
	 * @return -1 if this date is earlier, 0 if equal, 1 if this date is later
	 */
	public int compareTo(Date212 other) {
		if (this.year < other.year) {
			return -1;
		}
		if (this.year > other.year) {
			return 1;
		}
		if (this.year == other.year && this.month < other.month) {
			return -1;
		}
		if (this.year == other.year && this.month > other.month) {
			return 1;
		}
		if (this.year == other.year && this.month == other.month && this.day < other.day) {
			return -1;
		}
		if (this.year == other.year && this.month == other.month && this.day > other.day) {
			return 1;
		}
		if (this.year == other.year && this.month == other.month && this.day == other.day) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Checks if this date is equal to another object.
	 * 
	 * @param obj the object to compare to
	 * @return true if the dates are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Date212 other = (Date212) obj;
		return year == other.year && month == other.month && day == other.day;
	}
	
	/**
	 * Returns a hash code for this date.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return year * 10000 + month * 100 + day;
	}
}