package com.example.notemodel;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;

@SuppressLint("SimpleDateFormat")
public class Note {
	private int id;
	private int type;
	private String content;
	private Date created_at;
	private Date alarm_time;

	public Note(String content) {
		// TODO Auto-generated constructor stub
		this.content = content;
		this.created_at = new Date(System.currentTimeMillis());
	}
	
	public Note(String content, String created_at, String alarm_time) {
		// TODO Auto-generated constructor stub
		this.content = content;
		setCreated_at(created_at);
		if (alarm_time != null)
			setAlarm_time(alarm_time);
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
		DateFormat df = new SimpleDateFormat("yyyyMMdd  HH:mm");
		String alarmTime = df.format(alarm_time);
		return alarmTime;
	}

	public String getStringCreated_at() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd  HH:mm");
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
		DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm",
				Locale.ENGLISH);

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
		DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm",
				Locale.ENGLISH);

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
}
