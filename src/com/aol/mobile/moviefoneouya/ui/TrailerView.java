package com.aol.mobile.moviefoneouya.ui;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aol.mobile.core.logging.Logger;
import com.aol.mobile.core.util.StringUtil;
import com.aol.mobile.moviefoneouya.Globals;
import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.pojo.BitrateUrl;
import com.aol.mobile.moviefoneouya.pojo.Video;

public class TrailerView extends Fragment {

	//	interface onCompleteListener{
	//		abstract void onComplete();
	//	}
	//	
	//	public onCompleteListener mListener;
	//	
	//	public void setOnCompleteListener(onCompleteListener listener){
	//		mListener = listener;
	//	}


	@Deprecated
	public static final String TRAILER_BITRATE_URL_LIST = "com.aol.mobile.moviefonetogo.ui.TrailerView.trailerBitrateUrlList";

	private static final String TAG = "TrailerView";

	private View view;
	private static final int FADE_OUT = 1;
	private static final int SHOW_PROGRESS = 2;
	private static final int sDefaultTimeout = 3000;

	private VideoView mVideoView = null;
	private View mBottomPanel = null;

	private ImageButton mRewButton = null;
	private ImageButton mFwdButton = null;
	private ImageButton mPausePlayButton = null;
	private Button mHqButton = null;
	private TextView mEndTime, mCurrentTime;
	private ProgressBar mProgress;
	private boolean mShowing;
	private boolean mDragging;
	private StringBuilder mFormatBuilder;
	private Formatter mFormatter;
	private ArrayList<BitrateUrl> mBitrateUrlList;
	private ArrayList<TrailerSelectionList> mTrailerQualitySelectionList = new ArrayList<TrailerSelectionList>();




	private Video mVideo = null;
	private String mUrl = null;
	private ProgressDialog mProgressDialog;

	private static class TrailerSelectionList {
		public String mName;
		public int mBitrateUrlListIndex; // index of the actual BitrateUrl to
		// which this selectionListItem
		// corresponds to
	}

	private MediaPlayer.OnErrorListener mVideoViewErrorListener = new MediaPlayer.OnErrorListener() {
		public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
			if (Logger.D) {
				Logger.d(TAG, "Error with VideoView: framework_err="
						+ framework_err + " impl_err=" + impl_err);
			}
			String defaultName = Globals.getDefaultTrailerQuality();
			int index = findQualityPosition(defaultName);
			if (index > 0) {
				index--;
				String message = getResources()
						.getString(
								R.string.unable_to_play_video_fallback_to_lower_quality);
				Toast.makeText(getActivity(), message, Toast.LENGTH_LONG)
				.show();
				String name = mTrailerQualitySelectionList.get(index).mName;
				mHqButton.setText(name);
				Globals.setDefaultTrailerQuality(name);
				switchVideoMode();
			} else {
				return false;
			}
			return true;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//		Bundle extra = getIntent().getExtras();
		//
		//		if (extra != null) {
		//			
		//		}

	}


	public void startVideo(Video video) {
		showProgressDialog();
		mVideo = video;
		//		if (mVideo != null && !StringUtil.isNullOrEmpty(mVideo.videoLink)) {
		//			mUrl = mVideo.videoLink;
		//			setContentView(R.layout.videoview);
		//			createView();
		//		} else
		//			finish();
		mBitrateUrlList.clear();
		mBitrateUrlList = mVideo.mBitrateUrlList;
		if (mBitrateUrlList != null && mBitrateUrlList.size() > 0) {
			updateSelectionList();
			mUrl = getUrlToPlay();
			switchVideo();
		} else {
			//			finish();
		}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.videoview, container, false);
		mBitrateUrlList = new ArrayList<BitrateUrl>();
		createView(view);

		return view;
	}

	private void createView(View v) {
		mVideoView = (VideoView) v.findViewById(R.id.surface_view);

		if (mVideoView == null)
			return;
		mVideoView.setOnErrorListener(mVideoViewErrorListener);

		mBottomPanel = (LinearLayout) v.findViewById(R.id.bottom_panel);
		if (mBottomPanel == null)
			return;

		mRewButton = (ImageButton) v.findViewById(R.id.rew);
		if (mRewButton != null)
			mRewButton.setOnClickListener(onRewind);

		mFwdButton = (ImageButton) v.findViewById(R.id.ffwd);
		if (mFwdButton != null)
			mFwdButton.setOnClickListener(onForward);

		mPausePlayButton = (ImageButton) v.findViewById(R.id.pause);
		if (mPausePlayButton != null)
			mPausePlayButton.setOnClickListener(onPausePlay);

		mHqButton = (Button) v.findViewById(R.id.hq);
		if (mHqButton != null) {
			mHqButton.setText(Globals.getDefaultTrailerQuality());
			mHqButton.setOnClickListener(onHQ);
			if (mBitrateUrlList.size() > 1) {
				mHqButton.setVisibility(View.VISIBLE);
			} else {
				mHqButton.setVisibility(View.GONE);
			}
		}

		mEndTime = (TextView) v.findViewById(R.id.time);
		mCurrentTime = (TextView) v.findViewById(R.id.time_current);
		mProgress = (SeekBar) v.findViewById(R.id.mediacontroller_progress);
		if (mProgress != null) {
			if (mProgress instanceof SeekBar) {
				SeekBar seeker = (SeekBar) mProgress;
				seeker.setOnSeekBarChangeListener(onSeekBar);
			}
			mProgress.setMax(1000);
		}

		// when steam is done playing, go back to previous view
		mVideoView.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				try {
					Thread.sleep(500); // wait for 500ms before closing the
					// trailer view

				} catch (Exception ex) {
				}
				//				setResult(Activity.RESULT_OK);
				//				finish();
			}
		});

		mVideoView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					updateShowHideMediaPanel();
				}
				return false;
			}
		});

		mVideoView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				updateShowHideMediaPanel();
			}
		});

		mFormatBuilder = new StringBuilder();
		if (mFormatBuilder != null) {
			mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
		}

		//		showProgressDialog(R.string.loading_please_wait);
	}

	private void updateShowHideMediaPanel() {
		if (mShowing) {
			hide();
		} else {
			show(sDefaultTimeout);
		}
	}

	private void switchVideoMode() {
		String url = getUrlToPlay();
		if (!StringUtil.isNullOrEmpty(url)) {
			// pause the
			mVideoView.pause();
			int position = mVideoView.getCurrentPosition();
			mVideoView.stopPlayback();
			mVideoView.setVideoPath(url);
			mVideoView.seekTo(position);
			setProgress();

			// show spinner while the trailer is getting loaded
			mVideoView.setOnPreparedListener(new OnPreparedListener() {
				public void onPrepared(MediaPlayer mp) {
					//					dismissProgressDialog(R.string.loading_please_wait);
					// NOTE: Not sure why we need to do the following but it seems
					// to fix the blank video screen
					// issue when playing video on HTC EVO device specifically
					mVideoView.setVisibility(View.VISIBLE);
					hideProgressDialog();
					mVideoView.start();
				}
			});

			//			showProgressDialog(R.string.loading_please_wait);
			mVideoView.start();
		}
	}

	public void switchVideo() {
		String url = getUrlToPlay();
		if (!StringUtil.isNullOrEmpty(url)) {
			// pause the
			mVideoView.pause();
			mVideoView.stopPlayback();
			mVideoView.setVideoPath(url);
			mVideoView.seekTo(0);
			setProgress();

			// show spinner while the trailer is getting loaded
			mVideoView.setOnPreparedListener(new OnPreparedListener() {
				public void onPrepared(MediaPlayer mp) {
					//					dismissProgressDialog(R.string.loading_please_wait);
					// NOTE: Not sure why we need to do the following but it seems
					// to fix the blank video screen
					// issue when playing video on HTC EVO device specifically
					mVideoView.setVisibility(View.VISIBLE);
					hideProgressDialog();
					mVideoView.start();
				}
			});

			//			showProgressDialog(R.string.loading_please_wait);
			// mVideoView.start();
		}
	}

	private void showProgressDialog() {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle(R.string.loading_please_wait);
		mProgressDialog.show();
	}

	private void hideProgressDialog() {
		if(mProgressDialog != null) {
			mProgressDialog.dismiss();
		}

	}

	private String getUrlToPlay() {
		String url = null;
		String defaultName = Globals.getDefaultTrailerQuality();

		url = getVideoWithQuality(defaultName);

		if (StringUtil.isNullOrEmpty(url)) {
			Globals.setDefaultTrailerQuality(BitrateUrl.Quality.LDBASELINE.toString());
			url = getVideoWithQuality(BitrateUrl.Quality.LDBASELINE.toString());	
		}
		if (StringUtil.isNullOrEmpty(url)) {
			url = mBitrateUrlList.get(0).mUrl;
			Globals.setDefaultTrailerQuality(mBitrateUrlList.get(0).mType.toString());
		}

		return url;
	}

	private String getVideoWithQuality(String defaultName) {
		for (BitrateUrl biturl:mBitrateUrlList) {
			if (biturl.mType.toString().equalsIgnoreCase(defaultName))
				return biturl.mUrl;
		}

		return null;
	}

	private View.OnClickListener onRewind = new View.OnClickListener() {
		public void onClick(View v) {
			int pos = mVideoView.getCurrentPosition();
			pos -= 5000; // milliseconds
			mVideoView.seekTo(pos);
			setProgress();

			show(sDefaultTimeout);
		}
	};

	private View.OnClickListener onForward = new View.OnClickListener() {
		public void onClick(View v) {
			int pos = mVideoView.getCurrentPosition();
			pos += 15000; // milliseconds
			mVideoView.seekTo(pos);
			setProgress();

			show(sDefaultTimeout);
		}
	};

	private View.OnClickListener onPausePlay = new View.OnClickListener() {
		public void onClick(View v) {
			doPauseResume();
			show(sDefaultTimeout);
		}
	};

	private View.OnClickListener onHQ = new View.OnClickListener() {
		public void onClick(View v) {
			OnChangeTrailerQuality();
		}
	};

	private SeekBar.OnSeekBarChangeListener onSeekBar = new SeekBar.OnSeekBarChangeListener() {
		long duration;

		public void onStartTrackingTouch(SeekBar bar) {
			show(3600000);
			duration = mVideoView.getDuration();
		}

		public void onProgressChanged(SeekBar bar, int progress,
				boolean fromtouch) {
			if (fromtouch) {
				mDragging = true;
				duration = mVideoView.getDuration();
				long newposition = (duration * progress) / 1000L;
				mVideoView.seekTo((int) newposition);
				if (mCurrentTime != null)
					mCurrentTime.setText(stringForTime((int) newposition));
			}
		}

		public void onStopTrackingTouch(SeekBar bar) {
			mDragging = false;
			setProgress();
			updatePausePlay();
			show(sDefaultTimeout);
		}
	};

	/**
	 * Show the controller on screen. It will go away automatically after 3
	 * seconds of inactivity.
	 */
	public void show() {
		show(sDefaultTimeout);
	}

	/**
	 * Show the media panel on screen. It will go away automatically after
	 * 'timeout' milliseconds of inactivity.
	 * 
	 * @param timeout
	 *            The timeout in milliseconds. Use 0 to show the controller
	 *            until hide() is called.
	 */
	public void show(int timeout) {

		if (!mShowing) {
			setProgress();

			mBottomPanel.setVisibility(View.VISIBLE);
			mShowing = true;
		}
		updatePausePlay();

		// cause the progress bar to be updated even if mShowing
		// was already true. This happens, for example, if we're
		// paused with the progress bar showing the user hits play.
		mHandler.sendEmptyMessage(SHOW_PROGRESS);

		Message msg = mHandler.obtainMessage(FADE_OUT);
		if (timeout != 0) {
			mHandler.removeMessages(FADE_OUT);
			mHandler.sendMessageDelayed(msg, timeout);
		}
	}

	public boolean isShowing() {
		return mShowing;
	}

	/**
	 * Remove the controller from the screen.
	 */
	public void hide() {
		if (mShowing) {
			mHandler.removeMessages(SHOW_PROGRESS);
			mHandler.removeMessages(FADE_OUT);
			mBottomPanel.setVisibility(View.GONE);
			mShowing = false;
		}
	}

	private void doPauseResume() {
		if (mVideoView.isPlaying()) {
			mVideoView.pause();
		} else {
			mVideoView.start();
		}
		updatePausePlay();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int pos;
			switch (msg.what) {
			case FADE_OUT:
				hide();
				break;
			case SHOW_PROGRESS:
				pos = setProgress();
				if (!mDragging && mShowing && mVideoView.isPlaying()) {
					msg = obtainMessage(SHOW_PROGRESS);
					sendMessageDelayed(msg, 1000 - (pos % 1000));
				}
				break;
			}
		}
	};

	private String stringForTime(int timeMs) {
		String text = "";
		int totalSeconds = timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		if (mFormatBuilder != null && mFormatter != null) {
			mFormatBuilder.setLength(0);
			if (hours > 0) {
				text = mFormatter.format("%d:%02d:%02d", hours, minutes,
						seconds).toString();
			} else {
				text = mFormatter.format("%02d:%02d", minutes, seconds)
						.toString();
			}
		}
		return text;
	}

	private int setProgress() {
		if (mVideoView == null || mDragging) {
			return 0;
		}
		int position = mVideoView.getCurrentPosition();
		int duration = mVideoView.getDuration();
		if (mProgress != null) {
			if (duration > 0) {
				// use long to avoid overflow
				long pos = 1000L * position / duration;
				mProgress.setProgress((int) pos);
			}
			int percent = mVideoView.getBufferPercentage();
			mProgress.setSecondaryProgress(percent * 10);
		}

		if (mEndTime != null)
			mEndTime.setText(stringForTime(duration));
		if (mCurrentTime != null)
			mCurrentTime.setText(stringForTime(position));

		return position;
	}

	private void updatePausePlay() {
		if (mVideoView.isPlaying()) {
			mPausePlayButton.setImageResource(R.drawable.ic_media_pause);
		} else {
			mPausePlayButton.setImageResource(R.drawable.ic_media_play);
		}
	}

	public void OnChangeTrailerQuality() {
		final String[] trailerQualities = getAvailableTrailerQualities();
		final int currentTrailerIndex = getDefaultTrailerIndex();

		//		final AlertDialog ad = new AlertDialog.Builder(this)
		//				.setCancelable(true)
		//				.setTitle(R.string.select_trailer_quality)
		//				.setSingleChoiceItems(trailerQualities, currentTrailerIndex,
		//						new DialogInterface.OnClickListener() {
		//							public void onClick(DialogInterface dialog,
		//									int position) {
		//								dialog.dismiss();
		//								if (currentTrailerIndex != position) {
		//									mHqButton
		//											.setText(trailerQualities[position]);
		//									Globals.setDefaultTrailerQuality(trailerQualities[position]);
		//									switchVideoMode();
		//								}
		//							}
		//						}).create();
		//		ad.show();
	}

	private String[] getAvailableTrailerQualities() {
		if (mTrailerQualitySelectionList == null
				|| mTrailerQualitySelectionList.size() == 0)
			return null;

		String[] trailerQualities = new String[mTrailerQualitySelectionList
		                                       .size()];
		for (int i = 0; i < mTrailerQualitySelectionList.size(); i++) {
			trailerQualities[i] = mTrailerQualitySelectionList.get(i).mName;
		}
		return trailerQualities;
	}

	// gets the index of default bitrate name from
	// 'mTrailerQualitySelectionList'
	private int getDefaultTrailerIndex() {
		int index = 1; // corresponds to 'medium' trailer quality
		String defaultQualityName = Globals.getDefaultTrailerQuality();
		for (int i = 0; i < mTrailerQualitySelectionList.size(); i++) {
			if (defaultQualityName
					.equals(mTrailerQualitySelectionList.get(i).mName)) {
				index = i;
				break;
			}
		}
		return index;
	}

	private void updateSelectionList() {
		if (mBitrateUrlList == null || mBitrateUrlList.size() == 0)
			return;
		int i = 0;
		for (BitrateUrl bitUrl: mBitrateUrlList) {
			TrailerSelectionList item = new TrailerSelectionList();
			item.mName = bitUrl.mType.toString();
			item.mBitrateUrlListIndex = i++;
			mTrailerQualitySelectionList.add(item);
		}

		//		for (int i = 0; i < mBitrateUrlList.size(); i++) {
		//			TrailerSelectionList item = new TrailerSelectionList();
		//			if (mBitrateUrlList.get(i).mBitrate != -1) {
		//				// non-HD bitrates
		//				if (mBitrateUrlList.get(i).mBitrate <= Constants.LOW_QUALITY_BITRATE) {
		//					item.mName = Globals.getResourceString(R.string.low);
		//				} else if (mBitrateUrlList.get(i).mBitrate <= Constants.MEDIUM_QUALITY_BITRATE) {
		//					item.mName = Globals.getResourceString(R.string.medium);
		//				} else if (mBitrateUrlList.get(i).mBitrate <= Constants.HIGH_QUALITY_BITRATE) {
		//					item.mName = Globals.getResourceString(R.string.high);
		//				} else {
		//					// any trailer bitrate > Constants.HIGH_QUALITY_BITRATE in
		//					// non-HD, we ignore them for them
		//					item.mName = null;
		//				}
		//			} else {
		//				// HD bitrates
		//				item.mName = Globals.getResourceString(R.string.hd_prefix)
		//						+ mBitrateUrlList.get(i).mType + "p";
		//			}
		//			item.mBitrateUrlListIndex = i;
		//			if (!StringUtil.isNullOrEmpty(item.mName)) {
		//				int index = findQualityPosition(item.mName);
		//				if (index < 0) {
		//					// item not already in the list, so add it
		//					mTrailerQualitySelectionList.add(item);
		//				} else {
		//					// item already present, so update it
		//					TrailerSelectionList existingItem = mTrailerQualitySelectionList
		//							.get(index);
		//					existingItem.mBitrateUrlListIndex = item.mBitrateUrlListIndex;
		//					mTrailerQualitySelectionList.set(index, existingItem);
		//				}
		//			}
		//		}
	}

	private int findQualityPosition(String qualityName) {
		int index = -1;
		for (int i = 0; i < mTrailerQualitySelectionList.size(); i++) {
			if (qualityName.equals(mTrailerQualitySelectionList.get(i).mName)) {
				index = i;
				break;
			}
		}
		return index;
	}
}
