package com.example.notepro;

import java.util.ArrayList;

import com.example.notemodel.Note;

import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.AdapterView.OnItemClickListener;

public class MyWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICLK = "click";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		// super.onUpdate(context, appWidgetManager, appWidgetIds);
		ComponentName thisWidget = new ComponentName(context,
				MyWidgetProvider.class);
		NoteDataSource noteDataSource = new NoteDataSource(context);
		noteDataSource.open();

		ArrayList<Note> noteList = noteDataSource.getAllNotes();
		noteDataSource.close();

		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		for (int i = 0; i < allWidgetIds.length; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);

			remoteViews.setTextViewText(R.id.widget_note_title, noteList.get(0)
					.getTitle());
			Log.d("title" + this.getClass().getName(), noteList.get(0)
					.getTitle());
			remoteViews.setTextViewText(R.id.widget_note_content,
					noteList.get(0).getContent());
			
			remoteViews.setTextColor(R.id.widget_note_title, Color.YELLOW);
			
			Intent intent = new Intent(context, MyWidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			intent.putExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, noteList);

			Intent intent1 = new Intent(context, CreateNote.class);
			intent1.putExtra("id", noteList.get(0).getId());
			intent1.putExtra("isAlarm", true);
			intent1.putExtra("time", noteList.get(0).getStringAlarm_time());
			intent1.putExtra("note_content", noteList.get(0));
			PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, 0);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widget_note_content,
					pendingIntent);
			
			remoteViews.setOnClickPendingIntent(R.id.widget_note_title, pendingIntent1);
			
			appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
		}
	}

}
