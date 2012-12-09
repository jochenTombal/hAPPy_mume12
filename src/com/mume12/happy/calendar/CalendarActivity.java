

package com.mume12.happy.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mume12.happy.R;
import com.mume12.happy.results.ShowDayResultActivity;

public class CalendarActivity extends Activity  implements CalendarView.OnCellTouchListener{
	public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.com.mume12.happy.calendar.date";
	CalendarView mView = null;
	TextView mHit;
	Handler mHandler = new Handler();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
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
		

			
			Intent ret = new Intent(this, ShowDayResultActivity.class);
			ret.putExtra("year", year);
			ret.putExtra("month", month);
			ret.putExtra("day", day);
			
			startActivity(ret);
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