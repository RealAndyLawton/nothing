package com.aol.mobile.moviefoneouya.api;

import android.content.Context;

import com.aol.mobile.moviefoneouya.api.transactions.GetVideosTransaction;

public class MoviefoneApi {
	
	public static final String MOVIEFONE_URL_BASE = "http://www.moviefone.com/api";
	
	private static final String TAG = MoviefoneApi.class.getSimpleName();

	public static void requestVideos(Context context, String type, int page) {
		
		GetVideosTransaction transaction = new GetVideosTransaction();
		transaction.mContext = context;
		transaction.execute();
		
	}
	
}
