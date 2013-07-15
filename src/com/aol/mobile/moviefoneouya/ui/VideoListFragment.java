package com.aol.mobile.moviefoneouya.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.aol.mobile.moviefoneouya.Globals;
import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.adapters.VideoAdapter;
import com.aol.mobile.moviefoneouya.pojo.Video;

public class VideoListFragment extends ListFragment implements OnScrollListener {

	private final String TAG = "VideoListFragment";

	private ListView mList;
	private int mVideoSelectedIndex;
	VideoAdapter mAdapter;
	Activity currentActivity;
	VideosListener mVideosListener;
	View view;
	ArrayList<Video> mVideosList;

	public VideoListFragment() {
		//Stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null ) {
			// Initialize the adapters
			Log.d(TAG, "starting on Create.");
			

		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new VideoAdapter(getActivity(), 0 ,new ArrayList<Video>());
		currentActivity = getActivity();
		
		mList = getListView();

		mList.setAdapter(mAdapter);
		mList.setOnScrollListener(this);

		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
				mVideoSelectedIndex = position;
				mVideosListener.onVideoItemClicked((Video) adapter.getItemAtPosition(position));
			}

		});
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		// Make sure any Activity hosting this fragment is implementing the VideosListener interface
		try {
			mVideosListener = (VideosListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Parent activity needs to implement OnMovieItemClicked");
		}

	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "Starting onCreateView Again");
		view = inflater.inflate(R.layout.video_list_view, container, false);
		
		


		return view;

	}

	public void addVideos(List<Video> videos) {
		mAdapter.clear();
		mAdapter.addAll(videos);
		mAdapter.notifyDataSetChanged();
	}

	public static interface VideosListener {

		public void onFirstVideoPageLoaded(List<Video> videos);
		public void onVideoItemClicked(Video video);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

}
