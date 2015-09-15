package com.example.notepro;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.example.customadapter.ImageBasicAdapter;
import com.example.customadapter.ImageBasicAdapter.BtnClickListener;
import com.example.customview.LineEditText;
import com.example.dialog.ChangeColorDialog;
import com.example.dialog.ChangeColorDialog.ChangeColorDialogListener;
import com.example.notemodel.Note;
import com.example.utils.Constant;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
@SuppressWarnings("deprecation")
public class CreateNote extends ActionBarActivity implements
		ChangeColorDialogListener {

	Context context;
	private NoteDataSource noteDataSource;
	// EditText editText;
	EditText editTitleText;
	LineEditText editText;
	Button saveButton;
	Button alarm_button;
	Button shareButton;
	ImageButton backBtn;
	ImageButton forwardBtn;
	LinearLayout container;
	private int typeNum = 1;
	private int colorNum;
	private boolean isUpdated = false;
	private boolean isAlarm = false;
	private String created_at;
	GridView imageThumbnail;
	ArrayList<Bitmap> imageBitmaps = new ArrayList<Bitmap>();
	ArrayList<String> imagePath = new ArrayList<String>();
	ArrayList<Note> noteList = new ArrayList<Note>();
	int position = 0;
	ImageBasicAdapter adapter;
	String mCurrentPhotoPath;
	String datealarm = null;
	String timealarm = null;
	String alarmClock;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1337;
	Note note;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		noteDataSource = new NoteDataSource(this);
		noteDataSource.open();
		setContentView(R.layout.create_note);
		colorNum = Constant.defaultColor;
		container = (LinearLayout) findViewById(R.id.container);
		alarm_button = (Button) findViewById(R.id.alarm_time);
		shareButton = (Button) findViewById(R.id.shareBtn);
		editText = (LineEditText) findViewById(R.id.contentEdit);
		editTitleText = (EditText) findViewById(R.id.titleEdit);
		saveButton = (Button) findViewById(R.id.saveBtn);
		imageThumbnail = (GridView) findViewById(R.id.image_space);

		backBtn = (ImageButton) findViewById(R.id.back);
		backBtn.setVisibility(View.GONE);
		forwardBtn = (ImageButton) findViewById(R.id.forward);
		forwardBtn.setVisibility(View.GONE);

		imageThumbnail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// showDeleteImageDialog(imagePath.get(position), position);
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(
						Uri.parse("file://" + imagePath.get(position)),
						"image/*");
				startActivity(intent);
			}
		});
		Button dismiss = (Button) findViewById(R.id.dissmis);
		isUpdated = getIntent().getExtras().getBoolean("isUpdate");
		isAlarm = getIntent().getExtras().getBoolean("isAlarm");
		// Log.d("isUpdate", "" + isUpdated);

		if (isUpdated || isAlarm) {
			note = (Note) getIntent().getExtras().getSerializable(
					"note_content");
			// Log.d("note", note.getTitle());

			noteList = (ArrayList<Note>) getIntent().getExtras()
					.getSerializable("list_note");

			Log.d("note_list", "" + noteList.size());
			position = getIntent().getIntExtra("position", 0);
			Log.d("positon", "" + position);

			backBtn.setVisibility(View.VISIBLE);
			forwardBtn.setVisibility(View.VISIBLE);

			editTitleText.setText(note.getTitle());
			if (note.getAlarm_time() != null) {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
				String alarmTime = df.format(note.getAlarm_time());
				alarm_button.setText(alarmTime);
				alarmClock = alarmTime;
			} else {
				alarm_button.setVisibility(View.GONE);
			}
			editText.setText(note.getContent());
			editText.setBackgroundColor(Constant.colorList[note.getColor()]);
			container.setBackgroundColor(Constant.colorList[note.getColor()]);
			colorNum = note.getColor();
			shareButton.setVisibility(View.VISIBLE);
			String imagePaths = note.getImagePath();
			if (imagePaths != null) {
				imagePath = getImagePathFromDatabase(imagePaths);
				// Log.d("imagePath", imagePaths);
				// Log.d("imagePath size", "" + imagePath.size());
				getBitMapFromFile();
				adapter = new ImageBasicAdapter(this, 0, imageBitmaps,
						new BtnClickListener() {

							@Override
							public void onBtnClick(int position) {
								// TODO Auto-generated method stub
								alertDialogDelImage(position);
							}
						});
				imageThumbnail.setAdapter(adapter);
			}
			if (isAlarm) {
				saveButton.setVisibility(View.GONE);
				dismiss.setVisibility(View.VISIBLE);
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
						Intent intent = new Intent(context, CreateCheckList.class);
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
						Intent intent = new Intent(context, CreateCheckList.class);
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

		shareButton.setOnClickListener(new OnClickListener() {

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

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				Calendar c = Calendar.getInstance();
				created_at = df.format(c.getTime());

				if (isEditTextNull())
					return;
				if (!isUpdated) {
					Note note = noteDataSource.createNote(editTitleText
							.getText().toString(), editText.getText()
							.toString(), created_at, created_at, String
							.valueOf(colorNum), alarmClock, String
							.valueOf(typeNum), buildStringStoreImage(imagePath));

					if (alarmClock != null)
						AlarmManagerHelper.setAlarms(context);
				} else {
					// Log.d("imagePath", buildStringStoreImage(imagePath));
					if (alarmClock != null)
						Log.d("alarm save1", alarmClock);
					Note notevs = noteDataSource.updateNote("" + note.getId(),
							editTitleText.getText().toString(), editText
									.getText().toString(), created_at, String
									.valueOf(colorNum), alarmClock,
							buildStringStoreImage(imagePath));
					if (alarmClock != null)
						AlarmManagerHelper.setAlarms(context);
				}

				noteDataSource.close();
				Intent intent = new Intent(CreateNote.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

		dismiss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				noteDataSource.updateAlarmNote("" +note.getId(), "");
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		noteDataSource.close();
		Intent intent = new Intent(CreateNote.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	public boolean isEditTextNull() {
		if (editText.getText().toString().isEmpty()) {
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
		if (editTitleText.getText().toString().isEmpty()) {
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

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	public void getBitMapFromFile() {
		if (!imagePath.isEmpty()) {
			for (int i = 0; i < imagePath.size(); i++) {
				// Log.d("image file", imagePath.get(i));
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeFile(imagePath.get(i),
						options);
				bitmap = Bitmap.createScaledBitmap(bitmap, 160, 120, true);
				imageBitmaps.add(bitmap);
			}
		}
	}

	public void alertDialogDelImage(final int position) {
		new AlertDialog.Builder(context)
				.setTitle("Delete Image")
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								imagePath.remove(position);
								imageBitmaps.remove(position);
								adapter.notifyDataSetChanged();
								dialog.dismiss();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_TAKE_PHOTO
				&& resultCode == Activity.RESULT_OK) {

			Bitmap imaBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
			imaBitmap = Bitmap.createScaledBitmap(imaBitmap, 160, 120, true);

			imageBitmaps.add(imaBitmap);
			// Log.d("imageBitmap size", "" + imageBitmaps.size());
			adapter = new ImageBasicAdapter(this, 0, imageBitmaps,
					new BtnClickListener() {

						@Override
						public void onBtnClick(int position) {
							// TODO Auto-generated method stub
							alertDialogDelImage(position);
						}
					});

			imagePath.add(mCurrentPhotoPath);
			imageThumbnail.setAdapter(adapter);
			galleryAddPic();
		}
	}

	public String buildStringStoreImage(ArrayList<String> images) {
		if (images.size() != 0) {
			StringBuilder buidler = new StringBuilder();
			for (int i = 0; i < images.size(); i++) {
				buidler.append(images.get(i));
				buidler.append(";");
			}
			return buidler.toString();
		} else
			return null;
	}

	public ArrayList<String> getImagePathFromDatabase(String images) {
		ArrayList<String> imageList = new ArrayList<String>();

		List<String> imageP = Arrays.asList(images.split(";"));
		// Log.d("imageP", "" + imageP.size());
		imageList.addAll(imageP);
		return imageList;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		noteDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		noteDataSource.close();
		super.onPause();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	        
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	public void showChangeColorDialog() {
		ChangeColorDialog colorDialog = new ChangeColorDialog(this);
		colorDialog.show(getSupportFragmentManager(), "colorDialog");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_on_add, menu);
		return true;
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
				Log.d("alarmClock", alarmClock);
				dialog.dismiss();
			}
		});
		dialog.show();
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
		} else if (id == R.id.action_take_picture) {
			// Log.d("take picture", "take_picture");
			dispatchTakePictureIntent();
			return true;
		} else if (id == R.id.action_set_alarm) {
			pickDateTimeDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onColorClick(int color) {
		// TODO Auto-generated method stub
		// container.setBackgroundColor();
		editText.setBackgroundColor(Constant.colorList[color]);
		container.setBackgroundColor(Constant.colorList[color]);
		colorNum = color;
		// Log.d("color pick", "" + color);
	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".png");
		Log.d("File path", mediaFile.getAbsolutePath().toString());
		try {
			if (!mediaFile.exists()) {
				mediaFile.getParentFile().mkdirs();
				mediaFile.createNewFile();
			}
		} catch (IOException e) {
			// TODO: handle exception
			Log.e("OPEN_FILE", "Could not create file.", e);
		}
		mCurrentPhotoPath = mediaFile.getAbsolutePath();
		return mediaFile;
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

}
