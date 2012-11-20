package com.mume12.happy.general;

import oauth.signpost.OAuth;

import com.mume12.happy.R;
import com.mume12.happy.twitter.PrepareRequestTokenActivity;
import com.mume12.happy.twitter.TwitterHandler;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterActivity extends Activity {

	private SharedPreferences prefs;
	private EditText tweet;
	private TextView charCount;
	private final Handler mTwitterHandler = new Handler();
	
	// Boolean to handle multiple fast clicks of the tweet button (not allowed)
	private boolean isSendingTweet = false;

	// Toast messages
	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(),
					"Tweet sent as user: " + TwitterHandler.userName + "!",
					Toast.LENGTH_LONG).show();
			clearMessageTextView();
		}
	};

	final Runnable twitterException = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(), "Something went wrong!",
					Toast.LENGTH_LONG).show();
		}
	};

	final Runnable notConnected = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(),
					"Please check your internet connection", Toast.LENGTH_LONG)
					.show();
		}
	};

	// Counter for EditText
	private final TextWatcher mWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			charCount.setText(String.valueOf(140 - s.length()));
		}

		public void afterTextChanged(Editable s) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_twitter);

		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		tweet = (EditText) findViewById(R.id.editTextTweet);
		charCount = (TextView) findViewById(R.id.textViewCharCount);

		tweet.addTextChangedListener(mWatcher);
		
		isSendingTweet = false;
	}

	/**
	 * Send a tweet. If the user hasn't authenticated to Twitter yet, he'll be
	 * redirected via a webview to the twitter login page. Once the user
	 * authenticated, he'll authorize the Android application to send tweets on
	 * the users behalf.
	 */
	public void tweetMessage(View v) {
		// Prevent user from sending a tweet multiple times
		if(!isSendingTweet){
			isSendingTweet = true;
			sendTweet();
		} else{
			Toast.makeText(getBaseContext(), "Still handling tweet!",
					Toast.LENGTH_LONG).show();
		}
	}

	// Clear credentials
	public void clearCredentials(View v) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final Editor edit = prefs.edit();
		edit.remove(OAuth.OAUTH_TOKEN);
		edit.remove(OAuth.OAUTH_TOKEN_SECRET);
		edit.commit();
	}

	private String getTweetMsg() {
		String stringTweet = tweet.getText().toString();
		return stringTweet;
	}

	/**
	 * Try to send tweet. If it fails, a TwitterException is thrown. Catch the
	 * exception and start the authorization of the user.
	 * 
	 * All this has to be run in a separate thread to avoid
	 * NetworkOnMainThreadException.
	 */
	public void sendTweet() {		
		// Check if user is connected to internet
		if (isUserConnected()) {
			this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
			Thread t = new Thread() {
				public void run() {
					try {
						// Check if user was already authenticated
						if (TwitterHandler.isAuthenticated(prefs)) {
							TwitterHandler.sendTweet(prefs, getTweetMsg());
							mTwitterHandler.post(mUpdateTwitterNotification);
							isSendingTweet = false;
						} else {
							// start authorization to log in user
							startAuthorization();
							isSendingTweet = false;
						}
					} catch (Exception te) {
						isSendingTweet = false;
						Log.e("TWITTERACTIVITY", "send tweet failed");
						mTwitterHandler.post(twitterException);
						te.printStackTrace();
//						System.exit(-1);
					}
				}

			};
			t.start();
		} else {
			mTwitterHandler.post(notConnected);
		}
	}

	private void startAuthorization() {
		Intent i = new Intent(getApplicationContext(),
				PrepareRequestTokenActivity.class);
		i.putExtra("tweet_msg", getTweetMsg());
		startActivity(i);
	}

	private void clearMessageTextView() {
		// EditText tweet = (EditText) findViewById(R.id.editTextTweet);
		tweet.setText("");
	}

	private boolean isUserConnected() {
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conManager.getActiveNetworkInfo();

		if ((info != null) && info.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBackPressed() {
		// Return to correct screen
		Intent intent = new Intent(this, HomeScreenActivity.class);
		startActivity(intent);
	}

}
