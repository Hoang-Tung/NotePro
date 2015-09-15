package com.example.notemodel;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;

@SuppressLint({ "SimpleDateFormat", "NewApi" })
public class Note implements Serializable {
	private int id;
	private int type;
	private int color;
	private String title;
	private String content;
	private Date created_at;
	private Date updated_at;
	private Date alarm_time;
	private String imagePath;

	public Note(String content) {
		// TODO Auto-generated constructor stub
		this.content = content;
		this.created_at = new Date(System.currentTimeMillis());
		title = "title";
	}

	public Note(String content, String created_at, String alarm_time) {
		// TODO Auto-generated constructor stub
		this.content = content;
		setCreated_at(created_at);
		if (alarm_time != null)
			setAlarm_time(alarm_time);
		title = "title";
	}

	public int getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStringAlarm_time() {
		if (alarm_time != null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
		String alarmTime = df.format(alarm_time);
		return alarmTime;
	}

	public String getStringCreated_at() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
		String createdTime = df.format(this.created_at);
		return createdTime;
	}

	public Date getAlarm_time() {
		return alarm_time;
	}

	public void setAlarm_time(Date alarm_time) {
		
		this.alarm_time = alarm_time;
	}

	public void setAlarm_time(String alarm_time) {
		if(alarm_time.isEmpty()){
			Log.d("alarm", "empty");
			return;
		}
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Log.d(this.getClass().getName(), " set alarm");
		try {
			this.alarm_time = format.parse(alarm_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setCreated_at(String created_at) {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		try {
			this.created_at = format.parse(created_at);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getType() {
		return type;
	}

	public void setType(String type) {
		this.type = Integer.parseInt(type);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setColor(String color) {
		this.color = Integer.parseInt(color);
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public String getStringUpdated_at() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
		String updatedTime = df.format(this.updated_at);
		return updatedTime;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public void setUpdated_at(String updated_at) {
		if(updated_at.isEmpty())
			return;
		
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		try {
			this.updated_at = format.parse(updated_at);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getInfoNote() {
		String tmp = "id: " + String.valueOf(id) + " color: "
				+ String.valueOf(color) + " content: " + content;
		return tmp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
