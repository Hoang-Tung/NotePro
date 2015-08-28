package com.example.notepro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NOTE = "notes";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CONTENT = "content";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String ALARM_TIME = "alarm_time";
	public static final String COLUMN_COLOR = "color";

	private static final String DATABASE_NAME = "note.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table " + TABLE_NOTE
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_CONTENT + " text not null, " + COLUMN_COLOR + " integer, " + CREATED_AT
			+ " text not null, " + UPDATED_AT + " text not null, " + ALARM_TIME + " text);";

	public NoteSQLiteHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d(NoteSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXIST " + TABLE_NOTE);
		onCreate(db);
	}

}
