package com.mume12.happy.settings;

import com.mume12.happy.R;
import com.mume12.happy.handlers.ReminderHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TimePicker;

public class ReminderSettingActivity extends Activity {

	TimePicker timeReminder;
	CheckBox setReminder, vibrate, sound;

	private String settingsFileName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reminder_settings);

		// Initialize UI elements
		timeReminder = (TimePicker) findViewById(R.id.timePickerSetReminder);
		timeReminder.setIs24HourView(true);

		setReminder = (CheckBox) findViewById(R.id.checkBoxSetReminder);
		sound = (CheckBox) findViewById(R.id.checkBoxSound);
		vibrate = (CheckBox) findViewById(R.id.checkBoxVibrate);

		// Get filename for settings
		settingsFileName = getString(R.string.settingFileName);

		// Fill in UI elements with data from settingsStorage
		fillInUI();
	}

	@Override
	public void onStop() {
		super.onStop();
		// Save settings
		saveValuesOnStop();

		// ReminderHandler to start/stop reminder
		ReminderHandler remHandler = ReminderHandler.getInstance(this);

		if (setReminder.isChecked()) {

			// Get time
			int min = timeReminder.getCurrentMinute();
			int hour = timeReminder.getCurrentHour();

			// start reminder
			remHandler.startReminder(hour, min, sound.isChecked(),
					vibrate.isChecked());
		} else {
			// Cancel alarmmanager
			// This will also be cancelled when the user has already gone to bed
			// It is reactivated when the user wakes up
			remHandler.stopReminder();
		}
	}

	// Fill in UI with saved settings from previous time
	public void fillInUI() {
		SharedPreferences settings = this.getSharedPreferences(
				settingsFileName, 0);

		// Set TimePicker
		int min = settings.getInt(
				getString(R.string.reminder_saveSetReminderTimeMin), 0);
		timeReminder.setCurrentMinute(min);

		int hour = settings.getInt(
				getString(R.string.reminder_saveSetReminderTimeHour), 0);
		timeReminder.setCurrentHour(hour);

		// Set CheckBoxes
		boolean remind = settings.getBoolean(
				getString(R.string.reminder_saveSetReminder), false);
		setReminder.setChecked(remind);

		boolean pSound = settings.getBoolean(
				getString(R.string.reminder_saveSetReminderSound), false);
		sound.setChecked(pSound);

		boolean vib = settings.getBoolean(
				getString(R.string.reminder_saveSetReminderVibrate), false);
		vibrate.setChecked(vib);
	}

	// Save UI values on stop
	public void saveValuesOnStop() {

		int min = timeReminder.getCurrentMinute();
		int hour = timeReminder.getCurrentHour();

		boolean useReminder = setReminder.isChecked();
		boolean playSound = sound.isChecked();
		boolean vibratePhone = vibrate.isChecked();

		// Save
		SharedPreferences settings = getSharedPreferences(settingsFileName, 0);
		SharedPreferences.Editor editor = settings.edit();

		// Save time
		editor.putInt(getString(R.string.reminder_saveSetReminderTimeMin), min);
		editor.putInt(getString(R.string.reminder_saveSetReminderTimeHour),
				hour);

		// Save CheckBoxes
		editor.putBoolean(getString(R.string.reminder_saveSetReminder),
				useReminder);
		editor.putBoolean(getString(R.string.reminder_saveSetReminderSound),
				playSound);
		editor.putBoolean(getString(R.string.reminder_saveSetReminderVibrate),
				vibratePhone);

		editor.commit();
	}
}
