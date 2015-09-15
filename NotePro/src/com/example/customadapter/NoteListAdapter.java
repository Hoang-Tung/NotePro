package com.example.customadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.notemodel.Note;
import com.example.notepro.R;
import com.example.utils.Constant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
		LinearLayout contanier;
		ImageView alarmSignal;
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
			viewHolder.contanier = (LinearLayout) rowView
					.findViewById(R.id.cell_container);
			viewHolder.alarmSignal = (ImageView) rowView
					.findViewById(R.id.alarm_sign);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.title.setText(noteList.get(position).getTitle());

		if (noteList.get(position).getAlarm_time() == null) {
			holder.alarmSignal.setVisibility(View.GONE);
		}
		Log.d("note id" + position, "note type "
				+ noteList.get(position).getType());
		if (noteList.get(position).getType() == 1) {
			holder.content.setText(noteList.get(position).getContent());
		} else {
			holder.content.setText("Check List");
		}

		holder.created_at.setText(noteList.get(position).getStringCreated_at());

		holder.contanier.setBackgroundColor(Constant.colorList[noteList.get(
				position).getColor()]);
		Log.d("row color", "" + noteList.get(position).getId() + " color "
				+ noteList.get(position).getColor());

		return rowView;
	}
}
