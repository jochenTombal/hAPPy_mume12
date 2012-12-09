package com.mume12.happy.general;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mume12.happy.R;
import com.mume12.happy.calendar.CalendarActivity;
import com.mume12.happy.charts.MoodPieGTS;
import com.mume12.happy.charts.MoodPieWU;
import com.mume12.happy.charts.MoodVariationsActivity;
import com.mume12.happy.results.ShowEmotionResultsActivity;
import com.mume12.happy.results.ShowTimeResultsActivity;

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
			startActivity(new Intent(Intent.ACTION_PICK).setDataAndType(null, CalendarActivity.MIME_TYPE));
		} else {
			Log.i("WRONG_LISTITEM", "unknown listitem clicked");
		}
	}
	

}
