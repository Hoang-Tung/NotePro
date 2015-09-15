package com.example.dialog;

import com.example.notepro.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddItemCheckListDialog extends DialogFragment {

	Context context;
	EditText editContent;
	Button addBtn;
	Button addMoreBtn;
	Button cancelBtn;
	private AddItemCheckListDialog dialog;
	
	public interface AddItemDialogListener {
		public void onAddMorePressed(String content);

		public void onAddPress(String content);

		public void onCancelPressed();
	}

	AddItemDialogListener mListener;

	public AddItemCheckListDialog(Context context) {

		this.context = context;
		dialog = this;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (AddItemDialogListener) activity;
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
		
		addBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onAddPress(editContent.getEditableText().toString());
				dialog.dismiss();
			}
		});
		
		addMoreBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onAddMorePressed(editContent.getEditableText().toString());
				dialog.dismiss();
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onCancelPressed();
				dialog.dismiss();
			}
		});
		
		return view;
	}

}
