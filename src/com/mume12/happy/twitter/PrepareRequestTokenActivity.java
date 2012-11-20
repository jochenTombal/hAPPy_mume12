package com.mume12.happy.twitter;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import com.mume12.happy.R;
import com.mume12.happy.general.TwitterActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Window;

public class PrepareRequestTokenActivity extends Activity {

	final String DebugTag = "PrepareRequestTokenActivity";

	private OAuthConsumer consumer;
	private OAuthProvider provider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_prepare_req_token);
		try {
			this.consumer = new CommonsHttpOAuthConsumer(
					ConstantsTwitter.CONSUMER_KEY, ConstantsTwitter.CONSUMER_SECRET);
			this.provider = new CommonsHttpOAuthProvider(ConstantsTwitter.REQUEST_URL,
					ConstantsTwitter.ACCESS_URL, ConstantsTwitter.AUTHORIZE_URL);
		} catch (Exception e) {
			Log.e(DebugTag, "Error creating consumer / provider", e);
		}

		Log.i(DebugTag, "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(this, consumer, provider).execute();
	}

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the
	 * request token). The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null
				&& uri.getScheme().equals(ConstantsTwitter.OAUTH_CALLBACK_SCHEME)) {
			Log.i(DebugTag, "Callback received : " + uri);
			Log.i(DebugTag, "Retrieving Access Token");
			new RetrieveAccessTokenTask(this, consumer, provider, prefs)
					.execute(uri);
			finish();
		}
	}

	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

		private Context context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		private SharedPreferences prefs;

		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,
				OAuthProvider provider, SharedPreferences prefs) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
			this.prefs = prefs;
		}

		/**
		 * Retrieve the oauth_verifier, and store the oauth and
		 * oauth_token_secret for future API calls.
		 */
		@Override
		protected Void doInBackground(Uri... params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri
					.getQueryParameter(OAuth.OAUTH_VERIFIER);

			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

				final Editor edit = prefs.edit();
				edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
				edit.putString(OAuth.OAUTH_TOKEN_SECRET,
						consumer.getTokenSecret());
				edit.commit();

				String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
				String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

				consumer.setTokenWithSecret(token, secret);
				context.startActivity(new Intent(context, TwitterActivity.class));

				executeAfterAccessTokenRetrieval();

				Log.i(DebugTag, "OAuth - Access Token Retrieved");

			} catch (Exception e) {
				Log.e(DebugTag, "OAuth - Access Token Retrieval Error", e);
			}

			return null;
		}

		private void executeAfterAccessTokenRetrieval() {
			String msg = getIntent().getExtras().getString("tweet_msg");
			try {
				TwitterHandler.sendTweet(prefs, msg);
			} catch (Exception e) {
				Log.e(DebugTag, "OAuth - Error sending to Twitter", e);
			}
		}
	}
}
