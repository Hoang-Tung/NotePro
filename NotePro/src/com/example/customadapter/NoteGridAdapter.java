package com.example.customadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.customadapter.NoteListAdapter.ViewHolder;
import com.example.notemodel.Note;

import com.example.notepro.R;
import com.example.utils.Constant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NoteGridAdapter extends ArrayAdapter<Note> {

	Context context;
	ArrayList<Note> noteList = new ArrayList<Note>();

	public NoteGridAdapter(Context context, int resource,
			ArrayList<Note> objects) {
		super(context, R.layout.note_grid_adapter, objects);
		this.context = context;
		this.noteList = objects;
	}

	public static class ViewHolder {
		TextView title;
		TextView content;
		TextView time;
		ImageView alarmSignal;
		LinearLayout contanier;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			rowView = inflater.inflate(R.layout.note_grid_adapter, parent,
					false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) rowView
					.findViewById(R.id.grid_note_title);
			viewHolder.content = (TextView) rowView
					.findViewById(R.id.grid_note_content);
			viewHolder.contanier = (LinearLayout) rowView
					.findViewById(R.id.containter_grid);
			viewHolder.time = (TextView) rowView
					.findViewById(R.id.grid_note_time);
			viewHolder.alarmSignal = (ImageView) rowView
					.findViewById(R.id.alarm_signal);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		// Log.d("content", noteList.get(position).getContent());

		// Log.d("title", noteList.get(position).getTitle());
		holder.title.setText(noteList.get(position).getTitle());
		if(noteList.get(position).getType() == 1){
		holder.content.setText(noteList.get(position).getContent());
		} else {
			holder.content.setText("Check List");
			holder.content.setTextColor(Color.RED);
		}
			
		holder.time.setText(noteList.get(position).getStringUpdated_at());

		if (noteList.get(position).getAlarm_time() == null) {
			holder.alarmSignal.setVisibility(View.GONE);
		}

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			holder.contanier.setLayoutParams(new GridView.LayoutParams(
					width / 2 - 20, height / 4));
		} else
			holder.contanier.setLayoutParams(new GridView.LayoutParams(
					width / 2 - 20, height / 2));
		// holder.contanier.setBackgroundResource(Constant.backGroundResource[position]);

		Log.d("note color " + noteList.get(position).getId(),
				"" + noteList.get(position).getColor());
		holder.contanier.setBackgroundResource(Constant.backGroundResource[noteList.get(position).getColor()]);
		// holder.contanier.setBackgroundColor(Constant.colorList[noteList.get(
		// position).getColor()]);
		// Log.d("row color", "" + noteList.get(position).getId() + " color "
		// + noteList.get(position).getColor());

		return rowView;
	}
}
