package com.kn.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
	
	private long id;
	private String content;
	private String author;
	private long timeStamp;
	
	public Comment(long id, String author, String content, long timeStamp) {
		this.id = id;
		this.author = author;
		this.content = content;
		this.timeStamp = timeStamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(author);
		dest.writeString(content);
		dest.writeLong(timeStamp);
	}

}
