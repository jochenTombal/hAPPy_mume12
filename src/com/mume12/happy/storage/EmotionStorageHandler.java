package com.mume12.happy.storage;

import java.util.ArrayList;
import java.util.List;

import com.mume12.happy.handlers.FlowParametersHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EmotionStorageHandler extends DatabaseHandler {

	private static EmotionStorageHandler uniqueInstance;

	private EmotionStorageHandler(Context context) {
		super(context);
	}

	public static synchronized EmotionStorageHandler getInstance(Context con) {
		if (uniqueInstance == null) {
			uniqueInstance = new EmotionStorageHandler(con);
		}
		return uniqueInstance;
	}

	// Add emotional parameters when user is going to sleep
	public void addEmotionStorage(EmotionStorage emotionStorage, long lastRow) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, lastRow);
		values.put(SLEEP_MOOD, emotionStorage.get_mood_sleep());
		values.put(SOCIAL_CONTACT_QUAL,
				emotionStorage.get_soc_contact_quality());
		values.put(SOCIAL_CONTACT_QUAN,
				emotionStorage.get_soc_contact_quantity());
		values.put(STRESSWORK, emotionStorage.get_stress_work());
		values.put(STRESSNONWORK, emotionStorage.get_stress_non_work());
		values.put(RECREATIONAL_HOURS, emotionStorage.get_rec_hours());
		values.put(ENOUGH_REC, emotionStorage.get_rec_enough());
		values.put(ALCOHOL, emotionStorage.get_alcohol());
		values.put(CAFFEINE, emotionStorage.get_caffeine());

		// Inserting Row
		db.insert(TABLE_EMOTION_PARAMETERS, null, values);

		db.close();
	}

	// Update last emotional parameters when user wakes up
	public void updateEmotionStorage(EmotionStorage emotionStorage, Context con) {
		// get id of last row in database which is stored using
		// sharedpreferences (this is the same id as sleepDateTime)
		FlowParametersHandler sleepHandler = FlowParametersHandler
				.getInstance(con);
		long lastRow = sleepHandler.getIDlastRow();

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(WAKE_UP_MOOD, emotionStorage.get_wake_up_mood());
		values.put(SLEEP_QUALITY, emotionStorage.get_sleep_quality());
		values.put(ENOUGH_SLEEP, emotionStorage.get_enough_sleep());

		Log.i("UPDATING", "wakeupmood: " + emotionStorage.get_wake_up_mood());
		Log.i("UPDATING", "sleepqual: " + emotionStorage.get_sleep_quality());
		Log.i("UPDATING", "enoughsleep: " + emotionStorage.get_enough_sleep());

		// updating row
		db.update(TABLE_EMOTION_PARAMETERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(lastRow) });

		db.close(); // Closing database connection
	}

	// Getting single row
	public EmotionStorage getEmotionStorage(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_EMOTION_PARAMETERS, new String[] {
				KEY_ID, SLEEP_MOOD, SOCIAL_CONTACT_QUAL, SOCIAL_CONTACT_QUAN,
				STRESSWORK, STRESSNONWORK, RECREATIONAL_HOURS, ENOUGH_REC,
				ALCOHOL, CAFFEINE, WAKE_UP_MOOD, SLEEP_QUALITY, ENOUGH_SLEEP },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);

		if (cursor != null)
			cursor.moveToFirst();

		int key = Integer.parseInt(cursor.getString(0));
		int sleep_mood = cursor.getInt(1);
		int soc_qual = cursor.getInt(2);
		int soc_quan = cursor.getInt(3);
		int stresswork = cursor.getInt(4);
		int stressnonwork = cursor.getInt(5);
		double rec_hours = cursor.getInt(6);

		boolean enough_rec = true;
		if (cursor.getInt(7) == 0) {
			enough_rec = false;
		}

		boolean alcohol = true;
		if (cursor.getInt(8) == 0) {
			alcohol = false;
		}

		boolean caffeine = true;
		if (cursor.getInt(9) == 0) {
			caffeine = false;
		}

		int wake_up_mood = cursor.getInt(10);
		int sleep_quality = cursor.getInt(11);

		boolean enough_sleep = true;
		if (cursor.getInt(12) == 0) {
			enough_sleep = false;
		}

		EmotionStorage emotionStorage = new EmotionStorage(key, sleep_mood,
				soc_qual, soc_quan, stresswork, stressnonwork, rec_hours,
				enough_rec, wake_up_mood, sleep_quality, enough_sleep, alcohol,
				caffeine);

		cursor.close();
		db.close();

		return emotionStorage;
	}	
	
	public List<EmotionStorage> getAllEmotionStorage() {
		List<EmotionStorage> emotionStorageList = new ArrayList<EmotionStorage>();

		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_EMOTION_PARAMETERS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				boolean enough_rec = true;
				if (cursor.getInt(7) == 0) {
					enough_rec = false;
				}

				boolean alcohol = true;
				if (cursor.getInt(8) == 0) {
					alcohol = false;
				}

				boolean caffeine = true;
				if (cursor.getInt(9) == 0) {
					caffeine = false;
				}

				boolean enough_sleep = true;
				if (cursor.getInt(12) == 0) {
					enough_sleep = false;
				}

				int key = Integer.parseInt(cursor.getString(0));
				int sleep_mood = cursor.getInt(1);
				int soc_qual = cursor.getInt(2);
				int soc_quan = cursor.getInt(3);
				int stresswork = cursor.getInt(4);
				int stressnonwork = cursor.getInt(5);
				double rec_hours = cursor.getInt(6);

				int wake_up_mood = cursor.getInt(10);
				int sleep_quality = cursor.getInt(11);

				EmotionStorage emotionStorage = new EmotionStorage(key,
						sleep_mood, soc_qual, soc_quan, stresswork,
						stressnonwork, rec_hours, enough_rec, wake_up_mood,
						sleep_quality, enough_sleep, alcohol, caffeine);

				emotionStorageList.add(emotionStorage);

				Log.i("GETTING", "Wakeupmood: " + cursor.getInt(10));
				Log.i("GETTING", "Sleepqual: " + cursor.getInt(11));
				Log.i("GETTING", "enoughsleep: " + cursor.getInt(12));

			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return emotionStorageList;
	}

	/**
	 * Methods to get all columns separately
	 */
	
	public List<Integer> getKeys() {
		List<Integer> keys = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + KEY_ID + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				keys.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return keys;
	}
	
	// Alle sleep moods terugkrijgen in List
	public List<Integer> getSleepMoods() {
		List<Integer> sleepMoods = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + SLEEP_MOOD + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				sleepMoods.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return sleepMoods;
	}
	
	// Sleep mood van bepaalde KEY_ID terugkrijgen
	public int getSleepMoods(int id) {
		int sleepMood = -1;
		
		String selectQuery = "SELECT " + SLEEP_MOOD + " FROM "
				+ TABLE_EMOTION_PARAMETERS + " WHERE " + KEY_ID + " = " + id;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if(cursor != null)
		{
			cursor.moveToFirst();
			sleepMood = cursor.getInt(0);
		}

		
		return sleepMood;
	}
	
	public List<Integer> getSocialQuality() {
		List<Integer> socQual = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + SOCIAL_CONTACT_QUAL + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				socQual.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return socQual;
	}
	
	public List<Integer> getSocialQuantity() {
		List<Integer> socQuan = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + SOCIAL_CONTACT_QUAN + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				socQuan.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return socQuan;
	}
	
	public List<Integer> getStressWork() {
		List<Integer> stressWork = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + STRESSWORK + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				stressWork.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return stressWork;
	}
	
	public List<Integer> getStressNonWork() {
		List<Integer> stressNonWork = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + STRESSNONWORK + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				stressNonWork.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return stressNonWork;
	}
	
	public List<Integer> getRecHours() {
		List<Integer> recHours = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + RECREATIONAL_HOURS + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				recHours.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return recHours;
	}
	
	public List<Integer> getEnoughRec() {
		List<Integer> enoughRec = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + ENOUGH_REC + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				enoughRec.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return enoughRec;
	}
	
	public List<Integer> getAlcohol() {
		List<Integer> alcohol = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + ALCOHOL + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				alcohol.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return alcohol;
	}

	public List<Integer> getCaffeine() {
		List<Integer> caffeine = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + CAFFEINE + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				caffeine.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return caffeine;
	}
	
	public List<Integer> getWakeUpMood() {
		List<Integer> moodWakeUp = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + WAKE_UP_MOOD + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				moodWakeUp.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return moodWakeUp;
	}
	
	public List<Integer> getSleepQuality() {
		List<Integer> sleepQual = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + SLEEP_QUALITY + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				sleepQual.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return sleepQual;
	}
	
	public List<Integer> getEnougSleep() {
		List<Integer> enoughSleep = new ArrayList<Integer>();
		
		String selectQuery = "SELECT " + ENOUGH_SLEEP + " FROM "
				+ TABLE_EMOTION_PARAMETERS;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				enoughSleep.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		
		return enoughSleep;
	}
	
//	public void test() {
//	
//	List<Integer> keys = getKeys();
//	Log.i("KEYS", "keys: " + keys);
//	
//	List<Integer> sleepmoods = getSleepMoods();
//	Log.i("SLEEPMOODS", "sleepmoods: " + sleepmoods);
//	
//	Log.i("SLEEPMOOD KEY", "sleepmoods 1: " + getSleepMoods(1));
//	
//	List<Integer> qual = getSocialQuality();
//	Log.i("SOCQUAL", "socqual: " + qual);
//	
//	List<Integer> quan = getSocialQuantity();
//	Log.i("SOCQUAn", "socquan: " + quan);
//	
//	List<Integer> stresswork = getStressWork();
//	Log.i("STRESSWORK", "stresswork: " + stresswork);
//	
//	List<Integer> stressnonwork = getStressNonWork();
//	Log.i("STRESSNONWORK", "stressnonwork: " + stressnonwork);
//	
//	List<Integer> recHours = getRecHours();
//	Log.i("RECHOURS", "recHours: " + recHours);
//	
//	List<Integer> enoughRec = getEnoughRec();
//	Log.i("ENOUGHREC", "enoughRec: " + enoughRec);
//	
//	List<Integer> alcohol = getAlcohol();
//	Log.i("ALCOHOL", "alcohol: " + alcohol);
//	
//	List<Integer> caffeine = getCaffeine();
//	Log.i("CAFFEINE", "caffeine: " + caffeine);
//	
//	List<Integer> wakeupmood = getWakeUpMood();
//	Log.i("WAKEUPMOOD", "wake up mood: " + wakeupmood);
//	
//	List<Integer> sleepqual = getSleepQuality();
//	Log.i("SLEEPQUAL", "sleepqual: " + sleepqual);
//	
//	List<Integer> enoughsleep = getEnougSleep();
//	Log.i("ENOUGHSLEEP", "enoughsleep: " + enoughsleep);
//}
}
