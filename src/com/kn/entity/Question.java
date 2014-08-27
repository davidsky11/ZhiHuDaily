package com.kn.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
	
	private int id;
	private int questionId;
	private int answerId;
	private String title;
	private String content;
	private String description;
	private String username;
	private String updateAt;
	
	private boolean stared = false;
	private boolean unread = true;
	
	public Question(int id, int questionId, int answerId, String title, String content, String description, String username, String updateAt, boolean stared, boolean unread) {
		this.id = id;
		this.questionId = questionId;
		this.answerId = answerId;
		this.title = title;
		this.content = content;
		this.description = description;
		this.username = username;
		this.updateAt = updateAt;
		this.stared = stared;
		this.unread = unread;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isStared() {
		return stared;
	}

	public void setStared(boolean stared) {
		this.stared = stared;
	}

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(questionId);
		dest.writeInt(answerId);

		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(description);
		dest.writeString(username);
		dest.writeString(updateAt);

		dest.writeInt(stared ? 1 : 0);
		dest.writeInt(unread ? 1 : 0);
	}

}
