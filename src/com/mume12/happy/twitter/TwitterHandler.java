package src.com.mume12.happy.twitter;

import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.SharedPreferences;
import android.util.Log;

public class TwitterHandler {
	
	public static String userName = "No name";
	
	public static boolean isAuthenticated(SharedPreferences prefs){
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		
		if(token.equals("") || secret.equals("")){
			Log.e("TWITTERUTILS","tokens not in storage");
			return false;
		}

		AccessToken a = new AccessToken(token,secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(ConstantsTwitter.CONSUMER_KEY, ConstantsTwitter.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);

		try {
			// If we can get the screenname, the user is already authenticated
			userName = twitter.getScreenName();
			Log.i("TWITTERUTILS","isAuthenticated succeeded");
			return true;
		} catch(TwitterException te){
			Log.e("TWITTERUTILS","isAuthenticated failed");
			te.printStackTrace();
			return false;
		}
	}

	public static void sendTweet(SharedPreferences prefs, String msg)
			throws Exception {
		// Get tokens
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(ConstantsTwitter.CONSUMER_KEY,
				ConstantsTwitter.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
		// Send tweet
		twitter.updateStatus(msg);
	}

}
