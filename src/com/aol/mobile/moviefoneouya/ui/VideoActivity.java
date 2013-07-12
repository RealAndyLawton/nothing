package com.aol.mobile.moviefoneouya.ui;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.aol.mobile.moviefoneouya.Constants;
import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.pojo.BitrateUrl;
import com.aol.mobile.moviefoneouya.pojo.Video;

public class VideoActivity extends Activity {

	private static final String TAG = VideoActivity.class.getSimpleName();
	
	Video mVideo;
	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		text = (TextView)findViewById(R.id.video_name);
	
		processExtras(getIntent());
		text.setText(mVideo.videoName);
		
		playVideo();
	}
	
	private void processExtras(Intent intent) {
		if(intent.getExtras() != null) {
			setVideo((Video) intent.getParcelableExtra(Constants.VIDEO_BUNDLE_FLAG));
		}
	}
	
	private void playVideo() {
		
		BitrateUrl highestQualityBitrate = getVideo().getBitrateUrlList().get(0);
		new PlayVideoTask().execute(highestQualityBitrate.getUrl());
		
	}
	
	private class PlayVideoTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			
			String url = params[0];
			
			MediaPlayer mp = new MediaPlayer();
			try {
				mp.setDataSource(url);
				mp.prepare();
				mp.start();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
			
		}
		
	}

	public Video getVideo() {
		return mVideo;
	}

	public void setVideo(Video mVideo) {
		this.mVideo = mVideo;
	}

}
