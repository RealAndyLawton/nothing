package com.aol.mobile.moviefoneouya.ui;

import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.pojo.Video;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TestFragment extends Fragment {
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.video_list_view, container, false);
			
		// Set up the ListView
		return v;

	}
	
}
