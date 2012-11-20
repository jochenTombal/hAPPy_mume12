package com.mume12.happy.twitter;

import com.mume12.happy.general.TwitterActivity;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {
	
	private final Handler mTwitterHandler = new Handler();
	
	final Runnable twitterException = new Runnable() {
		public void run() {
			Toast.makeText(context, "Something went wrong when starting authentication!\n" +
					"Please check your connection.", Toast.LENGTH_LONG)
					.show();
		}
	};

	final String debugTag = "OAuthRequestTokenTask";
	private Context context;
	private OAuthProvider provider;
	private OAuthConsumer consumer;

	public OAuthRequestTokenTask(Context context, OAuthConsumer consumer,
			OAuthProvider provider) {
		this.context = context;
		this.consumer = consumer;
		this.provider = provider;
	}

	/**
	 * 
	 * Retrieve the OAuth Request Token and present a webview to the user to
	 * authorize the token.
	 * 
	 */
	@Override
	protected Void doInBackground(Void... params) {
		

		try {
			final String url = provider.retrieveRequestToken(consumer,
					ConstantsTwitter.OAUTH_CALLBACK_URL);
			Log.i(debugTag, "Showing webview with the authorize URL : " + url);
			
			// Show in webview
			// Flag "from background" indicates this intent is started from background operation
			Intent intent = new Intent(context,ShowAuthPageTwitterActivity.class).
					setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
					| Intent.FLAG_ACTIVITY_NO_HISTORY
					| Intent.FLAG_FROM_BACKGROUND);
			intent.putExtra("URLVIEW", url);
			context.startActivity(intent);
		} catch (Exception e) {
			Log.e(debugTag, "Error during OAUth retrieve request token", e);
			mTwitterHandler.post(twitterException);
			context.startActivity(new Intent(context, TwitterActivity.class));
		}

		return null;
	}

}
