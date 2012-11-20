package com.mume12.happy.handlers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mume12.happy.storage.EmotionStorage;
import com.mume12.happy.storage.EmotionStorageHandler;
import com.mume12.happy.storage.SleepEmotionStorage;
import com.mume12.happy.storage.SleepTimeStorage;
import com.mume12.happy.storage.TimeStorage;
import com.mume12.happy.storage.TimeStorageHandler;

public class SleepTip {

	private final static double sleepMoodFac = 0.4;
	private final static double socQual = 0.3;
	private final static double socQuan = 0.1;
	private final static double stressWork = 0.3;
	private final static double stressNonWork = 0.3;
	private final static double recHours = 0.1;
	private final static double enoughRec = 0.4;
	private final static double alcohol = 0.2;
	private final static double caffeine = 0.2;

	private final static int goodLimit = 7;

	public static void findTip(Context context,
			SleepEmotionStorage slEmotionStorage, SleepTimeStorage slTimeStorage) {
		double factorCurrentEvening = calcFactor(slEmotionStorage);
		Log.i("SLEEPTIP", "Factor current evening: " + factorCurrentEvening);

		// Limits
		double lower = factorCurrentEvening - 1.0;
		double higher = factorCurrentEvening + 1.0;

		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(context);
		
		TimeStorageHandler timeStorageHandler = TimeStorageHandler
				.getInstance(context);

		// All items from database
		List<EmotionStorage> emotionStorageList = new ArrayList<EmotionStorage>();
		emotionStorageList = emotionHandler.getAllEmotionStorage();

		// List to store EmotionStorage objects with correct factors
		List<EmotionStorage> goodFactors = new ArrayList<EmotionStorage>();
		
		// List to store TimeStorage object
		List<TimeStorage> times = new ArrayList<TimeStorage>();

		for (EmotionStorage em : emotionStorageList) {
			double fac = calcFactor(em);

			// Inside limits
			if ((fac <= higher) && (fac >= lower)) {
				// Wake up mood or sleep qual good (greater than certain limit)
				if ((em.get_wake_up_mood() >= goodLimit)
						|| (em.get_sleep_quality() >= goodLimit)) {
					goodFactors.add(em);
					// Add times for this object
					times.add(timeStorageHandler.getTimeDataStorage(em.getId()));
					Log.i("SLEEPTIP", "ID: " + em.getId() + " | Factor: " + fac
							+ " | Added");
				}
			}
			Log.i("SLEEPTIP", "ID: " + em.getId() + " | Factor: " + fac);
		}

		/**
		 * Calculate average sleep time from EmotionStorage objects in list and
		 * tell the user how long he/she should sleep
		 */
		if(!times.isEmpty()){
			long averageGoodSleep = TimeCalculations.averageSleep(times);
			
			String recommendSleepTime = TimeCalculations.convertMillis(averageGoodSleep);
			
			Log.i("SLEEPTIP", "You should sleep this long: " + recommendSleepTime);
			
			Toast.makeText(context, "According to previous data you should sleep this long: " + recommendSleepTime,
					Toast.LENGTH_LONG).show();
		}
	}

	public static double calcFactor(EmotionStorage slEmSt) {
		double factor = slEmSt.get_mood_sleep() * sleepMoodFac
				+ slEmSt.get_soc_contact_quality() * socQual
				+ slEmSt.get_soc_contact_quantity() * socQuan
				+ slEmSt.get_stress_work() * stressWork
				+ slEmSt.get_stress_non_work() * stressNonWork
				+ slEmSt.get_rec_hours() * recHours + slEmSt.get_rec_enough()
				* enoughRec + slEmSt.get_alcohol() * alcohol
				+ slEmSt.get_caffeine() * caffeine;

		return factor;
	}

}
