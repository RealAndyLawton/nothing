package com.aol.mobile.moviefoneouya.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.aol.mobile.moviefoneouya.BusProvider;
import com.aol.mobile.moviefoneouya.Constants;
import com.aol.mobile.moviefoneouya.Globals;
import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.api.MoviefoneApi;
import com.aol.mobile.moviefoneouya.api.transactions.GetVideosTransaction.VideosResponseEvent;
import com.aol.mobile.moviefoneouya.pojo.Video;
import com.squareup.otto.Subscribe;

public class MainActivity extends Activity implements VideoListFragment.VideosListener {

	private final static String TAG = MainActivity.class.getSimpleName();

	public VideoListFragment mVideoListFragment;
	public TrailerView mTrailerView;
	private Video currVideo;

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_main);
		
		BusProvider.getBusInstance().register(this);

		if(saved == null) {
			mVideoListFragment = (VideoListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);
			mTrailerView = (TrailerView) getFragmentManager().findFragmentById(R.id.video_fragment);
			//			mVideoListFragment = new VideoListFragment();
			//			TestFragment t = new TestFragment();
			//			getFragmentManager().beginTransaction().add(R.id.list_fragment, mVideoListFragment).commit();
			//			getFragmentManager().beginTransaction().add(R.id.video_fragment, t).commit();
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
		//		mTrailerView.setUpVideoView(event.getHandler().getVideos().get(0));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onVideoItemClicked(Video video) {
		if(!video.equals(currVideo)) {
			mTrailerView.startVideo(video);
			setCurrVideo(video);
		}
	}
	
	public void setCurrVideo(Video video) {
		this.currVideo = video;
		getActionBar().setTitle(video.movieTitle);
	}

	@Override
	public void onFirstVideoPageLoaded(List<Video> videos) {
		// TODO Auto-generated method stub

	}

}
