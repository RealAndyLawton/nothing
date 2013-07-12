package com.aol.mobile.moviefoneouya.api.transactions;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aol.mobile.moviefoneouya.BusProvider;
import com.aol.mobile.moviefoneouya.api.MoviefoneApi;
import com.aol.mobile.moviefoneouya.api.handlers.VideoResponseHandler;
import com.aol.mobile.moviefoneouya.pojo.Movie;

public class GetVideosTransaction extends AsyncTask<Void, Void, Void> {
	
	public Context mContext;
	
	public static final String TYPE_TRAILERS = "type=Trailers";
	
	private static final String TAG = GetVideosTransaction.class.getSimpleName();

	@Override
	protected Void doInBackground(Void... params) {
		
		String videosUrlFormat = MoviefoneApi.MOVIEFONE_URL_BASE + "/all-trailers.xml?%s&page=%d";
		String url = String.format(videosUrlFormat, TYPE_TRAILERS, 1);
		
		StringRequest request = new StringRequest(Request.Method.GET, url, 
				
			new Response.Listener<String>() {
	
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					
					
					VideoResponseHandler responseHandler = new VideoResponseHandler();
					
					try {
						processXMLResponse(response, responseHandler);
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally {
						List<Movie> movies = responseHandler.getMovieList();
						if(movies != null) {
							produceVideosResponseEvent(movies);
						}
					}
					
				}
				
			},
			new Response.ErrorListener() {
	
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.d(TAG, error.getMessage());			
				}
				
			}
		);
		
		RequestQueue queue = Volley.newRequestQueue(mContext);
		queue.add(request);
		
		return null;
		
	}
	
	public void processXMLResponse(String response, DefaultHandler handler) throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		xr.setContentHandler(handler);
		InputSource s = new InputSource(new StringReader(response));
		xr.parse(s);

	}
	
	public class VideosResponseEvent {
		
		List<Movie> mMovies;

		public VideosResponseEvent(List<Movie> movies) {
			this.mMovies = movies;
		}

		public List<Movie> getmMovies() {
			return mMovies;
		}

		public void setmMovies(List<Movie> mMovies) {
			this.mMovies = mMovies;
		}
		
	}
	
	public void produceVideosResponseEvent(List<Movie> movies) {
		BusProvider.getBusInstance().post(new VideosResponseEvent(movies));
	}
	
}