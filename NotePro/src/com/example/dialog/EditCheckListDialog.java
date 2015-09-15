package com.example.dialog;

import java.util.ArrayList;

import com.example.notemodel.CheckListItem;
import com.example.notepro.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class EditCheckListDialog extends DialogFragment {
	
	Context context;
	EditText editContent;
	Button addBtn;
	Button addMoreBtn;
	Button cancelBtn;
	private EditCheckListDialog dialog;
	int position;
	ArrayList<CheckListItem> noteList = new ArrayList<CheckListItem>();
	
	public EditCheckListDialog(int position, ArrayList<CheckListItem> noteList) {
		// TODO Auto-generated constructor stub
		this.position = position;
		this.noteList = noteList;
		dialog = this;
	}
	
	public interface EditCheckListDialogListener{
		public void onEditBtnPressed(String content, int position);
		public void onDeleteBtnPressed(int position);
	}
	
	EditCheckListDialogListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (EditCheckListDialogListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.dialog_add_item_check_list,
				container);
		editContent = (EditText) view.findViewById(R.id.check_item_content);
		addBtn = (Button) view.findViewById(R.id.add);
		addMoreBtn = (Button) view.findViewById(R.id.add_more);
		cancelBtn = (Button) view.findViewById(R.id.cancel);
		
		addBtn.setVisibility(View.GONE);
		editContent.setText(noteList.get(position).getContent());
		addMoreBtn.setText("Edit");
		addMoreBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onEditBtnPressed(editContent.getText().toString(), position);
				dialog.dismiss();
			}
		});
		cancelBtn.setText("Delete");
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onDeleteBtnPressed(position);
				dialog.dismiss();
			}
		});
		
		return view;
	}
}
