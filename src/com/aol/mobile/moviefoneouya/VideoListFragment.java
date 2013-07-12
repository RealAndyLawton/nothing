package com.aol.mobile.moviefoneouya;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
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

import com.aol.mobile.moviefoneouya.adapters.VideoAdapter;
import com.aol.mobile.moviefoneouya.api.transactions.GetVideosTransaction.VideosResponseEvent;
import com.aol.mobile.moviefoneouya.pojo.Video;
import com.squareup.otto.Subscribe;

public class VideoListFragment extends Fragment implements OnScrollListener {
	
	private final String TAG = "VideoListFragment";
	
	private static ListView mList;
	private int mVideoSelectedIndex;
	VideoAdapter mAdapter;
	Activity currentActivity;
	VideosListener mVideosListener;

	public VideoListFragment() {
		//Stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null ) {
			currentActivity = getActivity();
			// Initialize the adapters
			mAdapter = new VideoAdapter(getActivity(), 0, new ArrayList<Video>());
			
			//Test Loop 
			for(int i = 0; i < 10; i++) {
				Log.d(TAG , "added Video " + Integer.toString(i));
				Video v = new Video();
				v.videoName = "Video " + Integer.toString(i); 
				mAdapter.add(v);
			}
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		currentActivity = getActivity();
	}
	
	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		// Make sure any Activity hosting this fragment is implementing the OnMovieItemClicked interface
		try {
			mVideosListener = (VideosListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Parent activity needs to implement OnMovieItemClicked");
		}

	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Log.d(TAG, "Starting onCreateView");
		View v = inflater.inflate(R.layout.video_list_view, container, false);
			
		// Set up the ListView
		mList = (ListView) v.findViewById(R.id.videos_list);
		
		mList.setAdapter(mAdapter);
		mList.setOnScrollListener(this);
		
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
				mVideoSelectedIndex = position;
				mVideosListener.onVideoItemClicked((Video) adapter.getItemAtPosition(position));
			}

		});

		return v;

	}
	
	public void addVideos(HashMap<String, ArrayList<Video>> hashMap) {
		Log.d(TAG, hashMap.toString());
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
