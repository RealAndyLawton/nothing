package com.aol.mobile.moviefoneouya;

import android.content.Context;

import com.aol.mobile.moviefoneouya.pojo.Video;

public class Globals {

	
	
	private static String				sDefaultTrailerQuality;
	private static Context sContext;
	
	public static Video currentVideo;
	
	
	public static void init(Context ctx) {
		
		sContext = ctx;
		sDefaultTrailerQuality = getResourceString(R.string.medium);
	}
	
	public static String getResourceString(int id) {
		return sContext.getResources().getString(id);
	}
	
	public static String getDefaultTrailerQuality() {
		return sDefaultTrailerQuality;
	}

	public static void setDefaultTrailerQuality(String name) {
		sDefaultTrailerQuality = name;
		
	}
	
	public static Context getsContext() {
		return sContext;
	}

	public static void setsContext(Context sContext) {
		Globals.sContext = sContext;
	}

	
	
	
	
}
