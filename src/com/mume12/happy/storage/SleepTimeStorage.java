package com.mume12.happy.storage;

import android.content.Context;

public class SleepTimeStorage extends TimeStorage{

	private long startDate;
	private int id;

	public SleepTimeStorage() {
	}

	// This constructor is for going to sleep when goingToSleep boolean is true
	// Otherwise set endDate and endTime because user has woken up
	public SleepTimeStorage(long StartDate) {
		this.startDate = StartDate;
	}

	// Constructor to get all results with begin and end-date/time
	public SleepTimeStorage(int id, long startDate) {
		this.id = id;
		this.startDate = startDate;
	}
	
	// SAVE and return rowID
	public long storeDB(Context context){
		TimeStorageHandler tsHandler = TimeStorageHandler.getInstance(context);
		long lastRow = tsHandler.addTimeStorage(this);
		
		return lastRow;
	}

	// Getters
	public long getStartDate() {
		return startDate;
	}

	public int getId() {
		return id;
	}

	// Setters
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public void setId(int id) {
		this.id = id;
	}
}
