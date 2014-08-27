package com.kn.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ZhiHuNews implements Parcelable {
	
	private int id;
	private String thumbnail;		// 缩略图
	private String ga_prefix;		// 
	private String image_source;
	private String title;
	private String url;
	private String image;
	private String share_url;
	
	public ZhiHuNews(int id, String thumbnail, String ga_prefix, String image_source, String title, String url, String image, String share_url) {
		this.id = id;
		this.thumbnail = thumbnail;
		this.ga_prefix = ga_prefix;
		this.title = title;
		this.image = image;
		this.image_source = image_source;
		this.share_url = share_url;
		this.url = url;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getGa_prefix() {
		return ga_prefix;
	}

	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}

	public String getImage_source() {
		return image_source;
	}

	public void setImage_source(String image_source) {
		this.image_source = image_source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(ga_prefix);
		dest.writeString(image);
		dest.writeString(image_source);
		dest.writeString(thumbnail);
		dest.writeString(share_url);
		dest.writeString(title);
		dest.writeString(url);
	}

}
