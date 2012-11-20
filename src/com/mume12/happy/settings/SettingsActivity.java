package com.mume12.happy.settings;

import java.util.ArrayList;
import java.util.List;

import com.mume12.happy.R;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends ListActivity {

	// String adapter will handle data for listview
	private ArrayAdapter<String> adapter;

	private List<String> settings = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview);

		showListItems();
	}

	public void showListItems() {
		// Add new settings here (and in string.xml add a name)
		// Also add a new activity in the settings package
		// the correct listitem is clicked. This
		// Activity will handle the specific setting
		// TODO Create settings with preferencescreens
		settings.add(getString(R.string.settings_accelero));
		settings.add(getString(R.string.settings_reminder));

		// Show listitems
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, settings);
		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String pen = o.toString();

		if (pen.equals(getString(R.string.settings_accelero))) {
			Intent intent = new Intent(this, AcceleroSettingsActivity.class);
			startActivity(intent);
		} else if (pen.equals(getString(R.string.settings_reminder))) {
			Intent intent = new Intent(this, ReminderSettingActivity.class);
			startActivity(intent);
		}
	}
}
