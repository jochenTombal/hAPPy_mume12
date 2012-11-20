package com.mume12.happy.storage;

import android.content.Context;

public class WakeUpEmotionStorage extends EmotionStorage{
	private int sleep_quality, wake_up_mood;
	private int id;
	
	private int enough_sleep;
	
	public WakeUpEmotionStorage(){}
	
	public WakeUpEmotionStorage(int _wake_up_mood, int _sleep_quality, boolean _enough_sleep) {
		
		this.wake_up_mood = _wake_up_mood;
		this.sleep_quality = _sleep_quality;
		
		if(_enough_sleep){
			this.enough_sleep = 1;
		} else{
			this.enough_sleep = 0;
		}
	}

	public WakeUpEmotionStorage(int _id, int _wake_up_mood, int _sleep_quality, boolean _enough_sleep) {
		this.id = _id;
		
		this.wake_up_mood = _wake_up_mood;
		this.sleep_quality = _sleep_quality;
		
		if(_enough_sleep){
			this.enough_sleep = 1;
		} else{
			this.enough_sleep = 0;
		}
	}
	
	// SAVE
	public void storeDB(Context context){
		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(context);
		
		emotionHandler.updateEmotionStorage(this, context);
	}

	// GETTERS
	public int get_sleep_quality() {
		return sleep_quality;
	}
	
	public int get_wake_up_mood() {
		return wake_up_mood;
	}
	
	public int get_enough_sleep() {
		return enough_sleep;
	}
	
	public int getId() {
		return id;
	}

	// SETTERS
	public void set_sleep_quality(int sleep_quality) {
		this.sleep_quality = sleep_quality;
	}

	public void set_wake_up_mood(int wake_up_mood) {
		this.wake_up_mood = wake_up_mood;
	}

	public void set_enough_sleep(int enough_sleep) {
		this.enough_sleep = enough_sleep;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
