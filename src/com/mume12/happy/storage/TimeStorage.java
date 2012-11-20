package com.mume12.happy.storage;

public class TimeStorage {
	private int id;
	private long startDate, endDate;

	public TimeStorage() {
	}

	public TimeStorage(long startDate, long endDate) {
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public TimeStorage(int id, long startDate, long endDate) {
		this.setId(id);

		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	// GETTERS
	public int getId() {
		return id;
	}

	public long getStartDate() {
		return startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	// SETTERS
	public void setId(int id) {
		this.id = id;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
}
