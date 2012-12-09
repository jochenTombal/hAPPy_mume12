package com.mume12.happy.results;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mume12.happy.R;
import com.mume12.happy.handlers.TimeCalculations;
import com.mume12.happy.storage.TimeStorage;
import com.mume12.happy.storage.TimeStorageHandler;

public class ShowDayResultActivity extends ListActivity {

	private ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview);
		Intent intent = getIntent();
		int year = intent.getIntExtra("year", 0);   // get number of year
        int month = intent.getIntExtra("month", 0); // get number of month 0..11
        int day = intent.getIntExtra("day", 0);     // get number of day 0..31
        showDateListItems(year, month, day);
		
			
	}

	@SuppressWarnings("deprecation")
	public void showDateListItems(int year, int month, int day) {
			
			final Calendar dat = Calendar.getInstance();
	        dat.set(Calendar.YEAR, year);
	        dat.set(Calendar.MONTH, month);
	        dat.set(Calendar.DAY_OF_MONTH, day);
	        
			List<String> listItems = new ArrayList<String>();

			TimeStorageHandler tsHandler = TimeStorageHandler.getInstance(this);
			List<TimeStorage> timeStorageList = new ArrayList<TimeStorage>();
			
			timeStorageList = tsHandler.getAllTimeStorage();
			
			Calendar dat2 = Calendar.getInstance();
			
			for (TimeStorage sl : timeStorageList) {
				dat2.setTimeInMillis(sl.getStartDate());
				
				if(dat.getTime().getYear()==dat2.getTime().getYear() 
						&& dat.getTime().getMonth()==dat2.getTime().getMonth() 
						&& dat.getTime().getDay()==dat2.getTime().getDay() ){
					int id = sl.getId();

					// Get start and enddate (both milliseconds)
					long startDate = sl.getStartDate();
					long endDate = sl.getEndDate();

					// Convert milliseconds to Date and store as string
					Date begin = new Date(startDate);
					String begindate = begin.toLocaleString();

					Date end = new Date(endDate);
					String enddate = end.toLocaleString();

					// Calculate difference
					String difftime = TimeCalculations.timeDifference(sl);

					String showInList = "ID item: " + (Integer.toString(id)) + "\n"
							+ "Went to bed: " + begindate + "\n" + "Got out of bed: "
							+ enddate + "\n" + "Slept: " + difftime;
					
					listItems.add(showInList);
					
				}
				
			}
			long averageSleep = TimeCalculations.averageSleep(timeStorageList);

			String averageSleepString = "Average time in bed: "
					+ TimeCalculations.convertMillis(averageSleep);

			listItems.add(averageSleepString);

			// Show listitems
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, listItems);
			setListAdapter(adapter);           
	    
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String pen = o.toString();

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				v.getContext());
		builder.setMessage(pen).setNegativeButton("Close", null).show();
	}

}
