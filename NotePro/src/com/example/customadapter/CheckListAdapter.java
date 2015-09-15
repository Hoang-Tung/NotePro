package com.example.customadapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.example.notemodel.CheckListItem;
import com.example.notepro.R;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class CheckListAdapter extends ArrayAdapter<CheckListItem> {

	Context context;
	ArrayList<CheckListItem> itemList;

	public interface CheckListAdapterComponentClickListner {
		public void onEditButtonClick(CheckListItem item, int position);
	}

	CheckListAdapterComponentClickListner mListner;

	public CheckListAdapter(Context context, int resource,
			ArrayList<CheckListItem> objects,
			CheckListAdapterComponentClickListner mListner) {
		super(context, R.layout.check_list_row, objects);
		this.context = context;
		itemList = objects;
		this.mListner = mListner;
	}

	public static class ViewHolder {
		ImageView edit;
		TextView content;
		ImageView signal;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowview = convertView;

		if (rowview == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowview = inflater.inflate(R.layout.check_list_row, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.edit = (ImageView) rowview.findViewById(R.id.item_edit);
			viewHolder.content = (TextView) rowview
					.findViewById(R.id.check_list_item);
			viewHolder.signal = (ImageView) rowview
					.findViewById(R.id.check_sign);

			rowview.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowview.getTag();

		holder.content.setText(itemList.get(position).getContent());

		holder.edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListner.onEditButtonClick(itemList.get(position), position);
			}
		});

		if (itemList.get(position).getChecked() != 0) {
			holder.signal.setImageResource(R.drawable.done);
			holder.signal.setVisibility(View.VISIBLE);
		} else
			holder.signal.setVisibility(View.INVISIBLE);

		return rowview;
	}

}
