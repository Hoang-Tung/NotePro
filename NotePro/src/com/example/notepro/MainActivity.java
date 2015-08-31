package com.example.notepro;

import java.util.ArrayList;

import com.example.customadapter.NoteListAdapter;
import com.example.notemodel.Note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
	
	NoteDataSource noteDataSource;
	ListView listView;
	ArrayList<Note> noteList = new ArrayList<Note>();
	NoteListAdapter noteListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteDataSource = new NoteDataSource(this);
		noteDataSource.open();
		noteList = noteDataSource.getAllNotes();
		Log.d("noteList", "" + noteList.size());
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.note_list);
		noteListAdapter = new NoteListAdapter(this, 0, noteList);
		listView.setAdapter(noteListAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, CreateNote.class);
				intent.putExtra("isUpdate", true);
				intent.putExtra("note_content", noteList.get(position));
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		noteDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if(id == R.id.action_add_new){
			Intent intent = new Intent(this, CreateNote.class);
			intent.putExtra("isUpdate", false);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
	}
}
