package com.mume12.happy.general;

import com.mume12.happy.R;
import com.mume12.happy.services.AcceleroService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class AskWakeUpActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Show fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_ask_wake_up);

		// Wake up screen and unlock
		unlockScreen();
	}

	protected void unlockScreen() {
		// Flags work from API level 5
		Window wind = this.getWindow();
		wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
	}

	// User is getting out of bed
	// Start WakingUpActivity (button clicked)
	public void wakeUp(View v) {
		Intent intent = new Intent(this, WakingUpFragmentActivity.class);
		startActivity(intent);
		// TODO Service hier stoppen?
	}

	// User is not getting out of bed
	// Restart AcceleroService and show android home
	public void dontWakeUp(View v) {
		// Restart Acceleroservice
		Intent i = new Intent(this, AcceleroService.class);
		startService(i);

		// Return to android home
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// Back button pressed
	@Override
	public void onBackPressed() {
		// User must answer question, otherwise the flow of the app is disturbed
		Toast.makeText(this, "Answer the question please..", Toast.LENGTH_LONG)
				.show();
	}
}
