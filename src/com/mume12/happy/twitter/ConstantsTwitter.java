package com.mume12.happy.twitter;

// Just some constants for twitter
public class ConstantsTwitter {
	public static final String CONSUMER_KEY = "4PAm36PkCZmrUk5SOV9itA";
	public static final String CONSUMER_SECRET = "SbAfoVfhQTiLH2qA7dXLLDalxRO2wvdpfRnDtrUEg";

	public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-twitter";
	public static final String	OAUTH_CALLBACK_HOST	= "callback";
	public static final String	OAUTH_CALLBACK_URL	= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
