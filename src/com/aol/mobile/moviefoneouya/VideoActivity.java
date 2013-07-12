package com.aol.mobile.moviefoneouya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import col.aol.mobile.moviefoneouya.R;

import com.aol.mobile.moviefoneouya.pojo.Video;

public class VideoActivity extends Activity {

	Video mVideo;
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		text = (TextView)findViewById(R.id.video_name);
	
		Intent intent = getIntent();
		processExtras(intent);
		text.setText(mVideo.videoName);
	}
	
	private void processExtras(Intent intent) {
		mVideo = intent.getParcelableExtra(Constants.VIDEO_BUNDLE_FLAG);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

}
