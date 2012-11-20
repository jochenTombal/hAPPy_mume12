package com.mume12.happy.general;

import com.mume12.happy.R;

import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarQualityListener implements OnSeekBarChangeListener {

	private Context context;

	private TextView mLabel;

	private String[] labels;

	private int[] colors;

	public SeekBarQualityListener(Context con, TextView label, int[] colors) {
		this.context = con;
		this.mLabel = label;

		// Get text for labels from strings.xml
		this.labels = new String[] { context.getString(R.string.seekbarQual0),
				context.getString(R.string.seekbarQual1),
				context.getString(R.string.seekbarQual2),
				context.getString(R.string.seekbarQual3),
				context.getString(R.string.seekbarQual4) };

		// Get colors from SeekBarLabelColors
		this.colors = colors;
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress == 0) {
			mLabel.setText(labels[0]);
			mLabel.setTextColor(colors[0]);
		} else if (progress == 1) {
			mLabel.setText(labels[1]);
			mLabel.setTextColor(colors[1]);
		} else if (progress == 2) {
			mLabel.setText(labels[2]);
			mLabel.setTextColor(colors[2]);
		} else if (progress == 3) {
			mLabel.setText(labels[3]);
			mLabel.setTextColor(colors[3]);
		} else {
			mLabel.setText(labels[4]);
			mLabel.setTextColor(colors[4]);
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
