package com.mume12.happy.storage;

public class EmotionStorage {

	private int sleep_quality, wake_up_mood, mood_sleep, soc_contact_quality,
			soc_contact_quantity, stressWork,stressNonWork, rec_hours;
	private int id;

	private int enough_sleep, rec_enough, alcohol, caffeine;
	
	public EmotionStorage(){}
	
	public EmotionStorage(int _mood_sleep,
			int _soc_contact_quality, int soc_contact_quantity, int _stressWork, int _stressNonWork,
			int _rec_hours, boolean _rec_enough, int _wake_up_mood, int _sleep_quality, boolean _enough_sleep, boolean _alcohol, boolean _caffeine) {
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
		
		this.wake_up_mood = _wake_up_mood;
		
		this.sleep_quality = _sleep_quality;
		
		if(_enough_sleep){ // true
			this.enough_sleep = 1;
		} else{
			this.enough_sleep = 0;
		}
	}

	public EmotionStorage(int _id, int _mood_sleep,
			int _soc_contact_quality, int soc_contact_quantity, int _stressWork, int _stressNonWork,
			int _rec_hours, boolean _rec_enough, int _wake_up_mood, int _sleep_quality, boolean _enough_sleep,  boolean _alcohol, boolean _caffeine) {
		this.id = _id;
		this.mood_sleep = _mood_sleep;
		this.soc_contact_quality = _soc_contact_quality;
		this.soc_contact_quantity = soc_contact_quantity;
		this.stressWork = _stressWork;
		this.stressNonWork = _stressNonWork;
		this.rec_hours = _rec_hours;
		
		if(_alcohol){ // true
			this.alcohol = 1;
		} else{
			this.alcohol = 0;
		}
		
		if(_caffeine){ // true
			this.caffeine = 1;
		} else{
			this.caffeine = 0;
		}
		
		if(_rec_enough){ // true
			this.rec_enough = 1;
		} else{
			this.rec_enough = 0;
		}
		
		this.wake_up_mood = _wake_up_mood;
		
		this.sleep_quality = _sleep_quality;
		
		if(_enough_sleep){ // true
			this.enough_sleep = 1;
		} else{
			this.enough_sleep = 0;
		}
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
	
	public int get_rec_hours() {
		return rec_hours;
	}
	
	public int get_rec_enough() {
		return rec_enough;
	}
	
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

	public void set_rec_hours(int rec_hours) {
		this.rec_hours = rec_hours;
	}	

	public void set_rec_enough(int rec_enough) {
		this.rec_enough = rec_enough;
	}
	
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
