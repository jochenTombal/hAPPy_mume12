package com.mume12.happy.storage;

import java.util.ArrayList;
import java.util.List;

import com.mume12.happy.handlers.FlowParametersHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimeStorageHandler extends DatabaseHandler {

	private static TimeStorageHandler uniqueInstance;

	// Table name
	private static final String TABLE_TIMEDATE = "time_and_date";

	// Table timedate column names
	private String KEY_ID;
	private String KEY_STARTDATE;
	private String KEY_ENDDATE;

	private TimeStorageHandler(Context context) {
		super(context);

		this.KEY_ID = super.KEY_ID;
		this.KEY_STARTDATE = super.KEY_STARTDATE;
		this.KEY_ENDDATE = super.KEY_ENDDATE;
	}

	public static synchronized TimeStorageHandler getInstance(Context con) {
		if (uniqueInstance == null) {
			uniqueInstance = new TimeStorageHandler(con);
		}
		return uniqueInstance;
	}

	// Add date and time when user is going to sleep
	// Use object TimeStorage here (SleepStorage will automatically be used
	// because that is used in GoingToSleepActivity)
	public long addTimeStorage(TimeStorage sleepStorage) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STARTDATE, Long.toString(sleepStorage.getStartDate())); // Start Date

		// Inserting Row
		long lastRow = db.insert(TABLE_TIMEDATE, null, values);
		db.close(); // Closing database connection

		return lastRow;
	}

	// Update row when user wakes up
	// Date and time when user wakes up are added
	public int updateLastTimeStorage(TimeStorage timeStorage, Context con) {

		// get id of last row in database which is stored using
		// sharedpreferences
		FlowParametersHandler sleepHandler = FlowParametersHandler
				.getInstance(con);
		long lastRow = sleepHandler.getIDlastRow();

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ENDDATE, Long.toString(timeStorage.getEndDate()));

		// updating row
		int ret = db.update(TABLE_TIMEDATE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(lastRow) });

		db.close(); // Closing database connection

		return ret;
	}

	// Getting single row
	public TimeStorage getTimeDataStorage(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TIMEDATE, new String[] { KEY_ID,
				KEY_STARTDATE, KEY_ENDDATE },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);

		if (cursor != null)
			cursor.moveToFirst();

		// Make sure you put the items in the right order
		TimeStorage timeStorage = new TimeStorage(Integer.parseInt(cursor
				.getString(0)), Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)));

		cursor.close();
		db.close();

		return timeStorage;
	}

	// Getting All TimeStorage
	public List<TimeStorage> getAllTimeStorage() {
		List<TimeStorage> timeStorageList = new ArrayList<TimeStorage>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TIMEDATE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TimeStorage timeStorage = new TimeStorage();
				timeStorage.setId(Integer.parseInt(cursor.getString(0)));
				timeStorage.setStartDate(Long.parseLong(cursor.getString(1)));
				timeStorage.setEndDate(Long.parseLong(cursor.getString(2)));

				// Adding TimeStorage object to list
				timeStorageList.add(timeStorage);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		// return TimeStorage list
		return timeStorageList;
	}

	// Updating entire TimeStorage object
	public int updateTimeStorage(TimeStorage timeStorage) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STARTDATE, Long.toString(timeStorage.getStartDate()));
		values.put(KEY_ENDDATE, Long.toString(timeStorage.getEndDate()));

		// updating row
		int ret = db.update(TABLE_TIMEDATE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(timeStorage.getId()) });

		db.close(); // Closing database connection

		return ret;
	}

	// Deleting single TimeStorage object (single row)
	public void deleteTimeStorage(TimeStorage timeStorage) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TIMEDATE, KEY_ID + " = ?",
				new String[] { String.valueOf(timeStorage.getId()) });
		db.close();
	}

	// Getting row Count
	public int getStorageCount() {
		String countQuery = "SELECT  * FROM " + TABLE_TIMEDATE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		db.close();

		// return count
		return cursor.getCount();
	}

}
