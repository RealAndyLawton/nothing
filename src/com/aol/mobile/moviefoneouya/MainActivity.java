package com.aol.mobile.moviefoneouya;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import col.aol.mobile.moviefoneouya.R;

import com.aol.mobile.moviefoneouya.pojo.Video;

public class MainActivity extends Activity implements VideoListFragment.VideosListener {

	
	public Fragment mVideoListFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState == null) {
			mVideoListFragment = new VideoListFragment();
			getFragmentManager().beginTransaction().replace(R.id.main_fragment, mVideoListFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onFirstVideoPageLoaded(List<Video> videos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVideoItemClicked(Video video) {
		Intent intent = new Intent(this, VideoActivity.class);
		intent.putExtra(Constants.VIDEO_BUNDLE_FLAG, video);
		startActivity(intent);
	}

}
