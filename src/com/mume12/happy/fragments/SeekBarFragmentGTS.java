package com.mume12.happy.fragments;

import com.mume12.happy.R;
import com.mume12.happy.general.SeekBarLabelColors;
import com.mume12.happy.general.SeekBarQualityListener;
import com.mume12.happy.general.SeekBarQuantityListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarFragmentGTS extends Fragment {

	private TextView moodLabel, socQualLabel, socQuanLabel, stressWorkLabel,
			stressNonWorkLabel;

	public static SeekBar mood, socialQuality, socialQuantity, stressWork,
			stressNonWork;

	private View mView;

	private final int MaxSlider = 4;

	private Activity act;

	private String initLabel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_seekbars_gts, container,
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
		initLabel = act.getString(R.string.seekbarLabelInitValue);
		
		// Initialize moodlabel
		moodLabel = (TextView) mView.findViewById(R.id.textViewMoodLabelGTS);
		moodLabel.setText(initLabel);
		moodLabel.setTextColor(Color.GRAY);

		// Initialize social quality label
		socQualLabel = (TextView) mView
				.findViewById(R.id.textViewSocQualLabelGTS);
		socQualLabel.setText(initLabel);
		socQualLabel.setTextColor(Color.GRAY);

		// Initialize social quantity label
		socQuanLabel = (TextView) mView
				.findViewById(R.id.textViewSocQuanLabelGTS);
		socQuanLabel.setText(initLabel);
		socQuanLabel.setTextColor(Color.GRAY);

		// Initialize stress work label
		stressWorkLabel = (TextView) mView
				.findViewById(R.id.textViewStressWorkLabelGTS);
		stressWorkLabel.setText(initLabel);
		stressWorkLabel.setTextColor(Color.GRAY);

		// Initialize stress non work label
		stressNonWorkLabel = (TextView) mView
				.findViewById(R.id.textViewStressNonWorkLabelGTS);
		stressNonWorkLabel.setText(initLabel);
		stressNonWorkLabel.setTextColor(Color.GRAY);
	}

	private void initializeSliders() {

		// Initialize sliders
		mood = (SeekBar) mView.findViewById(R.id.seekBarMoodGTS);
		socialQuality = (SeekBar) mView.findViewById(R.id.seekBarQualityGTS);
		socialQuantity = (SeekBar) mView.findViewById(R.id.seekBarQuantityGTS);
		stressWork = (SeekBar) mView.findViewById(R.id.seekBarStressWorkGTS);
		stressNonWork = (SeekBar) mView
				.findViewById(R.id.seekBarStressNonWorkGTS);

		// Set max value for sliders
		mood.setMax(MaxSlider);
		socialQuality.setMax(MaxSlider);
		socialQuantity.setMax(MaxSlider);
		stressWork.setMax(MaxSlider);
		stressNonWork.setMax(MaxSlider);

		// Set progress to half of max
		mood.setProgress(MaxSlider / 2);
		socialQuality.setProgress(MaxSlider / 2);
		socialQuantity.setProgress(MaxSlider / 2);
		stressWork.setProgress(MaxSlider / 2);
		stressNonWork.setProgress(MaxSlider / 2);
		
		// Set listeners
		setListeners();
	}

	private void setListeners() {
		// Create new listeners for seekbars
		mood.setOnSeekBarChangeListener(new SeekBarQualityListener(act,
				moodLabel, SeekBarLabelColors.getColorsRedToGreen()));

		socialQuality.setOnSeekBarChangeListener(new SeekBarQualityListener(
				act, socQualLabel, SeekBarLabelColors.getColorsRedToGreen()));

		socialQuantity.setOnSeekBarChangeListener(new SeekBarQuantityListener(
				act, socQuanLabel, SeekBarLabelColors.getColorsRedToGreen()));

		stressWork.setOnSeekBarChangeListener(new SeekBarQuantityListener(act,
				stressWorkLabel, SeekBarLabelColors.getColorsGreenToRed()));

		stressNonWork.setOnSeekBarChangeListener(new SeekBarQuantityListener(
				act, stressNonWorkLabel, SeekBarLabelColors
						.getColorsGreenToRed()));
	}
}