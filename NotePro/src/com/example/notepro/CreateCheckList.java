package com.example.notepro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

import com.example.customadapter.CheckListAdapter;
import com.example.customadapter.CheckListAdapter.CheckListAdapterComponentClickListner;
import com.example.dialog.AddItemCheckListDialog;
import com.example.dialog.ChangeColorDialog;
import com.example.dialog.AddItemCheckListDialog.AddItemDialogListener;
import com.example.dialog.ChangeColorDialog.ChangeColorDialogListener;
import com.example.dialog.EditCheckListDialog;
import com.example.dialog.EditCheckListDialog.EditCheckListDialogListener;
import com.example.notemodel.CheckListItem;
import com.example.notemodel.Note;
import com.example.utils.Constant;

@SuppressLint("NewApi")
public class CreateCheckList extends ActionBarActivity implements
		AddItemDialogListener, ChangeColorDialogListener, EditCheckListDialogListener {

	Context context;
	private NoteDataSource noteDataSource;
	ArrayList<Note> noteList = new ArrayList<Note>();
	ListView checkList;
	EditText title;
	ImageButton addItem;
	CheckListAdapter adapter;
	ArrayList<CheckListItem> checkItems = new ArrayList<CheckListItem>();
	Button saveBtn;
	ImageButton backBtn;
	ImageButton forwardBtn;
	Button shareBtn;
	Button alarm_button;
	Button dismissBtn;
	String timealarm;
	String datealarm;
	String alarmClock;
	private int typeNum = 2;
	LinearLayout container;
	boolean isUpdated = false;
	boolean isAlarm = false;
	Note note;
	String created_at;
	int colorNum = Constant.defaultColor;
	int position;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_check_list);
		context = this;
		title = (EditText) findViewById(R.id.check_list_title);
		addItem = (ImageButton) findViewById(R.id.item_add);
		checkList = (ListView) findViewById(R.id.checklist);

		container = (LinearLayout) findViewById(R.id.container);
		container.setBackgroundColor(Constant.colorList[colorNum]);

		saveBtn = (Button) findViewById(R.id.saveBtn);
		backBtn = (ImageButton) findViewById(R.id.back);
		forwardBtn = (ImageButton) findViewById(R.id.forward);
		shareBtn = (Button) findViewById(R.id.shareBtn);
		alarm_button = (Button) findViewById(R.id.alarm_time);
		alarm_button.setVisibility(View.GONE);
		dismissBtn = (Button) findViewById(R.id.dissmis);
		noteDataSource = new NoteDataSource(context);
		noteDataSource.open();
		isUpdated = getIntent().getExtras().getBoolean("isUpdate");
		isAlarm = getIntent().getExtras().getBoolean("isAlarm");

		if (isUpdated || isAlarm) {
			noteList = (ArrayList<Note>) getIntent().getExtras()
					.getSerializable("list_note");
			position = getIntent().getIntExtra("position", 0);
			note = (Note) getIntent().getExtras().getSerializable(
					"note_content");
			colorNum = note.getColor();
			Log.d("colorNum", "" + note.getColor());
			container.setBackgroundColor(Constant.colorList[colorNum]);
			title.setText(note.getTitle());
			backBtn.setVisibility(View.VISIBLE);
			forwardBtn.setVisibility(View.VISIBLE);
			shareBtn.setVisibility(View.VISIBLE);
			if (note.getAlarm_time() != null) {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
				String alarmTime = df.format(note.getAlarm_time());
				alarm_button.setText(alarmTime);
				alarmClock = alarmTime;
				alarm_button.setVisibility(View.VISIBLE);
			} else {
				alarm_button.setVisibility(View.GONE);
			}

			try {
				checkItems = readFromJSONArray(new JSONArray(note.getContent()));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (isAlarm) {
				saveBtn.setVisibility(View.GONE);
				dismissBtn.setVisibility(View.VISIBLE);
			}

		} else {
			alarm_button.setVisibility(View.GONE);
			backBtn.setVisibility(View.GONE);
			forwardBtn.setVisibility(View.GONE);
		}

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (position != 0) {
					if (noteList.get(position - 1).getType() == 1) {
						Intent intent = new Intent(context, CreateNote.class);
						intent.putExtra("isUpdate", true);
						intent.putExtra("note_content",
								noteList.get(position - 1));
						intent.putExtra("position", position - 1);
						intent.putExtra("list_note", noteList);
						startActivity(intent);
						finish();
					} else {
						Intent intent = new Intent(context,
								CreateCheckList.class);
						intent.putExtra("isUpdate", true);
						intent.putExtra("note_content",
								noteList.get(position - 1));
						intent.putExtra("position", position - 1);
						intent.putExtra("list_note", noteList);
						startActivity(intent);
						finish();
					}
				}
			}
		});

		forwardBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (position != noteList.size() - 1) {
					if (noteList.get(position + 1).getType() == 1) {
						Intent intent = new Intent(context, CreateNote.class);
						intent.putExtra("isUpdate", true);
						intent.putExtra("note_content",
								noteList.get(position + 1));
						intent.putExtra("position", position + 1);
						intent.putExtra("list_note", noteList);
						startActivity(intent);
						finish();
					} else {
						Intent intent = new Intent(context,
								CreateCheckList.class);
						intent.putExtra("isUpdate", true);
						intent.putExtra("note_content",
								noteList.get(position + 1));
						intent.putExtra("position", position + 1);
						intent.putExtra("list_note", noteList);
						startActivity(intent);
						finish();
					}
				}
			}
		});

		dismissBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String shareBody = note.getTitle() + "/n" + note.getContent();
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						getResources().getString(R.string.title)));
			}
		});

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = creatContentNote();
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				Calendar c = Calendar.getInstance();
				created_at = df.format(c.getTime());

				if (isEditTextNull(content))
					return;
				if (!isUpdated) {
					Note note = noteDataSource.createNote(title.getText()
							.toString(), content, created_at, created_at,
							String.valueOf(colorNum), alarmClock, String
									.valueOf(typeNum), null);

					if (alarmClock != null)
						AlarmManagerHelper.setAlarms(context);
				} else {
					// Log.d("imagePath", buildStringStoreImage(imagePath));
					if (alarmClock != null)
						Log.d("alarm save1", alarmClock);
					Note notevs = noteDataSource.updateNote("" + note.getId(),
							title.getText().toString(), content, created_at,
							String.valueOf(colorNum), alarmClock, null);
					if (alarmClock != null)
						AlarmManagerHelper.setAlarms(context);
				}

				noteDataSource.close();
				Intent intent = new Intent(CreateCheckList.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

		addItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialogAddMoreItem();
			}
		});

		adapter = new CheckListAdapter(context, 0, checkItems,
				new CheckListAdapterComponentClickListner() {

					@Override
					public void onEditButtonClick(CheckListItem item,
							int position) {
						// TODO Auto-generated method stub
						EditCheckListDialog dialog = new EditCheckListDialog(position, checkItems);
						dialog.show(getSupportFragmentManager(), "Edit item");
					}
				});

		checkList.setAdapter(adapter);

		checkList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (checkItems.get(position).getChecked() == 0)

					checkItems.get(position).setChecked(1);
				else
					checkItems.get(position).setChecked(0);
				adapter.notifyDataSetChanged();
			}
		});

		alarm_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (alarm_button.getText() != null && isUpdated == true) {
					if (note.getAlarm_time() != null)
						AlarmManagerHelper.cancelAlarmsById(context, note);
					noteDataSource.deleteAlarm(note);
					alarm_button.setText("");
					alarm_button.setVisibility(View.GONE);
					alarmClock = null;
				} else {
					alarmClock = null;
					alarm_button.setVisibility(View.GONE);
				}
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		noteDataSource.close();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public boolean isEditTextNull(String content) {
		if (content.isEmpty()) {
			new AlertDialog.Builder(this)
					.setTitle("Cannot save")
					.setMessage("Note content null")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).show();
			return true;
		}
		if (title.getText().toString().isEmpty()) {
			new AlertDialog.Builder(this)
					.setTitle("Cannot save")
					.setMessage("Title content null")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).show();
			return true;
		}

		return false;
	}

	public ArrayList<CheckListItem> readFromJSONArray(JSONArray data) {
		ArrayList<CheckListItem> arrayList = new ArrayList<CheckListItem>();
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject object = data.getJSONObject(i);
				CheckListItem item = new CheckListItem(
						object.getString("content"), object.getInt("checked"));

				arrayList.add(item);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Log.d("array list from json", "" + arrayList.size());
		return arrayList;
	}

	public String creatContentNote() {
		JSONArray array = new JSONArray();
		for (int i = 0; i < checkItems.size(); i++) {
			JSONObject object = new JSONObject();
			try {
				object.put("content", checkItems.get(i).getContent());
				object.put("checked", checkItems.get(i).getChecked());
				array.put(object);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Log.d("array json", array.toString());
		return array.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_on_add, menu);
		MenuItem item = menu.findItem(R.id.action_take_picture);
		item.setVisible(false);
		this.invalidateOptionsMenu();

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
		} else if (id == R.id.action_set_alarm) {
			pickDateTimeDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	public void showChangeColorDialog() {
		ChangeColorDialog colorDialog = new ChangeColorDialog(this);
		colorDialog.show(getSupportFragmentManager(), "colorDialog");
	}

	private void pickDateTimeDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.date_time_picker);
		dialog.setTitle("Alarm Clock");
		String alarm;

		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		timealarm = "" + String.format("%02d", hour) + ":"
				+ String.format("%02d", minute);
		datealarm = "" + year + "/" + String.format("%02d", month + 1) + "/"
				+ String.format("%02d", day);

		TimePicker timePicker = (TimePicker) dialog
				.findViewById(R.id.time_picker);

		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				timealarm = "" + String.format("%02d", hourOfDay) + ":"
						+ String.format("%02d", minute);
			}
		});

		DatePicker datePicker = (DatePicker) dialog
				.findViewById(R.id.date_picker);
		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				// Log.d("date", "year: " + year + " month: " + monthOfYear
				// + " day: " + dayOfMonth);

				datealarm = "" + year + "/"
						+ String.format("%02d", monthOfYear + 1) + "/"
						+ String.format("%02d", dayOfMonth);
				Log.d("datealarm", datealarm);
			}
		});

		Button getAlarmTime = (Button) dialog.findViewById(R.id.get_time_date);
		getAlarmTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alarmClock = datealarm + " " + timealarm;
				alarm_button.setText(alarmClock);
				alarm_button.setVisibility(View.VISIBLE);
				// Log.d("alarmClock", alarmClock);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void showDialogAddMoreItem() {
		AddItemCheckListDialog dialog = new AddItemCheckListDialog(context);
		dialog.show(getSupportFragmentManager(), "Dialog Add Item");
	}

	@Override
	public void onAddMorePressed(String content) {
		// TODO Auto-generated method stub
		checkItems.add(new CheckListItem(content));
		adapter.notifyDataSetChanged();
		showDialogAddMoreItem();
	}

	@Override
	public void onAddPress(String content) {
		// TODO Auto-generated method stub
		checkItems.add(new CheckListItem(content));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCancelPressed() {
		// TODO Auto-generated method stub
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onColorClick(int position2) {
		// TODO Auto-generated method stub
		container.setBackgroundColor(Constant.colorList[position2]);
		colorNum = position2;
		Log.d("positon color", "" + position2);
	}

	@Override
	public void onEditBtnPressed(String content, int post) {
		// TODO Auto-generated method stub
		Log.d("content edit", content + " " + post);
		checkItems.get(post).setContent(content);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDeleteBtnPressed(int position) {
		// TODO Auto-generated method stub
		checkItems.remove(position);
		adapter.notifyDataSetChanged();
	}

}
