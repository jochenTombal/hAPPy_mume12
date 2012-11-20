package com.mume12.happy.handlers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mume12.happy.storage.TimeStorage;

// Handles time calculations
public class TimeCalculations {

	// Calculate average time slept
	public static long averageSleep(List<TimeStorage> timeStorageList) {
		// Avoid division by zero
		if (!timeStorageList.isEmpty()) {
			long counter = 0;

			long totalSleep = 0;
			long average = 0;

			for (TimeStorage sl : timeStorageList) {
				totalSleep += (sl.getEndDate() - sl.getStartDate());
				counter++;
			}

			average = totalSleep / counter;

			return average;
		} else {
			return 0;
		}
	}

	public static String timeDifference(TimeStorage timeStorage) {
		long diff = timeStorage.getEndDate() - timeStorage.getStartDate();
		return convertMillis(diff);
	}

	// Convert millisec to nice string
	public static String convertMillis(long millis) {
		long secs = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
		long mins = (millis % (1000 * 60 * 60)) / (1000 * 60);
		long hours = millis / (1000 * 60 * 60);

		String converted = hours + ":" + mins + ":" + secs;

		return converted;
	}

	public static String timeDifference(long end, long begin) {
		long diff = end - begin;
		return convertMillis(diff);
	}

	// Calculate a reminder date and hour
	// if date is before current date (because of hour selected) then
	// an extra day is added
	// USED IN REMINDERSETTINGACTIVITY
	public static long createReminderDate(int hour, int min) {
		long oneDay = 24 * 60 * 60 * 1000;

		Calendar cal = Calendar.getInstance();

		Date reminderDate = new Date(cal.getTimeInMillis());
		reminderDate.setHours(hour);
		reminderDate.setMinutes(min);

		if (reminderDate.before(cal.getTime())) {
			Date newReminderDate = new Date(cal.getTimeInMillis() + oneDay);
			newReminderDate.setHours(hour);
			newReminderDate.setMinutes(min);

			return newReminderDate.getTime();
		} else {
			return reminderDate.getTime();
		}
	}
}
