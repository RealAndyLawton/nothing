package com.aol.mobile.moviefoneouya;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aol.mobile.moviefoneouya.api.MoviefoneApi;
import com.aol.mobile.moviefoneouya.api.transactions.GetVideosTransaction.VideosResponseEvent;
import com.squareup.otto.Subscribe;

public class MainActivity extends Activity {
	
	private final static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		BusProvider.getBusInstance().register(this);
		
		requestVideos(1);
		
	}
	
	private void requestVideos(int page) {
		MoviefoneApi.requestVideos(this, "", page);
	}

	@Override
	protected void onDestroy() {
		BusProvider.getBusInstance().unregister(this);
		super.onDestroy();
	}
	
	@Subscribe
	public void onVideosResponseEvent(VideosResponseEvent event) {
		Log.d(TAG, "movies="+event.getmMovies());
	}

}
