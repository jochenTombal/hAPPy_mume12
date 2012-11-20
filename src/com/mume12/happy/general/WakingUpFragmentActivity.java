package com.mume12.happy.general;

import java.util.Date;

import com.mume12.happy.R;
import com.mume12.happy.fragments.SeekBarFragmentWU;
import com.mume12.happy.handlers.FlowParametersHandler;
import com.mume12.happy.handlers.ReminderHandler;
import com.mume12.happy.storage.WakeUpEmotionStorage;
import com.mume12.happy.storage.WakeUpTimeStorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

public class WakingUpFragmentActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragmentactivity_waking_up);
	}

	public void wakingUpSubmitResults(View v) {
		// User has woken up (button pressed)
		// set user awake
		FlowParametersHandler flowHandler = FlowParametersHandler
				.getInstance(getBaseContext());
		flowHandler.setUserAwake();

		ReminderHandler remHandler = ReminderHandler.getInstance(this);

		// Enable reminder again if needed
		// Check if reminder was set
		if (flowHandler.isReminderSet()) {
			remHandler.startReminder(flowHandler);
		}

		// Save endtime and enddate
		saveDateTime();

		// Save emotional parameters
		saveEmotionalParameters();

		// Go to homescreen
		Intent intent = new Intent(this, HomeScreenActivity.class);
		startActivity(intent);
	}

	protected void saveDateTime() {
		// Get date
		long date = new Date().getTime();
		Log.i("WU", "Storing time mill: " + date);
		WakeUpTimeStorage wakeUpTimeStorage = new WakeUpTimeStorage(date);

		// save in db by updating last row (which was saved when going to sleep)
		wakeUpTimeStorage.storeDB(this);
	}

	protected void saveEmotionalParameters() {
		CheckBox enoughSleep = (CheckBox) findViewById(R.id.checkBoxEnoughSleepWU);

		WakeUpEmotionStorage wakeUpEmotionStorage = new WakeUpEmotionStorage(
				SeekBarFragmentWU.wakingUpMood.getProgress(),
				SeekBarFragmentWU.wakingUpsleepQuality.getProgress(),
				enoughSleep.isChecked());

		wakeUpEmotionStorage.storeDB(this);
	}
	
}
