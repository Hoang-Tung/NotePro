package com.example.customadapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.notepro.R;

@SuppressLint("ViewHolder")
public class ImageBasicAdapter extends ArrayAdapter<Bitmap> {

	Context context;
	ArrayList<Bitmap> bitmapList;

	private BtnClickListener mListener = null;

	public interface BtnClickListener {
		public abstract void onBtnClick(int position);
	}

	public ImageBasicAdapter(Context context, int resource,
			ArrayList<Bitmap> objects, BtnClickListener listener) {
		super(context, R.layout.image_basic_adapter, objects);
		this.context = context;
		this.bitmapList = objects;
		mListener = listener;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.image_basic_adapter, parent,
				false);
		ImageView image = (ImageView) rowView.findViewById(R.id.image_view);
		ImageView imageTrash = (ImageView) rowView.findViewById(R.id.close_btn);
		// image.setLayoutParams(new RelativeLayout.LayoutParams(width /3, width
		// /2));
		image.setScaleType(ScaleType.FIT_XY);
		image.setImageBitmap(bitmapList.get(position));

		imageTrash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onBtnClick(position);
			}
		});

		return rowView;
	}

}
