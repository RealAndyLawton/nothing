/**
 * 
 */
package col.aol.mobile.moviefoneouya.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alvaro Pereda
 * 
 */
public class Poster implements Parcelable {
	public String mPosterUrl;
	public String mTitle;

	public String mMpaaRating;
	public String mRunTime;
	public String mOpeningDate;
	public String mDvdReleaseDate;
	public String mBoxOffice;

	public Poster() {
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mPosterUrl);
		dest.writeString(mTitle);
		dest.writeString(mMpaaRating);
		dest.writeString(mRunTime);
		dest.writeString(mOpeningDate);
		dest.writeString(mDvdReleaseDate);
		dest.writeString(mBoxOffice);
	}

	public Poster(Parcel in) {
		mPosterUrl = in.readString();
		mTitle = in.readString();
		mMpaaRating = in.readString();
		mRunTime = in.readString();
		mOpeningDate = in.readString();
		mDvdReleaseDate = in.readString();
		mBoxOffice = in.readString();
	}

	public static final Parcelable.Creator<Poster> CREATOR = new Parcelable.Creator<Poster>() {
		@Override
		public Poster createFromParcel(Parcel source) {
			return new Poster(source);
		}

		@Override
		public Poster[] newArray(int size) {
			return new Poster[size];
		}
	};
}
