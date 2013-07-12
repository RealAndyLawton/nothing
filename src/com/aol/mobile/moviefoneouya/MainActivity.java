package com.aol.mobile.moviefoneouya;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import col.aol.mobile.moviefoneouya.R;

public class MainActivity extends Activity {

	
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

}
