package com.aol.mobile.moviefoneouya.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aol.mobile.moviefoneouya.R;
import com.aol.mobile.moviefoneouya.pojo.Video;


public class VideoAdapter extends ArrayAdapter<Video> {

	
	private final String TAG 	= "VideoAdapter";
	private Context context;
	List<Video> mVideos;
	private LayoutInflater mInflater;
	
	public VideoAdapter(Context context, int textViewResourceId, List<Video> videos) {
		super(context, textViewResourceId, videos);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mVideos = videos;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	private static class VideoViewHolder {

		private TextView title;
		
	}
	
	@Override
	public int getCount() {
		return (mVideos.size() > 0) ? mVideos.size() : 0;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		Log.d(TAG, "Getting View");
		VideoViewHolder holder = null;

		// If convertView is empty, we need to inflate a layout
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.video_list_item, null);

			// Find the child views of the inflated layout and store them in the ViewHolder
			holder = new VideoViewHolder();
			Log.d(TAG, "Getting Title View");
			holder.title = (TextView) convertView.findViewById(R.id.video_list_item_title);
			// Tag the view with the ViewHolder
			convertView.setTag(holder);
		}
		else {
			holder = (VideoViewHolder) convertView.getTag();
		}

		Video video = mVideos.get(position);
		Log.d(TAG, "about to set title");
		holder.title.setText(video.videoName);


		return convertView;

	}


}
