package com.example.notepro;

import com.example.notemodel.Note;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends Service {

	public static String TAG = AlarmService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Note note = (Note) intent.getExtras().getSerializable("note_content");
		if (note.getType() == 1) {

			Intent alarmIntent = new Intent(getBaseContext(), CreateNote.class);
			alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			alarmIntent.putExtras(intent);
			getApplication().startActivity(alarmIntent);
		} else {
			Intent alarmIntent = new Intent(getBaseContext(), CreateCheckList.class);
			alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			alarmIntent.putExtras(intent);
			getApplication().startActivity(alarmIntent);
		}
		// AlarmManagerHelper.setAlarms(this);
		return super.onStartCommand(intent, flags, startId);
	}

}
