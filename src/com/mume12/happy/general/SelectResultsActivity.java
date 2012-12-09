package com.mume12.happy.general;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mume12.happy.R;
import com.mume12.happy.calendar.CalendarActivity;
import com.mume12.happy.charts.MoodPieGTS;
import com.mume12.happy.charts.MoodPieWU;
import com.mume12.happy.charts.MoodVariationsActivity;
import com.mume12.happy.results.ShowEmotionResultsActivity;
import com.mume12.happy.results.ShowTimeResultsActivity;
import com.mume12.happy.handlers.TimeCalculations;
import com.mume12.happy.storage.TimeStorage;
import com.mume12.happy.storage.TimeStorageHandler;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectResultsActivity extends ListActivity {

	// Adapter which will handle data for listview
	private ArrayAdapter<String> adapter;

	private List<String> items = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview);

		// All options
		items.add(getString(R.string.show_time_results));
		items.add(getString(R.string.show_emotion_results));
		items.add("Going to sleep MoodPie");
		items.add("Waking up MoodPie");
		items.add("Mood variations");
		items.add(getString(R.string.calendar_name));

		// Show different result options
		showListItems();
	}

	protected void showListItems() {
		// Show listitems
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String pen = o.toString();

		if (pen.equals(items.get(0))) {
			// Start time results
			Intent intent = new Intent(this, ShowTimeResultsActivity.class);
			startActivity(intent);
		} else if (pen.equals(items.get(1))) {
			// Start emotion results
			Intent intent = new Intent(this, ShowEmotionResultsActivity.class);
			startActivity(intent);
		} else if (pen.equals(items.get(2))) {
			Intent pieChartIntent = new MoodPieGTS().execute(this);
			startActivity(pieChartIntent);
		} else if (pen.equals(items.get(3))) {
			Intent pieChartIntent = new MoodPieWU().execute(this);
			startActivity(pieChartIntent);
		} else if (pen.equals(items.get(4))) {
			// Intent barChartIntent = new MoodBars().execute(this);
			Intent barChartIntent = new Intent(this,
					MoodVariationsActivity.class);
			startActivity(barChartIntent);
		}else if (pen.equals(items.get(5))){
			startActivityForResult(new Intent(Intent.ACTION_PICK).setDataAndType(null, CalendarActivity.MIME_TYPE), 100);
		} else {
			Log.i("WRONG_LISTITEM", "unknown listitem clicked");
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onactivityresult");
	    if(resultCode==RESULT_OK) {
	        int year = data.getIntExtra("year", 0);   // get number of year
	        int month = data.getIntExtra("month", 0); // get number of month 0..11
	        int day = data.getIntExtra("day", 0);     // get number of day 0..31

//TODO data uithalen en weergeven
			
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
				
				if(dat.getTime().getYear()==dat2.getTime().getYear() && dat.getTime().getMonth()==dat2.getTime().getMonth() &&dat.getTime().getDay()==dat2.getTime().getDay()){
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
				
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				
				String message;
				
			    StringBuilder sb = new StringBuilder();

				for (int i = 0; i < listItems.size(); i++) {
		            sb.append( listItems.get(i) );
		           
				}
				
	            sb.append( averageSleepString );
				message = sb.toString();
				builder.setMessage(message).setNegativeButton("Close", null).show();
				
			
			
			//TODO
	                
	    }
	}
	
	
	

}
