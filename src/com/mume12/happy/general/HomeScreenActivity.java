package com.mume12.happy.general;

import com.mume12.happy.R;
import com.mume12.happy.handlers.FlowParametersHandler;
import com.mume12.happy.settings.SettingsActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class HomeScreenActivity extends Activity {

	// Buttons
	private Button goingToSleep, wakingUp, results;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB){
			// No title bar
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		setContentView(R.layout.activity_home_screen);
		
	}

	@Override
	public void onResume() {
		super.onResume();
		// We have to disable the correct button(s)
		handleButtons();
	}

	protected void handleButtons() {
		// Initialize flowHandler
		FlowParametersHandler sleepHandler = FlowParametersHandler.getInstance(getBaseContext());

		// Initialize buttons
		goingToSleep = (Button) findViewById(R.id.homescreen_goingtosleep);
		wakingUp = (Button) findViewById(R.id.homescreen_wakingup);
		results = (Button) findViewById(R.id.homescreen_results);

		if (sleepHandler.checkUserSleeping()) {
			// Can't see results while sleeping
			// So disable button
			results.setEnabled(false);

			// This check is needed if user has started the app without trigger by
			// accelerometer
			if (sleepHandler.accelerometerUsed()) {
				goingToSleep.setEnabled(true);
				wakingUp.setEnabled(false);

				// redirect to askwakeup activity (app was opened without acc
				// triggering this activity)
				Intent intent = new Intent(this, AskWakeUpActivity.class);
				startActivity(intent);

			} else {
				// accelero not used
				// disable going to sleep button
				goingToSleep.setEnabled(false);
				wakingUp.setEnabled(true);
			}
		} else {
			// Can see results when awake
			results.setEnabled(true);

			// disable waking up button and enable going to sleep button
			goingToSleep.setEnabled(true);
			wakingUp.setEnabled(false);
		}
	}

	public void startGoingToSleep(View v) {
		Intent i = new Intent(this, GoingToSleepFragmentActivity.class);
		startActivity(i);
	}

	public void startWakingUp(View v) {
//		 Intent i = new Intent(this, WakingUpActivity.class);
		Intent i = new Intent(this, WakingUpFragmentActivity.class);
		 startActivity(i);
	}

	public void startResults(View v) {
		Intent i = new Intent(this, SelectResultsActivity.class);
		startActivity(i);
	}

	public void startSettingsActivity() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	public void startTwitter(View v){
		Intent intent = new Intent(this, TwitterActivity.class);
		startActivity(intent);
	}

	// Back button pressed
	@Override
	public void onBackPressed() {
		// Always go back to android home
		// Return to android home
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// Create menu (which shows the Settings option)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.homescreen_settings:
			startSettingsActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
