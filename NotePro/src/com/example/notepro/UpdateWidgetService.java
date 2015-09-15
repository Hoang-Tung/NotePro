package com.example.notepro;

import java.util.ArrayList;

import com.example.notemodel.Note;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;

public class UpdateWidgetService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		NoteDataSource noteDataSource = new NoteDataSource(this.getApplicationContext());
		noteDataSource.open();
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
		
		ArrayList<Note> noteList = noteDataSource.getAllNotes();
		
				
		
	}

}
