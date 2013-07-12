package com.aol.mobile.moviefoneouya.pojo;

import android.os.Parcel;
import android.os.Parcelable;


// BitrateUrl class
// Contains data for Trailer Url and it's corresponding bitrate. No setters/getters
public class BitrateUrl implements Parcelable {
    public String mUrl;
    public int mBitrate;
    public Quality mType;
    
    public enum Quality {
    	LD(600), SD(800), HD(1500), FHD(2000), LDBASELINE(600);
    	public final int width;
    	Quality(int width) {this.width = width;}
    	
    }
    
    public BitrateUrl() {
    }
    
    public BitrateUrl (Parcel in) {
        mUrl = in.readString();
        mBitrate = in.readInt();
        mType = Quality.valueOf(in.readString());
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeInt(mBitrate);
        dest.writeString(mType.toString());
    }
    
    public static final Parcelable.Creator<BitrateUrl> CREATOR 
    = new Parcelable.Creator<BitrateUrl>() 
    {
        public BitrateUrl createFromParcel(Parcel in) {
            return new BitrateUrl(in); 
        } 
        
        public BitrateUrl[] newArray (int size)
        {
            return new BitrateUrl[size];
        }
    };
    
}
