package com.example.notepro;

import com.example.dialog.ChangeColorDialog;
import com.example.dialog.ChangeColorDialog.ChangeColorDialogListener;
import com.example.utils.Constant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class CreateNote extends ActionBarActivity implements ChangeColorDialogListener{
	
	private NoteDataSource noteDataSource;
	EditText editText;
	Button saveButton;
	LinearLayout container;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_note);
		container = (LinearLayout)findViewById(R.id.container);
		editText = (EditText)findViewById(R.id.contentEdit);
		saveButton = (Button)findViewById(R.id.saveBtn);
	}
	
	public void showChangeColorDialog(){
		ChangeColorDialog colorDialog = new ChangeColorDialog(this);
		colorDialog.show(getSupportFragmentManager(), "colorDialog");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_on_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_change_color) {
			showChangeColorDialog();
			return true;
		} 
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onColorClick(int color) {
		// TODO Auto-generated method stub
		//container.setBackgroundColor();
		editText.setBackgroundColor(Constant.colorList[color]);
		Log.d("color pick", "" + color);
	}
}
