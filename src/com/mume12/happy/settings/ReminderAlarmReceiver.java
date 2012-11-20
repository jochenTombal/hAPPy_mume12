package com.mume12.happy.settings;

import java.io.IOException;

import com.mume12.happy.R;
import com.mume12.happy.general.HomeScreenActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class ReminderAlarmReceiver extends BroadcastReceiver {

	// This method will receive the Broadcast of the AlarmManager and do
	// whatever you like
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		boolean playSound = bundle.getBoolean("PLAYSOUND");
		boolean vibrate = bundle.getBoolean("VIBRATE");

		// User is not sleeping
		if (vibrate) {
			// vibrate 1 sec
			Vibrator vib = (Vibrator) context
					.getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(1000);
		}

		if (playSound) {
			playSound(context);
		}

		// Show notification
		showNotification(context);
	}

	private void showNotification(Context context) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context);
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setContentTitle(context.getString(R.string.app_name));
		mBuilder.setContentText(context
				.getString(R.string.reminder_notification));

		Intent resultIntent = new Intent(context, HomeScreenActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(HomeScreenActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(100, mBuilder.build());
	}

	// Play sound
	private void playSound(Context context) {
		Uri defaultRingtoneUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		MediaPlayer mediaPlayer = new MediaPlayer();

		try {
			mediaPlayer.setDataSource(context, defaultRingtoneUri);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
