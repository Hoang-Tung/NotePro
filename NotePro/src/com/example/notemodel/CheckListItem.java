package com.example.notemodel;

public class CheckListItem {
	
	private String content;
	private int checked;
	
	public CheckListItem() {
		// TODO Auto-generated constructor stub
		checked = 0;
	}
	
	public CheckListItem(String content){
		this.content = content;
		this.checked = 0;
	}
	
	public CheckListItem(String content, int checked){
		this.content = content;
		this.checked = checked;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	
}
