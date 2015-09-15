package com.example.customadapter;

import com.example.notepro.R;
import com.example.utils.Constant;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChangeColorAdapter extends ArrayAdapter<String> {

	private Context context;
	private String[] listColor;
	

	public ChangeColorAdapter(Context context, String[] listColor) {
		// TODO Auto-generated constructor stub
		super(context, 0, listColor);
		this.context = context;
		this.listColor = listColor;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.color_adapter, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textView = (TextView)rowView.findViewById(R.id.color_name);
			viewHolder.linearLayout = (LinearLayout)rowView.findViewById(R.id.cell);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		holder.textView.setText(listColor[position]);
		holder.linearLayout.setBackgroundColor(Constant.colorList[position]);
		
		return rowView;
	}

	public static class ViewHolder {
		LinearLayout linearLayout;
		TextView textView;
	}
}
