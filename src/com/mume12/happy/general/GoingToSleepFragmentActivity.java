package com.mume12.happy.general;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mume12.happy.R;
import com.mume12.happy.fragments.SeekBarFragmentGTS;
import com.mume12.happy.handlers.FlowParametersHandler;
import com.mume12.happy.handlers.ReminderHandler;
import com.mume12.happy.handlers.SleepTip;
import com.mume12.happy.services.AcceleroService;
import com.mume12.happy.storage.SleepEmotionStorage;
import com.mume12.happy.storage.SleepTimeStorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class GoingToSleepFragmentActivity extends FragmentActivity {

	// flowHandler to easily save sharedprefs
	private FlowParametersHandler flowHandler;

	// Boolean to check if accelerometer can be used
	private boolean useAccelero = false;

	private final int maxRecHours = 24;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragmentactivity_goingtosleep);

		// Filename where settings are stored
		String settingsFileName = getString(R.string.settingFileName);
		SharedPreferences settings = getSharedPreferences(settingsFileName, 0);

		/**
		 * Check if accelerometer has to be used // If there is no acc then the
		 * AcceleroService will not be used, // and also if the user decides he
		 * doesn't want to use the acc.
		 */
		useAccelero = settings.getBoolean(
				getString(R.string.accsettings_saveUseAccBool), false);
	}

	// Going to sleep button pushed
	public void GoingToSleep(View v) {

		// Check number of recreational hours smaller than max
		if (getRecHours() > maxRecHours) {
			Toast.makeText(this,
					"More than 24 hours recreation?",
					Toast.LENGTH_LONG).show();
		} else {

			// flowHandler used to easily save SharedPrefs used for flow of app
			flowHandler = FlowParametersHandler.getInstance(getBaseContext());

			ReminderHandler remHandler = ReminderHandler.getInstance(this);

			// Check if reminder was set
			if (flowHandler.isReminderSet()) {
				// Disable reminder when user is sleeping!
				remHandler.stopReminder();
			}

			// Save user is sleeping
			flowHandler.setUserSleeping();

			// Get time and date
			SleepTimeStorage slTimeStorage = getTimeAndDate();

			// Get emotion parameters from UI
			SleepEmotionStorage slEmotionStorage = getEmotionParameters();

			// Get all storage and find parameters which resemble these
			// parameters
			SleepTip.findTip(this, slEmotionStorage, slTimeStorage);

			// Save time and date
			long rowID = saveTimeAndDate(slTimeStorage);

			// Save emotional parameters
			saveEmotionalParameters(slEmotionStorage, rowID);

			// Start acceleroservice only if there is an accelerometer
			// And only if user wants to use accelerometer
			if (useAccelero) {
				Intent i = new Intent(this, AcceleroService.class);
				startService(i);

				Toast.makeText(this,
						"Don't forget to plug in your smartphone!!!",
						Toast.LENGTH_LONG).show();

				// return to android home screen
				goToAndroidHome();

				/**
				 * Finish this activity to make sure that the user always sees
				 * the homescreen when the app is restarted this is needed if
				 * the accelerometer didn't trigger the ask wakeup activity
				 */
				this.finish();

			} else {
				// Return to home screen by finishing this activity
				this.finish();
			}
		}
	}

	protected SleepTimeStorage getTimeAndDate() {
		// Get date
		// Create object to store in SQLite database
		long date = new Date().getTime();

		Log.i("GTS", "Storing time mill: " + date);

		SleepTimeStorage sleepSt = new SleepTimeStorage(date);

		return sleepSt;
	}

	protected double getRecHours() {
		EditText nr_rec_hours = (EditText) findViewById(R.id.editTextRecreationalTimeGTS);
		String rec_hours_value = nr_rec_hours.getText().toString();

		if(rec_hours_value.equals("") == false){
			return Double.parseDouble(rec_hours_value);
		} else{
			return 0;
		}
	}

	protected SleepEmotionStorage getEmotionParameters() {
		// List with seekbar values (from seekbarfragment)
		List<Integer> emPar = getEmotionalParameters();

		// Checkbox enough recreational hours
		CheckBox checkBoxRecHours = (CheckBox) findViewById(R.id.checkBoxEnoughRecGTS);
		boolean enough_rec_hours = checkBoxRecHours.isChecked();

		// Number of recreational hours
		EditText nr_rec_hours = (EditText) findViewById(R.id.editTextRecreationalTimeGTS);
		String rec_hours_value = nr_rec_hours.getText().toString();
		double rec_hours = 0;
		if(rec_hours_value.equals("") == false){
			rec_hours = Double.parseDouble(rec_hours_value);
		}

		// Alcohol checkbox
		CheckBox checkBoxAlcohol = (CheckBox) findViewById(R.id.checkBoxAlcoholGTS);
		boolean alcohol = checkBoxAlcohol.isChecked();

		// Caffeine checkboxrec
		CheckBox checkBoxCaffeine = (CheckBox) findViewById(R.id.checkBoxCaffeineGTS);
		boolean caffeine = checkBoxCaffeine.isChecked();

		// Create SleepEmotionStorage object so we can easily save to database
		SleepEmotionStorage sleepEmotionStorage = new SleepEmotionStorage(
				emPar.get(0), emPar.get(1), emPar.get(2), emPar.get(3),
				emPar.get(4), rec_hours, enough_rec_hours, alcohol, caffeine);

		return sleepEmotionStorage;
	}

	protected List<Integer> getEmotionalParameters() {
		List<Integer> sliderValues = new ArrayList<Integer>();

		/**
		 * All sliders reside within the SeekBarFragmentGTS fragment Values can
		 * be retrieved by calling the getProgress method on the static seekbars
		 */
		sliderValues.add(SeekBarFragmentGTS.mood.getProgress());
		sliderValues.add(SeekBarFragmentGTS.socialQuality.getProgress());
		sliderValues.add(SeekBarFragmentGTS.socialQuantity.getProgress());
		sliderValues.add(SeekBarFragmentGTS.stressWork.getProgress());
		sliderValues.add(SeekBarFragmentGTS.stressNonWork.getProgress());

		return sliderValues;
	}

	protected long saveTimeAndDate(SleepTimeStorage sleepSt) {
		// Save sleeptimestorage
		// ID to identify this row is returned and stored with sharedpref
		// This rowID is later used to store items in the correct row at wakeup
		long lastRow = sleepSt.storeDB(this);

		/**
		 * Store id of last row so we can update it at wake-up Use Handler so we
		 * don't have to deal with sharedprefs here FlowParametersHandler
		 * flowHandler = FlowParametersHandler .getInstance(this);
		 */
		flowHandler.saveIDlastRow(lastRow);

		return lastRow;
	}

	protected void saveEmotionalParameters(
			SleepEmotionStorage sleepEmotionStorage, long rowID) {
		// Save to database
		sleepEmotionStorage.storeDB(this, rowID);
	}

	protected void goToAndroidHome() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
