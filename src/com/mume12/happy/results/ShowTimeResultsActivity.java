package com.mume12.happy.results;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mume12.happy.R;
import com.mume12.happy.handlers.TimeCalculations;
import com.mume12.happy.storage.TimeStorage;
import com.mume12.happy.storage.TimeStorageHandler;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowTimeResultsActivity extends ListActivity {

	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview);
		showListItems(Calendar dat);
	}

	// Show date and time
	public void showListItems(Calendar dat) {
		List<String> listItems = new ArrayList<String>();

		TimeStorageHandler tsHandler = TimeStorageHandler.getInstance(this);

		List<TimeStorage> timeStorageList = new ArrayList<TimeStorage>();

		// All items from database
		timeStorageList = tsHandler.getAllTimeStorage();

		// Get Time and Date from SleepStorage and add in this list
		for (TimeStorage sl : timeStorageList) {
			int id = sl.getId();

			// Convert milliseconds to Date and store as string
			String begindate = dat.getTime().toLocaleString();
			
			Date end = dat.getTime();
			end.setDate(dat.getTime().getDate()+1);
			String enddate = end.toLocaleString();

			// Calculate difference
			String difftime = TimeCalculations.timeDifference(sl);

			String showInList = "ID item: " + (Integer.toString(id)) + "\n"
					+ "Went to bed: " + begindate + "\n" + "Got out of bed: "
					+ enddate + "\n" + "Slept: " + difftime;

			listItems.add(showInList);
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
