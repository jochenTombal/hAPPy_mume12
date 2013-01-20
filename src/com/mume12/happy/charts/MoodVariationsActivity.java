package com.mume12.happy.charts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.mume12.happy.R;
import com.mume12.happy.storage.EmotionStorage;
import com.mume12.happy.storage.EmotionStorageHandler;
import com.mume12.happy.storage.TimeStorage;
import com.mume12.happy.storage.TimeStorageHandler;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;

public class MoodVariationsActivity extends Activity {

	private List<Integer> moodsGTS = new ArrayList<Integer>();
	private List<Integer> moodsWU = new ArrayList<Integer>();

	// private String title = "Mood variations";

	private int color_gts = Color.GRAY;
	private int color_wu = Color.BLUE;

	private int color_xy_labels = Color.WHITE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(getView());
	}

	public View getView() {
		// Get all moods and put them in arraylist, which are converted to
		// arrays after this
		getMoods();

		Integer[] arrayMoodsGTS = moodsGTS
				.toArray(new Integer[moodsGTS.size()]);
		Integer[] arrayMoodsWU = moodsWU.toArray(new Integer[moodsWU.size()]);

		// Get dates from database
		String[] arrayDates = getDates();

		XYMultipleSeriesRenderer renderer = getBarRenderer(arrayDates);
		setChartSettings(renderer, arrayDates.length);

		View view = ChartFactory
				.getBarChartView(this, getBarData(arrayMoodsGTS, arrayMoodsWU),
						renderer, Type.DEFAULT);
		return view;
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer, int length) {
		// Title
		// renderer.setChartTitle(title);
		// renderer.setChartTitleTextSize(40.0f);

		// Axis labels
		renderer.setXTitle("Date");
		renderer.setYTitle("Moods");

		// Set boundaries
		renderer.setXAxisMin(0);

		renderer.setYAxisMin(0);
		renderer.setYAxisMax(4);

		// Disable pan Y direction
		renderer.setPanEnabled(true, true);
		// +5 for extra margin
		renderer.setPanLimits(new double[] { 0, length + 5, 0, 4 });
		renderer.setZoomEnabled(true, false);

		// Legend
		renderer.setLegendTextSize(20.0f);

		// Background
		renderer.setBackgroundColor(Color.rgb(176, 224, 230));
		renderer.setApplyBackgroundColor(true);
	}

	private XYMultipleSeriesDataset getBarData(Integer[] sleepMoodsArray,
			Integer[] wakeupMoodsArray) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		CategorySeries sleepMoods = new CategorySeries("Going to sleep moods");
		for (int i = 0; i < sleepMoodsArray.length; i++) {
			sleepMoods.add((double) sleepMoodsArray[i]);
		}

		CategorySeries wakeupMoods = new CategorySeries("Waking up moods");
		for (int i = 0; i < wakeupMoodsArray.length; i++) {
			wakeupMoods.add((double) wakeupMoodsArray[i]);
		}

		dataset.addSeries(sleepMoods.toXYSeries());
		dataset.addSeries(wakeupMoods.toXYSeries());

		return dataset;
	}

	public XYMultipleSeriesRenderer getBarRenderer(String[] dates) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(30);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setBarSpacing(0.2);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });

		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(color_gts);
		renderer.addSeriesRenderer(r);

		r = new SimpleSeriesRenderer();
		r.setColor(color_wu);
		renderer.addSeriesRenderer(r);

		// Set dates on X-axis
		renderer.setXLabels(0);
		for (int i = 0; i < dates.length; i++) {

			renderer.addXTextLabel(i + 1, dates[i]);
		}

		// Moods on Y-axis
		renderer.setYLabels(0);
		renderer.addYTextLabel(0, getString(R.string.seekbarQual0));
		renderer.addYTextLabel(1, getString(R.string.seekbarQual1));
		renderer.addYTextLabel(2, getString(R.string.seekbarQual2));
		renderer.addYTextLabel(3, getString(R.string.seekbarQual3));
		renderer.addYTextLabel(4, getString(R.string.seekbarQual4));

		// Set label colors
		renderer.setXLabelsColor(color_xy_labels);
		renderer.setYLabelsColor(0, color_xy_labels);

		return renderer;
	}

	protected void getMoods() {
		// Handler to get all emotion storage from database
		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(this);

		List<EmotionStorage> emotionStorageList = new ArrayList<EmotionStorage>();

		// All items from database
		emotionStorageList = emotionHandler.getAllEmotionStorage();

		for (EmotionStorage em : emotionStorageList) {
			int sleep_mood = em.get_mood_sleep();
			moodsGTS.add(sleep_mood);

			int wakeup_mood = em.get_wake_up_mood();
			moodsWU.add(wakeup_mood);
		}
	}

	protected String[] getDates() {
		TimeStorageHandler timeStHandler = TimeStorageHandler.getInstance(this);

		List<TimeStorage> timeStorageList = new ArrayList<TimeStorage>();

		timeStorageList = timeStHandler.getAllTimeStorage();

		String[] dates = new String[timeStorageList.size()];

		int count = 0;

		// long one_day = 24 * 60 * 60 * 1000; // For testing

		for (TimeStorage timeSt : timeStorageList) {
			long date = timeSt.getStartDate();

			// date += (one_day * count); // For testing

			String date_string = new SimpleDateFormat("yyyy-MM-dd")
					.format(date);

			dates[count] = date_string;

			count++;
		}
		return dates;
	}
}
