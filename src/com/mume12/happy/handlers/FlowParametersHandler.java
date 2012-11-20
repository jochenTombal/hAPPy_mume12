package com.mume12.happy.handlers;

import com.mume12.happy.R;
import android.content.Context;
import android.content.SharedPreferences;

// Using a FlowParametersHandler object you can get things easily from
// SharedPreferences
// This is useful to get to settings fast without having to use extra code
// to get to sharedpreferences
public class FlowParametersHandler {

	// Context
	private Context context;

	private String parametersFileName;
	
	private String lastRowFile = "ID_LAST_ROW";

	private static FlowParametersHandler uniqueInstance;

	private FlowParametersHandler(Context con) {
		this.context = con;
		this.parametersFileName = context
				.getString(R.string.parametersFileName);
	}

	// Singleton
	// use SleepParameterHandler.getInstance(getBaseContext()) when in an
	// activity
	public static synchronized FlowParametersHandler getInstance(Context con) {
		if (uniqueInstance == null) {
			uniqueInstance = new FlowParametersHandler(con);
		}
		return uniqueInstance;
	}

	// Check if the user is sleeping
	public boolean checkUserSleeping() {
		parametersFileName = context.getString(R.string.parametersFileName);
		SharedPreferences parameters = context.getSharedPreferences(
				parametersFileName, 0);

		boolean ret = parameters.getBoolean(
				context.getString(R.string.parameters_userSleeping), false);

		return ret;
	}

	// User has woken up, store this in sharedpreferences
	public void setUserAwake() {
		// Save
		SharedPreferences parameters = context.getSharedPreferences(
				parametersFileName, 0);
		SharedPreferences.Editor editor = parameters.edit();

		editor.putBoolean(context.getString(R.string.parameters_userSleeping),
				false);

		// Commit the edits!
		editor.commit();
	}

	// User is sleeping, store this in sharedpreferences
	public void setUserSleeping() {
		// Save
		SharedPreferences parameters = context.getSharedPreferences(
				parametersFileName, 0);
		SharedPreferences.Editor editor = parameters.edit();

		editor.putBoolean(context.getString(R.string.parameters_userSleeping),
				true);

		editor.commit();
	}

	// Does the user want to use the accelerometer?
	public boolean accelerometerUsed() {
		String settingsFileName = context.getString(R.string.settingFileName);
		SharedPreferences settings = context.getSharedPreferences(
				settingsFileName, 0);

		boolean useAcc = settings.getBoolean(
				context.getString(R.string.accsettings_saveUseAccBool), false);
		return useAcc;
	}

	// The id of the last row saved in the database is stored
	// this way the same id can be used when the user wakes up
	// and the correct row in the tables is updated
	public void saveIDlastRow(long ID) {
		// Save
		SharedPreferences parameters = context.getSharedPreferences(
				parametersFileName, 0);
		SharedPreferences.Editor editor = parameters.edit();

		editor.putLong(lastRowFile, ID);

		editor.commit();
	}
	
	// Get the last saved row id
	public long getIDlastRow(){
		parametersFileName = context.getString(R.string.parametersFileName);
		SharedPreferences parameters = context.getSharedPreferences(
				parametersFileName, 0);

		long lastRow = parameters.getLong(lastRowFile,-1);
		
		return lastRow;
	}
	
	// Functions for reminder
	
	// Check if reminder is set
	public boolean isReminderSet(){
		String settingsFileName = context.getString(R.string.settingFileName);
		SharedPreferences settings = context.getSharedPreferences(
				settingsFileName, 0);

		boolean reminder = settings.getBoolean(
				context.getString(R.string.reminder_saveSetReminder), false);
		return reminder;
	}
	
	// Save if user wants the smartphone to play a sound when reminder is triggered
	public boolean playSound(){
		String settingsFileName = context.getString(R.string.settingFileName);
		SharedPreferences settings = context.getSharedPreferences(
				settingsFileName, 0);

		boolean sound = settings.getBoolean(
				context.getString(R.string.reminder_saveSetReminderSound), false);
		return sound;
	}
	
	// Save if user wants the smartphone to vibrate when reminder is triggered
	public boolean vibrate(){
		String settingsFileName = context.getString(R.string.settingFileName);
		SharedPreferences settings = context.getSharedPreferences(
				settingsFileName, 0);

		boolean vib = settings.getBoolean(
				context.getString(R.string.reminder_saveSetReminderVibrate), false);
		return vib;
	}
}
