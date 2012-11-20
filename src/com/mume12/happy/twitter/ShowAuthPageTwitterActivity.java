package com.mume12.happy.twitter;

import com.mume12.happy.R;
import com.mume12.happy.general.TwitterActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebView;

public class ShowAuthPageTwitterActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_show_auth_page_twitter);

		// Get url passed from OAuthRequestTokenTask
		Intent intent = getIntent();
		String url = intent.getStringExtra("URLVIEW");

		WebView webview = (WebView) findViewById(R.id.webview);
		webview.loadUrl(url);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}

	@Override
	public void onBackPressed() {
		// Return to correct screen
		Intent intent = new Intent(this, TwitterActivity.class);
		startActivity(intent);
	}
}
