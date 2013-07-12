package com.aol.mobile.moviefoneouya.api.handlers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;
import android.util.Log;

import com.aol.mobile.moviefoneouya.pojo.BitrateUrl;
import com.aol.mobile.moviefoneouya.pojo.BitrateUrl.Quality;
import com.aol.mobile.moviefoneouya.pojo.Movie;
import com.aol.mobile.moviefoneouya.pojo.Video;

/**
 * Looks like the movie basic response, but some tricky fields, make the
 * MovieBasicResponse unusable for this response.
 * 
 */
public class VideoResponseHandler extends DefaultHandler {
	
	static final String					TAG				= "VideoResponseHandler";

	protected String							mCurrentElementData;

	protected Movie								mMovie;
	// do not parse this for Posters
	// protected CelebrityData mTempCelebrityData;
	protected ArrayList<Movie>					mMovies			= new ArrayList<Movie>();
	protected HashMap<String, ArrayList<Video>>	mVideos			= new HashMap<String, ArrayList<Video>>();

	protected int								mTotalCount;
	protected int								mPage;

	private boolean								isParsingMovie	= false;

	Video								mVideo;	
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		mCurrentElementData = "";
		if (localName.equalsIgnoreCase("movie")) {
			mMovie = new Movie();
			mVideo = new Video();
			isParsingMovie = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		mCurrentElementData += new String(ch, start, length);

	}

	public Movie getMovie() {
		return this.mMovie;
	}

	public ArrayList<Movie> getMovieList() {
		if (mVideos != null && !mVideos.isEmpty()) {
			for (Movie movie : mMovies) {
				movie.mVideos = mVideos.get(movie.mMovieId);
			}
		}
		return this.mMovies;
	}

	public int getTotalCount() {
		return this.mTotalCount > 0 ? mTotalCount : this.mMovies != null ? mMovies.size() : 0;
	}

	public int getPage() {
		return this.mPage == 0 ? 1 : this.mPage;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (localName.equalsIgnoreCase("movie")) {
			isParsingMovie = false;
			if (mVideos.get(mMovie.mMovieId) == null) {
				ArrayList<Video> videos;

				videos = mVideos.get(mMovie.mMovieId);

				if (videos == null) {
					videos = new ArrayList<Video>();
				}

				videos.add(mVideo);
				mMovies.add(mMovie);

				mVideos.put(mMovie.mMovieId, videos);
			}
		} else if (localName.equalsIgnoreCase("movieId")) {
			mMovie.mMovieId = mCurrentElementData;
			mVideo.movieId = mCurrentElementData;

		} else if (localName.equalsIgnoreCase("title") || localName.equalsIgnoreCase("movieTitle")) {
			// for top box movie, there is one extra title field before we parse
			// movie item.
			if (isParsingMovie) {
				mMovie.mTitle = mCurrentElementData;
			}
		
			mVideo.movieTitle = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("name")) {
			mVideo.mTitle = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("mpaaRating")) {
			if (mCurrentElementData.equalsIgnoreCase("Not Rated")
					|| mCurrentElementData.equalsIgnoreCase("Not Yet Rated")) mCurrentElementData = "NR";
			mMovie.mMpaaRating = mCurrentElementData;
			mVideo.mMpaaRating = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("userReviewScore")) {
			mMovie.mUserReviewScore = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("criticReviewScore") || localName.equalsIgnoreCase("reviewScore")) {
			mMovie.mCriticReviewScore = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("poster") || localName.equalsIgnoreCase("imageUrl")) {
			mMovie.setPosterUrl(mCurrentElementData);
			mVideo.mPosterUrl = mMovie.mPosterUrl;
		} else if (localName.equalsIgnoreCase("runTime")) {
			mMovie.mRunTime = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("url") || localName.equalsIgnoreCase("mainPageUrl")) {
			mMovie.mURL = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("openingDate")) {
//			mMovie.mOpeningDate = Utils.getStandardDateString(mCurrentElementData);
			mVideo.mOpeningDate = mMovie.mOpeningDate;
		} else if (localName.equalsIgnoreCase("showtimesUrl")) {
			mMovie.mShowtimesUrl = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("editorBoost")) {
			mMovie.mEditorBoost = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("synopsis")) {
			mMovie.mSynopsis = mCurrentElementData;
//			mMovie.mSynopsis = Utils.convertEntities(mMovie.mSynopsis);
			String[] entityList = new String[] { "<i>", "<I>", "</i>", "</I>", "<b>", "<B>", "</b>", "</B>", "<u>",
					"<U>", "</u>", "</U>" };
//			mMovie.mSynopsis = Utils.replaceSubstrings(mMovie.mSynopsis, entityList, "");
			if (!TextUtils.isEmpty(mMovie.mSynopsis)) {
				// mMovie.hasDetail = true;
				mMovie.mSynopsis = mMovie.mSynopsis.trim();
			}

		} else if (localName.equalsIgnoreCase("mediaId")) {
			mVideo.mVideoId = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("extraAttributes")) {
			parseBitrateUrls();
		}
		// Set category in MoviesMainListing rather than here
		else if (localName.equalsIgnoreCase("theatricalReleaseDate")) {
			// this only shows up in in-theater movie
			// mMovie.mCategory = Constants.MOVIES_IN_THEATER;
		} else if (localName.equalsIgnoreCase("showtimes")) {
			// this shows up in both in-theater and coming-soon movie
			// if we set this before we parse the "theatricalReleaseDate", it
			// will be overwrite later.
			// if (mMovie.mCategory != Constants.MOVIES_IN_THEATER)
			// mMovie.mCategory = Constants.MOVIES_IN_UPCOMING;
			mMovie.mShowtimeString = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("dvdReleaseDate")) {
//			mMovie.mDvdReleaseDate = Utils.getStandardDateString(mCurrentElementData);
		} else if (localName.equalsIgnoreCase("boxoffice")) {
			// mMovie.mCategory = Constants.MOVIES_IN_TOP_BOX;
			mMovie.mBoxOffice = mCurrentElementData;
		} else if (localName.equalsIgnoreCase("totalCount")) {
			try {
				mTotalCount = Integer.parseInt(mCurrentElementData);
			} catch (Exception e) {

			}
		} else if (localName.equalsIgnoreCase("page") || localName.equalsIgnoreCase("pageParam")) {
			try {
				mPage = Integer.parseInt(mCurrentElementData);
			} catch (Exception e) {

			}
		}

		// TODO:parse other attributes later

	}
	
	protected Quality getQuality(JSONArray item) throws JSONException {
		try {
			return BitrateUrl.Quality.valueOf(item.getString(0));
		} catch (IllegalArgumentException ignore) {
			return null;
		}
	}

	protected void parseBitrateUrls() {
		
		try {
			JSONArray array;
	
			JSONObject object = new JSONObject(mCurrentElementData);
			JSONObject string = new JSONObject(object.getString("string"));
			JSONObject map = string.getJSONObject("map");
			array = map.getJSONArray("entry");
			mVideo.mBitrateUrlList = new ArrayList<BitrateUrl>();
	
			for (int i = 0; i < array.length(); i++) {
				JSONArray item;
				Quality quality;
	
				item = array.getJSONObject(i).getJSONArray("string");
				quality = getQuality(item);
	
				if (quality != null) {
					BitrateUrl video = new BitrateUrl();
					video.mType = quality;
					video.mUrl = item.getString(5);
					video.mBitrate = item.getInt(2);
					mVideo.mBitrateUrlList.add(video);
				}
	
			}
	
		} catch (JSONException e) {
			Log.d(TAG, e.getLocalizedMessage(), e);
		}
		
	}
}
