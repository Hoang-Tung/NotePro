package com.example.notepro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.customadapter.NoteGridAdapter;
import com.example.customadapter.NoteListAdapter;
import com.example.notemodel.Note;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

	NoteDataSource noteDataSource;
	ListView listView;
	ArrayList<Note> noteList = new ArrayList<Note>();
	NoteListAdapter noteListAdapter;
	NoteGridAdapter noteGridAdapter;
	GridView gridView;
	private long mBackPressed;
	private static final int TIME_INTERVAL = 2000;
	SharedPreferences prefs;
	private int gridOrList = 0;
	private int timeOrTitle = 0;
	String viewTypeChoice = "com.example.notepro.gridorlist";
	String sortTypeChoise = "com.example.notepro.orderby";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteDataSource = new NoteDataSource(this);
		noteDataSource.open();
		noteList = noteDataSource.getAllNotes();

		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.note_list);
		gridView = (GridView) findViewById(R.id.note_grid);

		noteListAdapter = new NoteListAdapter(this, 0, noteList);
		noteGridAdapter = new NoteGridAdapter(this, 0, noteList);
		listView.setAdapter(noteListAdapter);
		gridView.setAdapter(noteGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				dialogItemClick(position);
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				dialogItemClick(position);
			}
		});

		prefs = this.getSharedPreferences("com.example.notepro",
				Context.MODE_PRIVATE);

		setListAdapter();
	}

	public void setListAdapter() {
		gridOrList = (int) prefs.getLong(viewTypeChoice, gridOrList);
		timeOrTitle = (int) prefs.getLong(sortTypeChoise, timeOrTitle);
		if (gridOrList == 0) {
			gridView.setVisibility(View.GONE);
		} else
			listView.setVisibility(View.GONE);
		if (timeOrTitle == 0) {
			sortNoteByTime();
		} else
			sortNodeByTitle();
	}

	public void dialogItemClick(final int position1) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_edit_or_del);
		ListView delOrEdit = (ListView) dialog.findViewById(R.id.list_view);
		String[] delOrE = getResources().getStringArray(R.array.del_or_edit);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, delOrE);
		delOrEdit.setAdapter(adapter);
		delOrEdit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 0) {
					if (noteList.get(position1).getType() == 1) {
						Intent intent = new Intent(MainActivity.this,
								CreateNote.class);
						intent.putExtra("isUpdate", true);
						intent.putExtra("note_content", noteList.get(position1));
						intent.putExtra("position", position1);
						intent.putExtra("list_note", noteList);
						startActivity(intent);
						finish();
					} else {
						Intent intent = new Intent(MainActivity.this,
								CreateCheckList.class);
						intent.putExtra("isUpdate", true);
						intent.putExtra("note_content", noteList.get(position1));
						intent.putExtra("position", position1);
						intent.putExtra("list_note", noteList);
						startActivity(intent);
						finish();
					}
				} else {
					noteDataSource.deleteComment(noteList.get(position1));
					noteList.remove(position1);

					noteGridAdapter.notifyDataSetChanged();
					noteListAdapter.notifyDataSetChanged();
				}
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		noteDataSource.open();
		noteList = noteDataSource.getAllNotes();
		noteListAdapter.notifyDataSetInvalidated();
		noteGridAdapter.notifyDataSetInvalidated();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_add_new_note) {
			Intent intent = new Intent(this, CreateNote.class);
			intent.putExtra("isUpdate", false);
			startActivity(intent);
			finish();
			return true;
		} else if (id == R.id.action_add_new_check_list) {
			Intent intent = new Intent(this, CreateCheckList.class);
			intent.putExtra("isUpdate", false);
			startActivity(intent);
			finish();
			return true;
		} else if (id == R.id.view_by_grid) {
			listView.setVisibility(View.GONE);
			gridView.setVisibility(View.VISIBLE);
			prefs.edit().putLong(viewTypeChoice, 1).apply();
			return true;
		} else if (id == R.id.view_by_list) {
			gridView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			prefs.edit().putLong(viewTypeChoice, 0).apply();
			return true;
		} else if (id == R.id.sort_by_time) {
			sortNoteByTime();
			prefs.edit().putLong(sortTypeChoise, 0).apply();
			return true;
		} else if (id == R.id.sort_by_title) {
			sortNodeByTitle();
			prefs.edit().putLong(sortTypeChoise, 1).apply();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void sortNoteByTime() {
		Collections.sort(noteList, new Comparator<Note>() {

			@Override
			public int compare(Note lhs, Note rhs) {
				// TODO Auto-generated method stub
				return rhs.getStringUpdated_at().compareTo(
						lhs.getStringUpdated_at());
			}
		});

		noteListAdapter.notifyDataSetChanged();
	}

	public void sortNodeByTitle() {
		Collections.sort(noteList, new Comparator<Note>() {

			@Override
			public int compare(Note lhs, Note rhs) {
				// TODO Auto-generated method stub
				return lhs.getTitle().compareTo(rhs.getTitle());
			}
		});

		noteListAdapter.notifyDataSetChanged();
	}
}
