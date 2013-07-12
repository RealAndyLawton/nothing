package com.aol.mobile.moviefoneouya.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

// Movie class
// Contains data for Movie List. No setters/getters
// 12/07/09 Added Theater List to movies.  For showing 1 movie and list of showtimes
public class Movie extends Poster implements Parcelable {

	public final static String	MOVIE_ID		= "movie_id";

	public static final int		DEFAULT_LIKE	= -1;

	/**
	 * Movie category. See categories in Constants. This value is set in
	 * {@link MovieBasicResponseHandler}
	 */
	public int					mCategory;
	/**
	 * This indicates whether the movie has the detail information, helps to
	 * update the movie hashmap properly
	 */
	public boolean				hasDetail		= false;

	// 1.in-theater.xml 2.coming-soon.xml 3.dvd.xml 4.box-office.xml
	public String				mMovieId;						// (1,2,3,4)
																// means this
																// attribute(not
																// exactly, id
																// actually)
																// shows up in
																// all 4 xml
																// results
																// public String
																// mMpaaRating;//
																// (1,2,3,4) add
																// to
																// Poster
	public String				mUserReviewScore;				// (1,2,3,4)
	// public String mRunTime;// (1,2,3,4) add to poster
	// posterUrl in super class: imageUrl in (1,2,3), poster in 4
	public String				mURL;							// mainPageUrl
																// in (1,2,3),
																// url in 4
	// public String mOpeningDate; // (1,2,4)
	// public String mShowtimes;// (1,2)
	public String				mFormatDescription;			// (1,2)
	public String				mTrailerUrl;					// (1,3)
	public String				mCriticReviewScore;			// criticReviewScore
																// in (1,3),
																// reviewScore
																// in 4
	public String				mShowtimesUrl;					// (1,2)
	public String				mTheatricalReleaseDate;		// (1)
	public String				mEditorBoost;					// (1)
	public String				mMovieCalculateBoost;			// (1)
	// public String mDvdReleaseDate;// (3)
	public String				mNetflixId;					// (3)
	// the following are all for box_office.xml
	// movieId in box_office.xml is actually id in other xml, reviewScore is
	// criticReviewScore
	public String				mMovieSlug;
	public String				mShowtimeCount;
	public String				mVideoCount;
	public String				mSynopsis;
	public String				mStarjson;
	public String				mPosterjson;
	public String				mGenres;
	public String				mArticle;						// be careful,
																// json
																// object:json,link,title

	public String				mFandangoMovieId;
	public String				mShowtimeString;				// this is used
																// to display
																// showtimes for
																// a
																// theater
	public boolean				mIsTickAvailable;
	public int					mFbLikeCount	= DEFAULT_LIKE;
	public String				mProductionYear;

	public ArrayList<Video>		mVideos;
	public String				mMpaaReason;

	// public String mLanguage;
	// public String mBlockDvd;
	// public String mHasSoundTrack;
	// public String mSearchTitle;
	// public String mKeywords;
	// public String mSynonyms;
	// public String mAwardCount;
	// public String mMostRecentAward;
	// public String mLastUpdate;
	// public String mPhotoCount;
	// public String mLastUpdateSource;
	// public String mProductionStudio;
	// public String mReleaseStudio;
	// public String mTicketId;
	// public String mFanMovieId;

	// mainUrl
	//
	// themes
	// tones
	// subgenres
	// primaryGenre
	// synopsisShort
	// amgMovieId
	// originCountry
	// mostRecentNews
	// newsStoryCount
	// reviewCount //reviewscore
	// mostRecentReview
	// castCount
	// productionYear
	// releaseYear
	// blockbusterId
	// netflixUrl

	public static interface RequestListener {
		public void onError(Exception e);

		public void onComplete();
	}

	// currently not used, but will probably be needed in near future.
	public ArrayList<CelebrityData>	mSelectedStars		= new ArrayList<CelebrityData>();
	public ArrayList<CelebrityData>	mSelectedDirector	= new ArrayList<CelebrityData>();
	public String					mTicketingUrl;											// ticketingUrl
//	public ArrayList<Theater>		mTheaters;
	public Integer					mLikeCount;

	public String					mState;

	public HashSet<String>			mInTheater;

	public String getFullTitle() {
		StringBuffer sb = new StringBuffer(mTitle);

		if (!TextUtils.isEmpty(mMpaaRating)) {
			sb.append(" (").append(mMpaaRating);

			if (!TextUtils.isEmpty(mRunTime)) {
				sb.append(", ").append(mRunTime).append(" min");
			}

			sb.append(")");
		} else if (!TextUtils.isEmpty(mRunTime)) {
			sb.append(" (").append(mRunTime).append(" min)");
		}

		return sb.toString();
	}

	public String getCastsStr() {
		if (mSelectedStars == null || mSelectedStars.isEmpty()) return "";
		StringBuffer str = new StringBuffer();
		int i = 0;
		for (CelebrityData celeb : mSelectedStars) {
			if (i == 0) {
				str.append(celeb.mTitle);
			} else {
				str.append(", " + celeb.mTitle);
			}
			i++;
		}

		return str.toString();
	}

	public String getCasts() {

		if (mSelectedStars == null || mSelectedStars.isEmpty()) return "";
		StringBuffer str = new StringBuffer();
		int i = 0;
		for (CelebrityData celeb : mSelectedStars) {
			if (i == 0) {
				str.append(celeb.mTitle);
			} else {
				str.append("\n" + celeb.mTitle);
			}
			i++;
		}

		return str.toString();
	}

	public String getDirectors() {
		if (mSelectedDirector == null || mSelectedDirector.isEmpty()) { return ""; }
		StringBuffer str = new StringBuffer();
		int i = 0;
		for (CelebrityData celeb : mSelectedDirector) {
			if (i == 0) {
				str.append(celeb.mTitle);
			} else {
				str.append("\n" + celeb.mTitle);
			}
			i++;
		}

		return str.toString();
	}

	public boolean hasShowtimes() {
//		if (Utils.notNullAndNotEmpty(mOpeningDate) && (!TextUtils.isEmpty(mShowtimeString))
//				&& TextUtils.isEmpty(mDvdReleaseDate)) {
//
//			try {
//				long openingDate = Utils.getDateFromStandardString(mOpeningDate).getTime();
//				long nextWeek = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;
//
//				return openingDate < nextWeek;
//			} catch (Exception e) {
//				return false;
//			}
//		} else {
//			return false;
//		}
		return false;
	}

	/**
	 * This method is called for 4 main movie lists: in theater, on DVD, coming
	 * soon, top box office
	 * 
	 * @param movies
	 * @param category
	 */
	public static void setCategory(List<Movie> movies, int category) {
		if (movies == null) return;
		for (Movie m : movies)
			m.mCategory = category;
	}

	public int getCriticReviewStar() {
//		if (TextUtils.isEmpty(mCriticReviewScore)) return 0;
//		int score = Integer.valueOf(mCriticReviewScore);
//		return Utils.scoreToStarNum(score);
		return 1;
	}

	public int getUserReviewStar() {
//		if (TextUtils.isEmpty(mUserReviewScore)) return 0;
//		int score = (int) (Double.valueOf(mUserReviewScore) * 100);
//		return Utils.scoreToStarNum(score);
		return 1;
	}

	public String getReview() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
			if (this.mCriticReviewScore != null) {
				sb.append("<B>Critics Review Score: </B>");
				sb.append(getCriticReviewStar() + "/5;<br>");
			}
			if (this.mUserReviewScore != null) {
				sb.append("<B>User Review Score: </B>");
				sb.append(getUserReviewStar() + "/5;");
			}
		}
		return sb.toString();
	}

	public String getRatingRuntime() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
			if (!TextUtils.isEmpty(this.mMpaaRating) || !TextUtils.isEmpty(this.mRunTime)) {
				boolean prevDataFieldAvail = false;
				sb.append("(");
				if (!TextUtils.isEmpty(this.mMpaaRating)) {
					sb.append(this.mMpaaRating);
					prevDataFieldAvail = true;
				}
				if (!TextUtils.isEmpty(this.mRunTime)) {
					if (prevDataFieldAvail) {
						sb.append(", ");
					}
					sb.append(this.mRunTime);
					sb.append(" min.");
				}
				sb.append(")");
			}
		}

		return sb.toString();
	}

	public String getReleaseDateLine() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
//			String date = Utils.getShortDateString(this.mOpeningDate);
//			if (!TextUtils.isEmpty(date)) {
//				sb.append("<B>Release Data: </B>");
//				sb.append(date);
//			}
		}

		return sb.toString();
	}

	public String getCastLine() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
			String cast = this.getCastsStr();
			if (!TextUtils.isEmpty(cast)) {
				sb.append("<B>Cast: </B>");
				sb.append(cast);
			}
		}

		return sb.toString();
	}

	public String getFacebookLine() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
			if (this.mLikeCount != null && this.mLikeCount.intValue() > 0) {
				sb.append("<B>Facebook: </B>");
				String facebookCount;
				if (this.mLikeCount.intValue() == 1) {
					facebookCount = "One person likes this";
				} else {
					facebookCount = String.format("%d people like this", this.mLikeCount.intValue());
				}
				sb.append(facebookCount);
			}
		}

		return sb.toString();
	}

	public String getSynopsisLine() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
			if (!TextUtils.isEmpty(this.mSynopsis)) {
				sb.append("<B>Synopsis: </B>");
				sb.append(this.mSynopsis);
			}
		}

		return sb.toString();
	}

	public String getUrl() {
		StringBuilder sb = new StringBuilder("");
		if (this != null) {
			if (!TextUtils.isEmpty(this.mURL)) {
				sb.append(this.mURL);
			}
		}
		return sb.toString();
	}

	public Movie() {

	}

	/**
	 * Create this method for ByTimeFragment. Since we are showing different
	 * showtimes for ByMovieFragment and ByTimeFragment, we don't want to revise
	 * the original data.
	 * 
	 * @return
	 */
	public Movie getCopy() {
		Movie movie = new Movie();
		movie.mMovieId = this.mMovieId;
		movie.mTitle = this.mTitle;
		movie.mPosterUrl = this.mPosterUrl;
		movie.mShowtimeString = this.mShowtimeString;
		movie.mRunTime = this.mRunTime;
		movie.mMpaaRating = this.mMpaaRating;
		movie.mFandangoMovieId = this.mFandangoMovieId;
		return movie;
	}

	/**
	 * When we open the movie from movie search results, it doesn't have
	 * category info. We need to assign category to it.
	 */
	public int getCategory() {
//		if (mCategory == Constants.MOVIES_UNCATEGORIZED) {// 0 by default
//			if (!TextUtils.isEmpty(mBoxOffice)) {
//				mCategory |= Constants.MOVIES_TOP_BOX_OFFICE;
//			}
//			if (!TextUtils.isEmpty(mDvdReleaseDate) && Utils.getDiffInDays(mDvdReleaseDate) <= 0) {
//				mCategory |= Constants.MOVIES_ON_DVD;
//			}
//			if (!TextUtils.isEmpty(mOpeningDate)) {
//				if (Utils.isComingSoon(mOpeningDate)) {
//					mCategory |= Constants.MOVIES_COMING_SOON;
//				} else if (Utils.isInTheater(mOpeningDate)) {
//					mCategory |= Constants.MOVIES_IN_THEATER;
//				}
//			}
//		}
		return mCategory;
	}
	
	
	

	/******** Implement Parcelable *******************************/

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(mCategory);
		dest.writeInt((hasDetail == true) ? 1 : 0);
		dest.writeString(mMovieId);
		dest.writeString(mUserReviewScore);
		dest.writeString(mURL);
		// dest.writeString(mShowtimes);
		dest.writeString(mTrailerUrl);
		dest.writeString(mCriticReviewScore);
		dest.writeString(mShowtimesUrl);
		dest.writeString(mTheatricalReleaseDate);
		dest.writeString(mShowtimeCount);
		dest.writeString(mVideoCount);
		dest.writeString(mSynopsis);
		dest.writeString(mGenres);
		dest.writeString(mShowtimeString);
		dest.writeInt((mIsTickAvailable == true) ? 1 : 0);
		dest.writeInt(mFbLikeCount);
		dest.writeString(mProductionYear);
		dest.writeString(mFandangoMovieId);
		dest.writeString(mMpaaReason);
	}

	public Movie(Parcel in) {
		super(in);
		mCategory = in.readInt();
		hasDetail = (in.readInt() == 1) ? true : false;
		mMovieId = in.readString();
		mUserReviewScore = in.readString();
		mURL = in.readString();
		// mShowtimes = in.readString();
		mTrailerUrl = in.readString();
		mCriticReviewScore = in.readString();
		mShowtimesUrl = in.readString();
		mTheatricalReleaseDate = in.readString();
		mShowtimeCount = in.readString();
		mVideoCount = in.readString();
		mSynopsis = in.readString();
		mGenres = in.readString();
		mShowtimeString = in.readString();
		mIsTickAvailable = (in.readInt() == 1) ? true : false;
		mFbLikeCount = in.readInt();
		mProductionYear = in.readString();
		mFandangoMovieId = in.readString();
		mMpaaReason = in.readString();

	}

	public static final Parcelable.Creator<Movie>	CREATOR	= new Parcelable.Creator<Movie>() {
																@Override
																public Movie createFromParcel(Parcel source) {
																	return new Movie(source);
																}

																@Override
																public Movie[] newArray(int size) {
																	return new Movie[size];
																}
															};

	@Override
	public String toString() {
		return "Movie [hasDetail=" + hasDetail + ", mTitle=" + mTitle + ", mMovieId=" + mMovieId + ", mSynopsis="
				+ mSynopsis + ", mStarjson=" + mStarjson + ", mPosterjson=" + mPosterjson + ", mShowtimeString="
				+ mShowtimeString + ", mIsTickAvailable=" + mIsTickAvailable + ", mSelectedStars=" + mSelectedStars
				+ ", mSelectedDirector=" + mSelectedDirector + ", mTicketingUrl=" + mTicketingUrl + ", mPosterUrl=" + mPosterUrl + "]";
	}

	public void setPosterUrl(String url) {
//		if (!TextUtils.isEmpty(url) && !url.contains("0000_p_m")) mPosterUrl = StringUtil.extractLastURL(url);
	}

}
