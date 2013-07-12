package com.aol.mobile.moviefoneouya.pojo;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class CelebrityData extends Poster implements Parcelable {

	public String mId;
	public String mBirthDate;
	public String mBiography;
	public String mBiographyLong;
	public String mRoles;
	public String mSmallImageUrl;
	public String mUrl;
	public Boolean mIsDirector = false;
	public String mMovieId;
	public String mPhotoCount;

	public ArrayList<Movie> mFilmography;
	public ArrayList<String> mBiographyEntities = new ArrayList<String>();

	public boolean hasDetail() {
		return mFilmography == null ? false : true;
	}

	public void setId(String id) {
		mId = id;
	}

	public void setName(String name) {
		mTitle = name;
	}

	public void setBirthDate(String date) {
		mBirthDate = date;
	}

	public void setBio(String bio) {
		mBiography = bio;
	}

	public void setImageUrl(String url) {
		mPosterUrl = url;
	}

	public String getName() {
		return mTitle;
	}

	public String getId() {
		return mId;
	}

	public String getBirthDate() {
		return mBirthDate;
	}

	public String getBio() {
		return mBiography;
	}

	public String getImageUrl() {
		return mPosterUrl;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setPosterUrl(String url) {
//		if (!url.contains("missing-image"))
//			mPosterUrl = StringUtil.extractLastURL(url);
	}
	
	public void setUrl(String url) {
		mUrl = url;
	}

	public String getSmallImageUrl() {
		return mSmallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		mSmallImageUrl = smallImageUrl;
	}

	public CelebrityData() {
	}

	public String getBiographyWithNewlines() {
		if (TextUtils.isEmpty(mBiographyLong)
				|| mBiographyEntities == null) {
			return "";
		}
		String result = mBiographyLong.replaceAll("\n", "<br/>");
		return result;
	}


	/********************* Implements Parcelable ***************/

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mId);
		dest.writeString(mBirthDate);
		dest.writeString(mBiography);
		dest.writeString(mBiographyLong);
		dest.writeString(mRoles);
		dest.writeString(mSmallImageUrl);
		dest.writeString(mUrl);
		dest.writeString(mMovieId);
		dest.writeString(mPhotoCount);
		dest.writeStringList(mBiographyEntities);
		dest.writeInt(mIsDirector == true ? 1 : 0);
	}

	public CelebrityData(Parcel in) {
		super(in);
		mId = in.readString();
		mBirthDate = in.readString();
		mBiography = in.readString();
		mBiographyLong = in.readString();
		mRoles = in.readString();
		mSmallImageUrl = in.readString();
		mUrl = in.readString();
		mMovieId = in.readString();
		mPhotoCount = in.readString();
		in.readStringList(mBiographyEntities);

		mIsDirector = (in.readInt() == 1) ? true : false;

	}

	public static final Parcelable.Creator<CelebrityData> CREATOR = new Parcelable.Creator<CelebrityData>() {
		@Override
		public CelebrityData createFromParcel(Parcel source) {
			return new CelebrityData(source);
		}

		@Override
		public CelebrityData[] newArray(int size) {
			return new CelebrityData[size];
		}
	};
}
