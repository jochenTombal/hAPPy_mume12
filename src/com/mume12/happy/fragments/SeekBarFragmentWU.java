package com.mume12.happy.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mume12.happy.R;
import com.mume12.happy.general.SeekBarLabelColors;
import com.mume12.happy.general.SeekBarQualityListener;

public class SeekBarFragmentWU extends Fragment {

	private View mView;

	private Activity act;

	private String initLabel;

	public static SeekBar wakingUpMood, wakingUpsleepQuality;

	private TextView moodLabel, sleepQualLabel;

	private final int MaxSlider = 4;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_seekbars_wu, container,
				false);
		this.mView = view;
		this.act = getActivity();

		// Always check if activity is not null
		if (act != null) {
			initializeLabels();
			initializeSliders();
		}

		return view;
	}

	private void initializeLabels() {
		initLabel = getString(R.string.seekbarLabelInitValue);
		
		// Initialize moodlabel
		moodLabel = (TextView) mView.findViewById(R.id.textViewMoodLabelWU);
		moodLabel.setText(initLabel);
		moodLabel.setTextColor(Color.GRAY);

		// Initialize sleep quality label
		sleepQualLabel = (TextView) mView
				.findViewById(R.id.textViewSleepQualLabelWU);
		sleepQualLabel.setText(initLabel);
		sleepQualLabel.setTextColor(Color.GRAY);
	}

	private void initializeSliders() {
		// Initialize sliders
		wakingUpMood = (SeekBar) mView.findViewById(R.id.seekBarWakingUpMoodWU);
		wakingUpsleepQuality = (SeekBar) mView
				.findViewById(R.id.seekBarWakingUpSleepQualityWU);

		// Set max value for sliders
		wakingUpMood.setMax(MaxSlider);
		wakingUpsleepQuality.setMax(MaxSlider);

		// Set progress to half of max
		wakingUpMood.setProgress(MaxSlider / 2);
		wakingUpsleepQuality.setProgress(MaxSlider / 2);
		
		// Set listeners
		setListeners();
	}

	private void setListeners() {
		// Create new listeners for seekbars
		wakingUpMood.setOnSeekBarChangeListener(new SeekBarQualityListener(act,
				moodLabel, SeekBarLabelColors.getColorsRedToGreen()));

		wakingUpsleepQuality
				.setOnSeekBarChangeListener(new SeekBarQualityListener(act,
						sleepQualLabel, SeekBarLabelColors
								.getColorsRedToGreen()));
	}
}
