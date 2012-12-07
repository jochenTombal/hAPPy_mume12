

package com.mume12.happy.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mume12.happy.R;
import com.mume12.happy.handlers.TimeCalculations;
import com.mume12.happy.storage.TimeStorage;
import com.mume12.happy.storage.TimeStorageHandler;

public class CalendarActivity extends Activity  implements CalendarView.OnCellTouchListener{
	public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
	CalendarView mView = null;
	TextView mHit;
	Handler mHandler = new Handler();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        mView = (CalendarView)findViewById(R.id.calendar);
        mView.setOnCellTouchListener(this);
        
        if(getIntent().getAction().equals(Intent.ACTION_PICK))
        	findViewById(R.id.hint).setVisibility(View.INVISIBLE);
    }

	public void onTouch(Cell cell) {
		Intent intent = getIntent();
		String action = intent.getAction();
		if(action.equals(Intent.ACTION_PICK) || action.equals(Intent.ACTION_GET_CONTENT)) {
			int year  = mView.getYear();
			int month = mView.getMonth();
			int day   = cell.getDayOfMonth();
			
	//TODO data uithalen en weergeven
			
			final Calendar dat = Calendar.getInstance();
	        dat.set(Calendar.YEAR, year);
	        dat.set(Calendar.MONTH, month);
	        dat.set(Calendar.DAY_OF_MONTH, day);
	        
			List<String> listItems = new ArrayList<String>();

			TimeStorageHandler tsHandler = TimeStorageHandler.getInstance(this);
			List<TimeStorage> timeStorageList = new ArrayList<TimeStorage>();
			
			timeStorageList = tsHandler.getAllTimeStorage();
			for (TimeStorage sl : timeStorageList) {
				
				if(sl.getStartDate() == dat.getTimeInMillis()){
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
				
			}
			
			//TODO
			
			// FIX issue 6: make some correction on month and year
			if(cell instanceof CalendarView.GrayCell) {
				// oops, not pick current month...				
				if (day < 15) {
					// pick one beginning day? then a next month day
					if(month==11)
					{
						month = 0;
						year++;
					} else {
						month++;
					}
					
				} else {
					// otherwise, previous month
					if(month==0) {
						month = 11;
						year--;
					} else {
						month--;
					}
				}
			}
		

			
			Intent ret = new Intent();
			ret.putExtra("year", year);
			ret.putExtra("month", month);
			ret.putExtra("day", day);
			this.setResult(RESULT_OK, ret);
			finish();
			return;
		}
		int day = cell.getDayOfMonth();
		if(mView.firstDay(day))
			mView.previousMonth();
		else if(mView.lastDay(day))
			mView.nextMonth();
		else
			return;

		mHandler.post(new Runnable() {
			public void run() {
				Toast.makeText(CalendarActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		}
		
		
		//final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//builder.setMessage(pen).setNegativeButton("Close", null).show();
		
//		final AlertDialog.Builder builder = new AlertDialog.Builder(
//				v.getContext());
//		builder.setMessage(pen).setNegativeButton("Close", null).show();
		
	//}
	
//	public boolean onTouchEvent (MotionEvent event){
//		Toast toast = Toast.makeText(CalendarActivity.this, "pick a date", Toast.LENGTH_SHORT);
//		toast.setDuration(3);
//		toast.show();
//		return true;
//	}
	
	

    
}