package com.mume12.happy.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	/** Database Version
	* If you change the database schema, you must increment the database
	* version (for app update).
	**/ 
	private static final int DATABASE_VERSION = 1;

	// Database Name
	protected static final String DATABASE_NAME = "hAPPy";

	// Table names
	protected final String TABLE_TIMEDATE = "time_and_date";
	protected final String TABLE_EMOTION_PARAMETERS = "emotion_parameters";

	// Table timedate column names
	protected final String KEY_ID = "id";
	protected final String KEY_STARTDATE = "startdate";
	protected final String KEY_ENDDATE = "enddate";

	// Table emotion parameters column names
	protected final String SLEEP_MOOD = "mood_sleep";
	protected final String SOCIAL_CONTACT_QUAL = "social_contact_quality";
	protected final String SOCIAL_CONTACT_QUAN = "social_contact_quantity";
	protected final String STRESSWORK = "stress_work";
	protected final String STRESSNONWORK = "stress_non_work";
	protected final String RECREATIONAL_HOURS = "recreational_hours";
	protected final String ENOUGH_REC = "enough_rec";
	protected final String ALCOHOL = "alcohol";
	protected final String CAFFEINE = "caffeine";

	protected final String WAKE_UP_MOOD = "wake_up_mood";
	protected final String SLEEP_QUALITY = "sleep_quality";
	protected final String ENOUGH_SLEEP = "enough_sleep";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {		
		String CREATE_SLEEP_TABLE = "CREATE TABLE " + TABLE_TIMEDATE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_STARTDATE + " TEXT,"
				+ KEY_ENDDATE + " TEXT" + ")";
		db.execSQL(CREATE_SLEEP_TABLE);

		String CREATE_EMOTION_TABLE = "CREATE TABLE "
				+ TABLE_EMOTION_PARAMETERS + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + SLEEP_MOOD + " INTEGER,"
				+ SOCIAL_CONTACT_QUAL + " INTEGER," + SOCIAL_CONTACT_QUAN
				+ " INTEGER," + STRESSWORK + " INTEGER," + STRESSNONWORK + " DOUBLE," + RECREATIONAL_HOURS
				+ " INTEGER," + ENOUGH_REC + " INTEGER," + ALCOHOL + " INTEGER," + CAFFEINE + " INTEGER," + WAKE_UP_MOOD
				+ " INTEGER," + SLEEP_QUALITY + " INTEGER," + ENOUGH_SLEEP
				+ " INTEGER" + ")";

		db.execSQL(CREATE_EMOTION_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMEDATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOTION_PARAMETERS);

		// Create tables again
		onCreate(db);
	}
}
