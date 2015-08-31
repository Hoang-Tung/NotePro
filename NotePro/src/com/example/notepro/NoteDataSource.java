package com.example.notepro;

import java.util.ArrayList;
import java.util.List;

import com.example.notemodel.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NoteDataSource {
	private SQLiteDatabase database;
	private NoteSQLiteHelper dbHelper;

	private String[] allCollums = { NoteSQLiteHelper.COLUMN_ID,
			NoteSQLiteHelper.COLUMN_CONTENT, NoteSQLiteHelper.COLUMN_COLOR,
			NoteSQLiteHelper.COLUMN_TYPE, NoteSQLiteHelper.CREATED_AT,
			NoteSQLiteHelper.UPDATED_AT, NoteSQLiteHelper.ALARM_TIME,
			NoteSQLiteHelper.COLUMN_TITLE, NoteSQLiteHelper.COLUMN_IMAGE };

	public NoteDataSource(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new NoteSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Note createNote(String title, String content, String updated_at,
			String created_at, String color, String alarm_time, String type, String imagePath) {

		ContentValues values = new ContentValues();
		values.put(NoteSQLiteHelper.COLUMN_TITLE, title);
		values.put(NoteSQLiteHelper.COLUMN_CONTENT, content);
		values.put(NoteSQLiteHelper.COLUMN_COLOR, color);
		values.put(NoteSQLiteHelper.COLUMN_TYPE, type);
		values.put(NoteSQLiteHelper.CREATED_AT, created_at);
		values.put(NoteSQLiteHelper.UPDATED_AT, updated_at);
		values.put(NoteSQLiteHelper.ALARM_TIME, alarm_time);
		values.put(NoteSQLiteHelper.COLUMN_IMAGE, imagePath);
		
		long insertId = database.insert(NoteSQLiteHelper.TABLE_NOTE, null,
				values);

		Cursor cursor = database.query(NoteSQLiteHelper.TABLE_NOTE, allCollums,
				NoteSQLiteHelper.COLUMN_ID + " = " + insertId, null, null,
				null, null);

		cursor.moveToFirst();
		Note note = cursorToNote(cursor);
		cursor.close();
		return note;
	}
	
	
	
	public void deleteComment(Note note) {
		long id = note.getId();
		Log.d("Note delete with id: ", "" + id);
		database.delete(NoteSQLiteHelper.TABLE_NOTE, NoteSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public ArrayList<Note> getAllNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();

		Cursor cursor = database.query(NoteSQLiteHelper.TABLE_NOTE, allCollums,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notes.add(note);
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}

	private Note cursorToNote(Cursor cursor) {
		Note note = new Note(cursor.getString(1));
		note.setId(cursor.getString(0));
		note.setColor(cursor.getString(2));
		note.setType(cursor.getString(3));
		note.setCreated_at(cursor.getString(4));
		if (cursor.getString(7) != null) {
			note.setTitle(cursor.getString(7));
		}
		if (cursor.getString(5) != null) {
			note.setUpdated_at(cursor.getString(5));
		}
		if (cursor.getString(6) != null) {
			note.setAlarm_time(cursor.getString(6));
		}
		if(cursor.getString(8) != null){
			note.setImagePath(cursor.getString(8));
		}
		return note;
	}
}
