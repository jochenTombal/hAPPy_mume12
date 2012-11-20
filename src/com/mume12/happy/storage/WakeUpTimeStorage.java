package com.mume12.happy.storage;

import android.content.Context;

public class WakeUpTimeStorage extends TimeStorage {

	private long endDate;
	private int id;

	public WakeUpTimeStorage() {
	}

	public WakeUpTimeStorage(long EndDate) {
		this.setEndDate(EndDate);
	}

	public WakeUpTimeStorage(int id, long EndDate) {
		this.id = id;
		this.endDate = EndDate;
	}

	// SAVE
	public void storeDB(Context context) {
		TimeStorageHandler tsHandler = TimeStorageHandler.getInstance(context);
		tsHandler.updateLastTimeStorage(this, context);
	}

	// GETTERS
	public long getEndDate() {
		return endDate;
	}

	public int getId() {
		return id;
	}

	// SETTERS
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public void setId(int id) {
		this.id = id;
	}
}
