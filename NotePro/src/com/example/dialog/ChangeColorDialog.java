package com.example.dialog;

import java.util.List;

import com.example.customadapter.ChangeColorAdapter;
import com.example.notepro.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class ChangeColorDialog extends DialogFragment{
	
	private GridView gridView;
	private String[] listColor;
	private Context context;
	private ChangeColorDialog colorDialog;
	
	public interface ChangeColorDialogListener{
		public void onColorClick(String color);
	}
	
	ChangeColorDialogListener mListener;
	
	public ChangeColorDialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		colorDialog = this;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (ChangeColorDialogListener) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.change_color_dialog, container);
		gridView = (GridView)view.findViewById(R.id.container);
		listColor = getResources().getStringArray(R.array.color_array);
		ChangeColorAdapter adapter = new ChangeColorAdapter(context, listColor);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mListener.onColorClick(listColor[position]);
				colorDialog.dismiss();
			}
		});
		return view;
	}
	
}
