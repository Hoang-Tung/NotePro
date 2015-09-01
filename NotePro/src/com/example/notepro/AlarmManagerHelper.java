package com.example.notepro;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.notemodel.Note;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

@SuppressLint("NewApi")
public class AlarmManagerHelper extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

	}

	public static void setAlarms(Context context) {
		cancelAlarms(context);
		NoteDataSource noteDataSource = new NoteDataSource(context);
		noteDataSource.open();
		ArrayList<Note> allNote = noteDataSource.getAllNotes();
		noteDataSource.close();
		Calendar calendar = Calendar.getInstance();
		for (Note note : allNote) {
			if (note.getStringAlarm_time() != null) {
				PendingIntent pIntent = createPendingIntent(context, note);
				Date alarm_time = new Date();
				DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm",
						Locale.ENGLISH);

				try {
					alarm_time = format.parse(note.getStringAlarm_time());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calendar.setTime(alarm_time);
				setAlarms(context, calendar, pIntent);
			}
		}

	}
	
	

	public static void setAlarms(Context context, Calendar calendar,
			PendingIntent pIntent) {
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), pIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), pIntent);
		}

	}

	public static void cancelAlarms(Context context) {
		NoteDataSource noteDataSource = new NoteDataSource(context);
		noteDataSource.open();
		ArrayList<Note> allNote = noteDataSource.getAllNotes();
		noteDataSource.close();
		for (Note note : allNote) {
			if (note.getStringAlarm_time() != null) {
				PendingIntent pIntent = createPendingIntent(context, note);
				AlarmManager alarmManager = (AlarmManager) context
						.getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(pIntent);
			}
		}
	}

	private static PendingIntent createPendingIntent(Context contenxt,
			Note model) {

		Intent intent = new Intent(contenxt, AlarmService.class);
		intent.putExtra("id", model.getId());
		intent.putExtra("time", model.getStringAlarm_time());
		return PendingIntent.getService(contenxt, model.getId(), intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
