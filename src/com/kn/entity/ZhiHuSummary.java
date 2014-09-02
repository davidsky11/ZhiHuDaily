package com.kn.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ZhiHuSummary implements Parcelable {
	
	private String id;
	private String imageUrl;
	private String title;
	private String desc;
	
	public ZhiHuSummary() {
		
	}
	
	public ZhiHuSummary(ZhiHuSummary zh) {
		this.imageUrl = zh.imageUrl;
		this.title = zh.title;
		this.desc = zh.desc;
	}
	
	private ZhiHuSummary(Parcel dest) {
		readFromParcel(dest);
	}

	public static final Parcelable.Creator<ZhiHuSummary> CREATOR = new Creator<ZhiHuSummary>() { 
		
        public ZhiHuSummary createFromParcel(Parcel dest) {  
            return new ZhiHuSummary(dest);  
        }
        
        public ZhiHuSummary[] newArray(int size) {  
            return new ZhiHuSummary[size];  
        }  
    };  
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public void readFromParcel(Parcel dest) {
		imageUrl = dest.readString();
		title = dest.readString();
		desc = dest.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(imageUrl);
		dest.writeString(title);
		dest.writeString(desc);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
