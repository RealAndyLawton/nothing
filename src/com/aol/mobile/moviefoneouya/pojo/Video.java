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

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getOgid() {
		return ogid;
	}

	public void setOgid(String ogid) {
		this.ogid = ogid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(String insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public String getVideoId() {
		return mVideoId;
	}

	public void setVideoId(String mVideoId) {
		this.mVideoId = mVideoId;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getmGenre() {
		return mGenre;
	}

	public void setGenre(String mGenre) {
		this.mGenre = mGenre;
	}

	public double getLengthSecs() {
		return mLengthSecs;
	}

	public void semLengthSecs(double mLengthSecs) {
		this.mLengthSecs = mLengthSecs;
	}

	public ArrayList<BitrateUrl> getBitrateUrlList() {
		return mBitrateUrlList;
	}

	public void setBitrateUrlList(ArrayList<BitrateUrl> mBitrateUrlList) {
		this.mBitrateUrlList = mBitrateUrlList;
	}
	
	
}
