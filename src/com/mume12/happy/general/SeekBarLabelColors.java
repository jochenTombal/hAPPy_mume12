package com.mume12.happy.general;

import android.graphics.Color;

// Get colors for seekbars and also for piecharts
public class SeekBarLabelColors {

	public static int redLabel = Color.RED;
	public static int orangeLabel = Color.rgb(255, 193, 37);
	public static int greyLabel = Color.GRAY;
	public static int darkgreenLabel = Color.rgb(34, 139, 34);
	public static int lightgreenLabel = Color.rgb(0, 255, 127);

	public static int[] getColorsRedToGreen() {
		int[] colors = new int[] { redLabel, orangeLabel, greyLabel,
				darkgreenLabel, lightgreenLabel };

		return colors;
	}

	public static int[] getColorsGreenToRed() {
		int[] colors = new int[] { lightgreenLabel, darkgreenLabel, greyLabel,
				orangeLabel, redLabel };

		return colors;
	}

}
