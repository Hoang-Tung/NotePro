package com.example.customadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.notepro.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageBasicAdapter extends ArrayAdapter<Bitmap> {

	Context context;
	ArrayList<Bitmap> bitmapList;

	public ImageBasicAdapter(Context context, int resource,
			ArrayList<Bitmap> objects) {
		super(context, R.layout.image_basic_adapter, objects);
		this.context = context;
		this.bitmapList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.image_basic_adapter, parent, false);
		ImageView image = (ImageView) rowView.findViewById(R.id.image_view);
		
		image.setImageBitmap(bitmapList.get(position));
		
		return rowView;
	}

}
