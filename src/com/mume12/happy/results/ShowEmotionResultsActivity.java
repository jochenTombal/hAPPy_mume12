package com.mume12.happy.results;

import java.util.ArrayList;
import java.util.List;

import com.mume12.happy.R;
import com.mume12.happy.storage.EmotionStorage;
import com.mume12.happy.storage.EmotionStorageHandler;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowEmotionResultsActivity extends ListActivity {

	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview);

		showListItems();
	}
	
	protected void showListItems() {
		// Handler to get all emotion storage from database
		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(this);
		
		List<String> listItems = new ArrayList<String>();

		List<EmotionStorage> emotionStorageList = new ArrayList<EmotionStorage>();

		// All items from database
		emotionStorageList = emotionHandler.getAllEmotionStorage();

		// Get Time and Date from SleepStorage and add in this list
		for (EmotionStorage em : emotionStorageList) {
			int id = em.getId();

			int sleep_mood = em.get_mood_sleep();
			int con_qual = em.get_soc_contact_quality();
			int con_quan = em.get_soc_contact_quantity();
			int stressWork = em.get_stress_work();
			int stressNonWork = em.get_stress_non_work();
			int rec_hours = em.get_rec_hours();
			int enough_rec = em.get_rec_enough();
			
			int alcohol = em.get_alcohol();
			int caffeine = em.get_caffeine();
			
			int sleep_qual = em.get_sleep_quality();
			int enough_sleep = em.get_enough_sleep();
			int wake_up_mood = em.get_wake_up_mood();

			String listItem = "id: " + id + "\n" 
					+ "sleep mood: " + sleep_mood + "\n" 
					+ "social contact quality: " + con_qual + "\n"
					+ "social contact quantity: " + con_quan + "\n"
					+ "stress work: " + stressWork + "\n" 
					+ "stress nonwork: " + stressNonWork + "\n" 
					+ "recreational hours: " + rec_hours + "\n" 
					+ "enough recreation: " + enough_rec + "\n"
					+ "alcohol: " + alcohol + "\n"
					+ "caffeine: " + caffeine + "\n"
					+ "sleep quality: " + sleep_qual + "\n"
					+ "enough sleep: " + enough_sleep + "\n" 
					+ "wake up mood: " + wake_up_mood + "\n";

			listItems.add(listItem);
		}

		// Show listitems
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
	}
	
	// Show emotions from specific row
	protected void showID(int ID){
		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(this);
		
		EmotionStorage em = emotionHandler.getEmotionStorage(2);
		
		int id = em.getId();

		int sleep_mood = em.get_mood_sleep();
		int con_qual = em.get_soc_contact_quality();
		int con_quan = em.get_soc_contact_quantity();
		int stressWork = em.get_stress_work();
		int stressNonWork = em.get_stress_non_work();
		int rec_hours = em.get_rec_hours();
		int enough_rec = em.get_rec_enough();
		
		int alcohol = em.get_alcohol();
		int caffeine = em.get_caffeine();
		
		int sleep_qual = em.get_sleep_quality();
		int enough_sleep = em.get_enough_sleep();
		int wake_up_mood = em.get_wake_up_mood();

		String listItem = "id: " + id + "\n" 
				+ "sleep mood: " + sleep_mood + "\n" 
				+ "social contact quality: " + con_qual + "\n"
				+ "social contact quantity: " + con_quan + "\n"
				+ "stress work: " + stressWork + "\n" 
				+ "stress nonwork: " + stressNonWork + "\n" 
				+ "recreational hours: " + rec_hours + "\n" 
				+ "enough recreation: " + enough_rec + "\n"
				+ "alcohol: " + alcohol + "\n"
				+ "caffeine: " + caffeine + "\n"
				+ "sleep quality: " + sleep_qual + "\n"
				+ "enough sleep: " + enough_sleep + "\n" 
				+ "wake up mood: " + wake_up_mood + "\n";
		
		List<String> listItems = new ArrayList<String>();
		listItems.add(listItem);
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String pen = o.toString();

		final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setMessage(pen).setNegativeButton("Close", null).show();
	}

}
