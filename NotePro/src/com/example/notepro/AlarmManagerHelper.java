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
import android.util.Log;

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
		
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		for (Note note : allNote) {

			if (note.getAlarm_time() != null) {
				PendingIntent pIntent = createPendingIntent(context, note);
				if (now.before(note.getAlarm_time())) {
					calendar.setTime(note.getAlarm_time());
					Log.d("calendar", calendar.getTime().toString());
					setAlarms(context, calendar, pIntent);
				} else{
					noteDataSource.updateAlarmNote("" +note.getId(), "");
				}
			}
		}
		noteDataSource.close();
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
			if (note.getAlarm_time() != null) {
				PendingIntent pIntent = createPendingIntent(context, note);
				AlarmManager alarmManager = (AlarmManager) context
						.getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(pIntent);
			}
		}
	}
	
	public static void cancelAlarmsById(Context context, Note note){
		PendingIntent pIntent = createPendingIntent(context, note);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pIntent);
	}

	private static PendingIntent createPendingIntent(Context context,
			Note model) {
		NoteDataSource noteDataSource = new NoteDataSource(context);
		noteDataSource.open();
		ArrayList<Note> allNote = noteDataSource.getAllNotes();
		noteDataSource.close();
		Intent intent = new Intent(context, AlarmService.class);
		intent.putExtra("id", model.getId());
		intent.putExtra("isAlarm", true);
		intent.putExtra("time", model.getStringAlarm_time());
		intent.putExtra("note_content", model);
		intent.putExtra("list_note", allNote);
		return PendingIntent.getService(context, model.getId(), intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
