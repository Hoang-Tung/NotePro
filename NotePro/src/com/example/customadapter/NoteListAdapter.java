package com.example.customadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.notemodel.Note;
import com.example.notepro.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteListAdapter extends ArrayAdapter<Note> {

	Context context;
	ArrayList<Note> noteList = new ArrayList<Note>();

	public NoteListAdapter(Context context, int resource,
			ArrayList<Note> objects) {
		super(context, R.layout.note_adapter_basic, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.noteList = objects;
	}

	public static class ViewHolder {
		TextView title;
		TextView content;
		TextView created_at;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			rowView = inflater.inflate(R.layout.note_adapter_basic, parent,
					false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) rowView.findViewById(R.id.note_title);
			viewHolder.content = (TextView) rowView
					.findViewById(R.id.note_content);
			viewHolder.created_at = (TextView) rowView
					.findViewById(R.id.note_created_time);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		Log.d("content", noteList.get(position).getContent());

		Log.d("title", noteList.get(position).getTitle());
		holder.title.setText(noteList.get(position).getTitle());

		holder.content.setText(noteList.get(position).getContent());
		holder.created_at.setText(noteList.get(position).getStringCreated_at());

		return rowView;
	}
}
