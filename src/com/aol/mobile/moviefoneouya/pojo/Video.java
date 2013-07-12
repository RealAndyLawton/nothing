package com.aol.mobile.moviefoneouya.pojo;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Video extends Poster implements Parcelable {

	public String activityId;
	public String activityType;
	public String movieId;
	public String ogid;
	// public String title; in super class
	public String description;
	public String link;
	// public String image; in super class
	public String author;
	public String publishDate;
	public String insertDateTime;
	public String movieTitle;
	public String videoName;// be careful, videoName is the same as title. title
							// attribute in xml is empty
	public String videoDescription;
	public String videoLink;
	public String mVideoId;
	public String length;
	public String mGenre;
	public double	mLengthSecs;
	public ArrayList<BitrateUrl>	mBitrateUrlList;

	public Video() {
	}

	/********************* Implements Parcelable ***************/
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(activityId);
		dest.writeString(activityType);
		dest.writeString(movieId);
		dest.writeString(ogid);
		dest.writeString(description);
		dest.writeString(link);
		dest.writeString(author);
		dest.writeString(publishDate);
		dest.writeString(insertDateTime);
		dest.writeString(movieTitle);
		dest.writeString(videoName);
		dest.writeString(videoDescription);
		dest.writeString(videoLink);
		dest.writeString(mVideoId);
		dest.writeString(length);
		dest.writeList(mBitrateUrlList);
	}

	@SuppressWarnings("unchecked")
	public Video(Parcel in) {
		super(in);
		activityId = in.readString();
		activityType = in.readString();
		movieId = in.readString();
		ogid = in.readString();
		description = in.readString();
		link = in.readString();
		author = in.readString();
		publishDate = in.readString();
		insertDateTime = in.readString();
		movieTitle = in.readString();
		videoName = in.readString();
		videoDescription = in.readString();
		videoLink = in.readString();
		mVideoId = in.readString();
		length = in.readString();
		mBitrateUrlList = in.readArrayList(BitrateUrl.class.getClassLoader());
	}

	public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
		@Override
		public Video createFromParcel(Parcel source) {
			return new Video(source);
		}

		@Override
		public Video[] newArray(int size) {
			return new Video[size];
		}
	};

	public void setPosterUrl(String url) {
		// FIXME Implement using method in this class or child
//		mPosterUrl = StringUtil.extractLastURL(url);		
	}
}
