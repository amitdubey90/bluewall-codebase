package com.bluewall.util.utility;

import java.util.concurrent.TimeUnit;

public class GenericUtil {

	public static java.sql.Date javaToSQLDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static String getHoursAndMinutesFromMillisecs(long millis) {

		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}
}
