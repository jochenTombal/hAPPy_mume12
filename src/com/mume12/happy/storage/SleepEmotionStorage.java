package com.mume12.happy.storage;

import android.content.Context;

public class SleepEmotionStorage extends EmotionStorage {
	private int mood_sleep, soc_contact_quality, soc_contact_quantity,  stressWork ,stressNonWork;
	private double rec_hours;
	private int id;

	private int rec_enough, alcohol, caffeine;
	
	public SleepEmotionStorage(){}

	public SleepEmotionStorage(int _mood_sleep,
			int _soc_contact_quality, int soc_contact_quantity, int _stressWork, int _stressNonWork,
			int _rec_hours, boolean _rec_enough, boolean _alcohol, boolean _caffeine) {
		this.mood_sleep = _mood_sleep;
		this.soc_contact_quality = _soc_contact_quality;
		this.soc_contact_quantity = soc_contact_quantity;
		this.stressWork = _stressWork;
		this.stressNonWork = _stressNonWork;
		this.rec_hours = _rec_hours;
		
		if(_alcohol){
			this.alcohol = 1;
		} else{
			this.alcohol = 0;
		}
		
		if(_caffeine){
			this.caffeine = 1;
		} else{
			this.caffeine = 0;
		}
		
		if(_rec_enough){
			this.rec_enough = 1;
		} else{
			this.rec_enough = 0;
		}
	}

	public SleepEmotionStorage(int _id, int _mood_sleep,
			int _soc_contact_quality, int soc_contact_quantity, int _stressWork, int _stressNonWork,
			double _rec_hours, boolean _rec_enough, boolean _alcohol, boolean _caffeine) {
		this.id = _id;
		this.mood_sleep = _mood_sleep;
		this.soc_contact_quality = _soc_contact_quality;
		this.soc_contact_quantity = soc_contact_quantity;
		this.stressWork = _stressWork;
		this.stressNonWork = _stressNonWork;
		this.rec_hours = _rec_hours;
		
		if(_alcohol){
			this.alcohol = 1;
		} else{
			this.alcohol = 0;
		}
		
		if(_caffeine){
			this.caffeine = 1;
		} else{
			this.caffeine = 0;
		}
		
		if(_rec_enough){
			this.rec_enough = 1;
		} else{
			this.rec_enough = 0;
		}
	}
	
	// SAVE
	public void storeDB(Context context, long rowID){
		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(context);
		emotionHandler.addEmotionStorage(this, rowID);
	}

	// GETTERS
	public int get_mood_sleep() {
		return mood_sleep;
	}

	public int get_soc_contact_quality() {
		return soc_contact_quality;
	}

	public int get_soc_contact_quantity() {
		return soc_contact_quantity;
	}

	public int get_stress_work() {
		return stressWork;
	}
	
	public int get_stress_non_work() {
		return stressNonWork;
	}

	public double get_rec_hours() {
		return rec_hours;
	}

	public int get_rec_enough() {
		return rec_enough;
	}

	public int getId() {
		return id;
	}

	// SETTERS
	public void set_mood_sleep(int mood_sleep) {
		this.mood_sleep = mood_sleep;
	}

	public void set_soc_contact_quality(int soc_contact_quality) {
		this.soc_contact_quality = soc_contact_quality;
	}

	public void set_soc_contact_quantity(int soc_contact_quantity) {
		this.soc_contact_quantity = soc_contact_quantity;
	}

	public void set_stress_work(int stress) {
		this.stressWork = stress;
	}
	
	public void set_stress_non_work(int stress) {
		this.stressNonWork = stress;
	}

	public void set_rec_hours(double rec_hours) {
		this.rec_hours = rec_hours;
	}

	public void set_rec_enough(int rec_enough) {
		this.rec_enough = rec_enough;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int get_alcohol() {
		return alcohol;
	}

	public void set_alcohol(int alcohol) {
		this.alcohol = alcohol;
	}

	public int get_caffeine() {
		return caffeine;
	}

	public void set_caffeine(int caffeine) {
		this.caffeine = caffeine;
	}

}
