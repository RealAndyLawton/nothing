package com.aol.mobile.moviefoneouya.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.aol.mobile.moviefoneouya.BusProvider;
import com.aol.mobile.moviefoneouya.Constants;
import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.api.MoviefoneApi;
import com.aol.mobile.moviefoneouya.api.transactions.GetVideosTransaction.VideosResponseEvent;
import com.aol.mobile.moviefoneouya.pojo.Video;
import com.squareup.otto.Subscribe;

public class MainActivity extends Activity implements VideoListFragment.VideosListener {

	private final static String TAG = MainActivity.class.getSimpleName();
	
	public VideoListFragment mVideoListFragment;
	
	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_main);
		
		BusProvider.getBusInstance().register(this);
		
		if(saved == null) {
			mVideoListFragment = new VideoListFragment();
			getFragmentManager().beginTransaction().replace(R.id.main_fragment, mVideoListFragment).commit();
		}
		
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
		mVideoListFragment.addVideos(event.getHandler().getVideos());
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onVideoItemClicked(Video video) {
		Intent intent = new Intent(this, VideoActivity.class);
		intent.putExtra(Constants.VIDEO_BUNDLE_FLAG, video);
		startActivity(intent);
	}

	@Override
	public void onFirstVideoPageLoaded(List<Video> videos) {
		// TODO Auto-generated method stub
		
	}

}
