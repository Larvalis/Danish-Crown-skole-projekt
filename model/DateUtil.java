package model;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The DU class contains utility methods to be used with Date objects
 * representing a date without time values (as in 2010-12-14).<br />
 * Note: Methods in this class ignore the Date objects' values of hour, minute,
 * second and millisecond.<br />
 * Note: Methods creating new Date objects requires the year in [1000..9999]. <br />
 * Note: Leap year and length of months use the gregorian calendar rules.
 * 
 * @author mlch, June 2011
 * 
 */
public abstract class DateUtil {
	/**
	 * Returns true, if the year is a leap year.<br />
	 * 
	 * @throws IllegalArgumentException
	 *             if <i>year</int> <= 0.
	 */
	public static boolean isLeapYear(int year) {
		if (year <= 0)
			throw new IllegalArgumentException("Year must be positive.");
		return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
	}

	/**
	 * Returns the length of the month in the year.
	 * 
	 * @throws IllegalArgumentException
	 *             if <i>month</i> not in [1..12] or <i>year</i> <= 0.
	 */
	public static int monthLength(int month, int year) {
		if (month < 1 || month > 12)
			throw new IllegalArgumentException("Month out of bounds");
		if (year <= 0)
			throw new IllegalArgumentException("Year must be positive.");

		int length = 31;
		if (month == 4 || month == 6 || month == 9 || month == 11)
			length = 30;
		else if (month == 2) {
			if (DateUtil.isLeapYear(year))
				length = 29;
			else
				length = 28;
		}
		return length;
	}

	/**
	 * Returns a new Date object parsed from the date.<br />
	 * Note: Format of <i>date</i> must be "yyyy-mm-dd" as in "2010-12-14".<br />
	 * 
	 * @throws IllegalArgumentException
	 *             if <i>date</i> is not a legal date with year in [1000..9999].
	 */
	public static Date createDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		ParsePosition pp = new ParsePosition(0);
		Date d = sdf.parse(date, pp);

		if (d == null || pp.getIndex() < date.length())
			throw new IllegalArgumentException("Error when parsing: " + date);

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		if (gc.get(Calendar.YEAR) < 1000 || gc.get(Calendar.YEAR) > 9999)
			throw new IllegalArgumentException("Year out of bounds");

		return d;
	}

	/**
	 * Returns a new Date object set to the given values.<br />
	 * 
	 * @throws IllegalArgumentException
	 *             if <i>year</i> not in [1000..9999], or <i>month</i> not in
	 *             [1..12], or <i>day</i> not in [1..DU.monthLength(month,
	 *             year)].
	 */
	public static Date createDate(int year, int month, int day) {
		if (year < 1000 || year > 9999)
			throw new IllegalArgumentException("Year out of bounds");
		if (month < 1 || month > 12)
			throw new IllegalArgumentException("Month out of bounds");
		if (day < 1 || day > DateUtil.monthLength(month, year))
			throw new IllegalArgumentException("Day out of bounds");

		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		return gc.getTime();
	}

	/**
	 * Returns a new Date object set to the date plus the days.<br />
	 * Note: <i>days</i> may be negative.
	 * 
	 * @throws IllegalArgumentException
	 *             if the resulting year is not in [1000..9999].
	 */
	public static Date createDate(Date date, int days) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_MONTH, days);

		if (gc.get(Calendar.YEAR) < 1000 || gc.get(Calendar.YEAR) > 9999)
			throw new IllegalArgumentException("Year out of bounds");

		return gc.getTime();
	}

	/**
	 * Returns a new GregorianCalender object representing the date's value with
	 * time of day set to 12:00:00. Note: Private helper method.
	 */
	private static GregorianCalendar createNormGregCal(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.MILLISECOND, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.HOUR_OF_DAY, 12);
		return gc;
	}

	/**
	 * Returns the date's year.
	 */
	public int getYear(Date date) {
		GregorianCalendar gc = DateUtil.createNormGregCal(date);
		return gc.get(Calendar.YEAR);
	}

	/**
	 * Returns the date's month.
	 */
	public int getMonth(Date date) {
		GregorianCalendar gc = DateUtil.createNormGregCal(date);
		return gc.get(Calendar.MONTH);
	}

	/**
	 * Returns the date's day of the month.
	 */
	public int getDay(Date date) {
		GregorianCalendar gc = DateUtil.createNormGregCal(date);
		return gc.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Returns how many integer days the start date is before the end date
	 * (negative if the start date is after the end date). Note: This method
	 * ignores time values of the dates.
	 */
	public static int daysDiff(Date startDate, Date endDate) {
		GregorianCalendar sd = DateUtil.createNormGregCal(startDate);
		GregorianCalendar ed = DateUtil.createNormGregCal(endDate);
		long millisPer24hours = 24 * 3600 * 1000;
		long millis = ed.getTimeInMillis() - sd.getTimeInMillis();
		if (millis >= 0)
			millis += 3600;
		else
			millis -= 3600;
		return (int) (millis / millisPer24hours);
	}

	/**
	 * Returns how many integer years the start date is before the end date
	 * (negative if the start date is after the end date). Note: This method
	 * ignores time values of the dates.
	 */
	public static int yearsDiff(Date startDate, Date endDate) {
		GregorianCalendar sd = DateUtil.createNormGregCal(startDate);
		GregorianCalendar ed = DateUtil.createNormGregCal(endDate);
		int sdMonth = sd.get(Calendar.MONTH) + 1;
		int edMonth = ed.get(Calendar.MONTH) + 1;
		int sdDay = sd.get(Calendar.DAY_OF_MONTH);
		int edDay = ed.get(Calendar.DAY_OF_MONTH);

		int years = ed.get(Calendar.YEAR) - sd.get(Calendar.YEAR);
		if (sd.before(ed)) {
			if (sdMonth > edMonth || (sdMonth == edMonth && sdDay > edDay))
				years--;
		} else {
			if (edMonth > sdMonth || (edMonth == sdMonth && edDay > sdDay))
				years++;
		}
		return years;
	}

	/**
	 * Returns a Date object parsed from the date string.<br />
	 * Note: Format of <i>date</i> must be "yyyyMMddHHmmss" as in
	 * "20101214154534".<br />
	 * 
	 * @throws IllegalArgumentException
	 *             if <i>date</i> is not a legal date with year in [1000..9999].
	 */
	public static Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setLenient(false);
		ParsePosition pp = new ParsePosition(0);
		Date d = sdf.parse(date, pp);

		if (d == null || pp.getIndex() < date.length())
			throw new IllegalArgumentException("Error when parsing: " + date);

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		if (gc.get(Calendar.YEAR) < 1000 || gc.get(Calendar.YEAR) > 9999)
			throw new IllegalArgumentException("Year out of bounds");

		return d;
	}

	/**
	 * Returns a string representing the date.<br />
	 * Note: Format of the returned string is "yyyyMMddHHmmss" as in
	 * "20101214154534".
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * Adds <i>minutes</i> minutes to a Date and returns the new Date.<br />
	 */
	public static Date addMinutesToDate(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * Returns how many integer mins the start date is before the end date
	 * (negative if the start date is after the end date).
	 */
	public static int minsDiff(Date startDate, Date endDate) {
		long millisPer1min = 60 * 1000;
		long millis = endDate.getTime() - startDate.getTime();
		return (int) (millis / millisPer1min);
	}

	/**
	 * Returnerer et tidspunkt i et brugervenligt format: "HH:mm" Krav: input
	 * skal være i format: "yyyyMMddHHmmss". ex ("20101214154534")
	 */
	public static String formatTimestamp(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/**
	 * Returnerer et tidspunkt i et brugervenligt format: "HH:mm"
	 */
	public static String formatTimestamp(String date) {
		return formatTimestamp(stringToDate(date));
	}

	/**
	 * Returnerer en dato i et brugervenligt format: "dd/MM-yy"
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM-yy");
		return sdf.format(date);
	}

	/**
	 * Returnerer en dato i et brugervenligt format: "dd/MM-yy" Krav: input skal
	 * være i format: "yyyyMMddHHmmss". ex ("20101214154534")
	 */
	public static String formatDate(String date) {
		return formatDate(stringToDate(date));
	}

	/**
	 * Behandler en date String og giver den tilbage i et brugervenligt format
	 * Krav: input skal være i format: "yyyyMMddHHmmss". ex ("20101214154534")
	 */
	public static String getSmartDate(String date) {
		if (date == null || date.length() != 14)
			date = "**************";
		String dag = date.substring(6, 8);
		String måned = date.substring(4, 6);
		String år = date.substring(2, 4);
		String time = date.substring(8, 10);
		String min = date.substring(10, 12);
		return (dag + "/" + måned + "-" + år + " " + time + ":" + min);
	}
}