package com.mume12.happy.handlers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mume12.happy.R;
import com.mume12.happy.settings.ReminderAlarmReceiver;

public class ReminderHandler {

	private Context context;

	private static ReminderHandler uniqueInstance;
	
	private final int requestCode = 192837;
	
	private final long interval = 24 * 60 * 60 * 1000; // 1 day
//	private final long interval = 20 * 1000; // 20 sec for test

	private ReminderHandler(Context con) {
		this.context = con;
	}

	public static synchronized ReminderHandler getInstance(Context con) {
		if (uniqueInstance == null) {
			uniqueInstance = new ReminderHandler(con);
		}
		return uniqueInstance;
	}

//	public boolean isReminderSet() {
//		String settingsFileName = context.getString(R.string.settingFileName);
//		SharedPreferences settings = context.getSharedPreferences(
//				settingsFileName, 0);
//
//		boolean reminder = settings.getBoolean(
//				context.getString(R.string.reminder_saveSetReminder), false);
//		return reminder;
//	}

	// With sleephandler
	public void startReminder(FlowParametersHandler sleepHandler) {
		Intent intent = new Intent(context, ReminderAlarmReceiver.class);
		
		// Put extra booleans if sound should be played and if phone should vibrate
		intent.putExtra("PLAYSOUND", sleepHandler.playSound());
		intent.putExtra("VIBRATE", sleepHandler.vibrate());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// first cancel te be sure
		am.cancel(pendingIntent);
		
		// Get reminder hour from settings
		String settingsFileName = context.getString(R.string.settingFileName);
		SharedPreferences settings = context.getSharedPreferences(
				settingsFileName, 0);
		
		int min = settings.getInt(context.getString(R.string.reminder_saveSetReminderTimeMin), 0);
		int hour = settings.getInt(context.getString(R.string.reminder_saveSetReminderTimeHour),0);

		// TimeHandler creates reminder date
		long reminderDate = TimeCalculations.createReminderDate(hour, min);

		// Alarm is repeated every 24 hours
		am.setRepeating(AlarmManager.RTC_WAKEUP, reminderDate, interval,
				pendingIntent);
	}
	
	// Without sleephandler
	public void startReminder(int hour, int min, boolean playSound, boolean vibrate) {
		Intent intent = new Intent(context, ReminderAlarmReceiver.class);
		
		// Put extra booleans if sound should be played and if phone should vibrate
		intent.putExtra("PLAYSOUND", playSound);
		intent.putExtra("VIBRATE", vibrate);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// first cancel te be sure
		am.cancel(pendingIntent);

		// TimeHandler creates reminder date
		long reminderDate = TimeCalculations.createReminderDate(hour, min);

		// Alarm is repeated every 24 hours
		am.setRepeating(AlarmManager.RTC_WAKEUP, reminderDate, interval,
				pendingIntent);
	}

	public void stopReminder() {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, ReminderAlarmReceiver.class);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		am.cancel(pendingIntent);
	}
}
