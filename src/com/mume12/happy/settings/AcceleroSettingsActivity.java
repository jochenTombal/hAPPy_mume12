package com.mume12.happy.settings;

import com.mume12.happy.R;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

public class AcceleroSettingsActivity extends Activity {

	private String settingsFileName;

	// Boolean to check if accelerometer is found
	private boolean accFound = false;

	// Items from ui
	private SeekBar sensitivityAcc;
	private CheckBox checkboxAcc;
	
	private int limit = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_accelero_settings);

		// Initialize SeekBar and CheckBox

		// If there is an accelerometer:
		// --> get range of acc and set seekbar to that range
		// --> restore settings stored in SharedPreferences
		handleUI();
	}

	// Settings saved when activity is stopped (activity is not visible anymore)
	@Override
	protected void onStop() {
		super.onStop();
		saveValuesOnStop();
	}

	// Depending on whether or not there is an accelerometer
	// this method will set previous values or
	// disable the slider and checkbox and show popup
	public void handleUI() {
		// Filename where settings are stored
		settingsFileName = getString(R.string.settingFileName);
		SharedPreferences settings = getSharedPreferences(settingsFileName, 0);

		// Get previously stored settings
		int sensiAcc = settings.getInt(
				getString(R.string.accsettings_saveAccSensitivityInt), 15);

		boolean useAcc = settings.getBoolean(
				getString(R.string.accsettings_saveUseAccBool), false);

		// Check if accelerometer is present
		accFound = checkAccelero();

		if (accFound) {
			int maxRange = getMaxAcc();

//			Toast.makeText(this, "Max value acc: " + maxRange + " m/s²",
//					Toast.LENGTH_LONG).show();

			// Range below certain value is not enough
			if (maxRange < limit) {
				disableSliderAndCheckbox();
			} else {

				setSlider(maxRange);

				// Set previously stored values
				setStoredValues(sensiAcc, useAcc);
			}
		} else {
			Toast.makeText(this, "No accelerometer found!", Toast.LENGTH_LONG)
					.show();

			// disable slider and checkbox (no acc available)
			disableSliderAndCheckbox();
		}
	}

	// Save settings to persistent storage
	// Settings are saved using SharedPreferences
	// Filenames can be found in strings.xml
	public void saveValuesOnStop() {

		int sens = sensitivityAcc.getProgress();

		checkboxAcc = (CheckBox) findViewById(R.id.checkBoxUseAccelerometer);
		boolean use = checkboxAcc.isChecked();

		// Save
		SharedPreferences settings = getSharedPreferences(settingsFileName, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putInt(getString(R.string.accsettings_saveAccSensitivityInt),
				sens);
		editor.putBoolean(getString(R.string.accsettings_saveUseAccBool), use);

		// Commit the edits!
		editor.commit();
	}

	// Set previously stored settings
	public void setStoredValues(int sens, boolean use) {
		sensitivityAcc = (SeekBar) findViewById(R.id.seekBarAccSensitivity);
		sensitivityAcc.setProgress(sens);

		checkboxAcc = (CheckBox) findViewById(R.id.checkBoxUseAccelerometer);
		checkboxAcc.setChecked(use);
	}

	// Disable slider and checkbox
	// this is called when there is no accelerometer
	public void disableSliderAndCheckbox() {
		sensitivityAcc = (SeekBar) findViewById(R.id.seekBarAccSensitivity);
		sensitivityAcc.setEnabled(false);

		checkboxAcc = (CheckBox) findViewById(R.id.checkBoxUseAccelerometer);
		checkboxAcc.setChecked(false);
		checkboxAcc.setEnabled(false);
	}

	// Get accelerometer maximum range
	public int getMaxAcc() {
		SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor mAccelero = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		float range = mAccelero.getMaximumRange();

		// subtract 1 m/s² to be on the safe side
		int max = ((int) Math.rint(range)) - 1;

		return max;
	}

	// Set the range for the seekbar
	// the maximum value is the maxRange of the accelerometer
	public void setSlider(int max) {
		// Initialize
		sensitivityAcc = (SeekBar) findViewById(R.id.seekBarAccSensitivity);

		// Set max value for sliders
		sensitivityAcc.setMax(max);
	}

	// Check if there is an accelerometer in the device
	public boolean checkAccelero() {
		// Reference to sensor service
		SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// Test if accelerometer is present
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
			// Accelerometer present
			return true;
		} else {
			return false;
		}
	}
}
