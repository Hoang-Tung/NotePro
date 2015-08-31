package com.example.notepro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.example.customadapter.ImageBasicAdapter;
import com.example.dialog.ChangeColorDialog;
import com.example.dialog.ChangeColorDialog.ChangeColorDialogListener;
import com.example.notemodel.Note;
import com.example.utils.Constant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
@SuppressWarnings("deprecation")
public class CreateNote extends ActionBarActivity implements
		ChangeColorDialogListener {

	private NoteDataSource noteDataSource;
	EditText editText;
	EditText editTitleText;
	Button saveButton;
	LinearLayout container;
	private int typeNum = 1;
	private int colorNum;
	private boolean isUpdated = false;
	private String created_at;
	GridView imageThumbnail;
	ArrayList<Bitmap> imageBitmaps = new ArrayList<Bitmap>();
	ArrayList<String> imagePath = new ArrayList<String>();
	String mCurrentPhotoPath;

	static final int REQUEST_IMAGE_CAPTURE = 1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		noteDataSource = new NoteDataSource(this);
		noteDataSource.open();
		setContentView(R.layout.create_note);
		colorNum = 9;
		container = (LinearLayout) findViewById(R.id.container);
		editText = (EditText) findViewById(R.id.contentEdit);
		editTitleText = (EditText) findViewById(R.id.titleEdit);
		saveButton = (Button) findViewById(R.id.saveBtn);
		imageThumbnail = (GridView) findViewById(R.id.image_space);

		isUpdated = getIntent().getExtras().getBoolean("isUpdate");
		Log.d("isUpdate", "" + isUpdated);

		if (isUpdated) {
			Note note = (Note) getIntent().getExtras().getSerializable(
					"note_content");
			Log.d("note", note.getTitle());
			editTitleText.setText(note.getTitle());
			editText.setText(note.getContent());
		}

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateFormat df = new SimpleDateFormat("yyyyMMdd  HH:mm");
				created_at = df.format(new Date(System.currentTimeMillis()));
				Log.d("created_at", created_at);
				if (editText.getText().toString().isEmpty()) {
					new AlertDialog.Builder(CreateNote.this)
							.setTitle("Cannot save")
							.setMessage("Note content null")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									}).show();
					return;
				}
				if (editTitleText.getText().toString().isEmpty()) {
					new AlertDialog.Builder(CreateNote.this)
							.setTitle("Cannot save")
							.setMessage("Title content null")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									}).show();
					return;
				}
				if (!isUpdated) {
					Log.d("imagePath", buildStringStoreImage(imagePath));
					Note note = noteDataSource.createNote(editTitleText
							.getText().toString(), editText.getText()
							.toString(), "", created_at, String
							.valueOf(colorNum), created_at, String
							.valueOf(typeNum), "");
				} else {
					Log.d("imagePath", buildStringStoreImage(imagePath));
				}
				// Log.d("Note info", note.getInfoNote());
				noteDataSource.close();
				Intent intent = new Intent(CreateNote.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
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
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
				
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imaBitmap = (Bitmap) extras.get("data");
			imageBitmaps.add(imaBitmap);
			ImageBasicAdapter adapter = new ImageBasicAdapter(this, 0,
					imageBitmaps);
			ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
			imaBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream2);
			
			try {
				FileOutputStream fos = new FileOutputStream(mCurrentPhotoPath);
				fos.write(stream2.toByteArray());
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			imagePath.add(mCurrentPhotoPath);
			imageThumbnail.setAdapter(adapter);
		}
	}

	public String buildStringStoreImage(ArrayList<String> images) {
		StringBuilder buidler = new StringBuilder();
		for (int i = 0; i < images.size(); i++) {
			buidler.append(images.get(i));
			buidler.append(";");
		}
		return buidler.toString();
	}

	public ArrayList<String> getImagePathFromDatabase(String images) {
		ArrayList<String> imageList = new ArrayList<String>();

		imageList = (ArrayList<String>) Arrays.asList(images.split(";"));

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
			Log.d("take picture", "take_picture");
			dispatchTakePictureIntent();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onColorClick(int color) {
		// TODO Auto-generated method stub
		// container.setBackgroundColor();
		editText.setBackgroundColor(Constant.colorList[color]);
		colorNum = color;
		Log.d("color pick", "" + color);
	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
//		File storageDir = Environment
//				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES,"");
//		File image = File.createTempFile(imageFileName, ".jpg", storageDir);
		
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".png");
		Log.d("File path", mediaFile.getAbsolutePath().toString());
		mCurrentPhotoPath = mediaFile.getAbsolutePath();
		return mediaFile;
	}

}
