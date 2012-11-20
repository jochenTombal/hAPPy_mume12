package com.mume12.happy.charts;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.mume12.happy.R;
import com.mume12.happy.general.SeekBarLabelColors;
import com.mume12.happy.storage.EmotionStorage;
import com.mume12.happy.storage.EmotionStorageHandler;

public class MoodPieWU {

	private final String title = "Moods at waking up";

	public Intent execute(Context context) {
		int[] colors = SeekBarLabelColors.getColorsRedToGreen();
		DefaultRenderer renderer = buildCategoryRenderer(colors);

		// Get mood labels
		String[] moods = getMoods(context);

		// Retrieve from database how many times a certain mood occurs
		int[] moodStats = getMoodStats(context);

		CategorySeries categorySeries = new CategorySeries(title);
		categorySeries.add(moods[0], moodStats[0]);
		categorySeries.add(moods[1], moodStats[1]);
		categorySeries.add(moods[2], moodStats[2]);
		categorySeries.add(moods[3], moodStats[3]);
		categorySeries.add(moods[4], moodStats[4]);

		return ChartFactory.getPieChartIntent(context, categorySeries,
				renderer, null);
	}

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		// Title
		renderer.setChartTitle(title);
		renderer.setChartTitleTextSize(40.0f);
		
		// Legend
		renderer.setLegendTextSize(20.0f);

		// Background
		renderer.setBackgroundColor(Color.rgb(176, 224, 230));
		renderer.setApplyBackgroundColor(true);

		// Labels
		renderer.setLabelsColor(Color.BLACK);
		renderer.setLabelsTextSize(15.0f);
		return renderer;
	}

	protected String[] getMoods(Context context) {
		String[] moods = new String[] {
				context.getString(R.string.seekbarQual0),
				context.getString(R.string.seekbarQual1),
				context.getString(R.string.seekbarQual2),
				context.getString(R.string.seekbarQual3),
				context.getString(R.string.seekbarQual4) };

		return moods;
	}

	protected int[] getMoodStats(Context context) {
		// Array to count how many times a mood occurs
		int[] moods = new int[] { 0, 0, 0, 0, 0 };

		// Handler to get all emotion storage from database
		EmotionStorageHandler emotionHandler = EmotionStorageHandler
				.getInstance(context);

		List<EmotionStorage> emotionStorageList = new ArrayList<EmotionStorage>();

		// All items from database
		emotionStorageList = emotionHandler.getAllEmotionStorage();

		for (EmotionStorage em : emotionStorageList) {
			int wakeup_mood = em.get_wake_up_mood();

			if (wakeup_mood <= moods.length) {
				moods[wakeup_mood]++;
			}
		}

		return moods;
	}
}
