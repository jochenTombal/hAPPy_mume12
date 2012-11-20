package com.mume12.happy.services;

import com.mume12.happy.R;
import com.mume12.happy.general.AskWakeUpActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

// We don't have to start a new thread in which the service can do it's work because it will only run during night
public class AcceleroService extends Service implements SensorEventListener {

	// Settingsfile so we can get sensitivity from saved settings
	private static String settingsFileName;

	// Accelerometer
	private SensorManager mSensorManager;
	private Sensor mAccelero;

	// Default stop condition accelerometer
	private double stopCondition = 15.0;

	// Only called the very first time
	@Override
	public void onCreate() {
		// Reference to sensor service
		this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// Get accelerometer
		if (mSensorManager != null) {
			this.mAccelero = mSensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			
			// Filename where settings are stored
			settingsFileName = getString(R.string.settingFileName);
			// SharedPref file used to store settings
			SharedPreferences settings = getSharedPreferences(settingsFileName,0);

			// Get stopcondition from saved settings
			stopCondition = settings.getInt(
					getString(R.string.accsettings_saveAccSensitivityInt), 15);

		}
	}

	// This method is called when the service is started with startService() (by
	// an activity or...)
	// Don't forget to stop with stopService() or stopSelf() !!!
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Register listener
		mSensorManager.registerListener(this, mAccelero,
				SensorManager.SENSOR_DELAY_NORMAL);

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	// Called when service is stopped
	@Override
	public void onDestroy() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// We don't provide binding, so return null
		return null;
	}

	// SENSOR METHODS
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent sensevent) {
		// View axes orientation at
		// http://developer.android.com/reference/android/hardware/SensorEvent.html

		// x-axes
		// default = 0 m/s²
		//double axes1 = sensevent.values[0];

		// y-axes
		// default = 0 m/s²
		//double axes2 = sensevent.values[1];

		// z-axes
		// default = -9.8 m/s²
		//double axes3 = sensevent.values[2];

//		Log.i("TEST ACCELEROVALUE", "Axes1: " + axes1 + " m/s² \n" + "Axes2: "
//				+ axes2 + " m/s² \n" + "Axes3: " + axes3 + " m/s² \n");

		// Shake to wake up phone
		if (sensevent.values[0] > stopCondition) {			
			// stop this service
			this.stopSelf();

			// start AskWakeUpActivity
			Intent intent = new Intent(this, AskWakeUpActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

	}
}
