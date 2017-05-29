package com.blueoxgym.xixiaandroidproject.Models;

/**
 * Created by macbook on 5/28/17.
 */

public class Picture {
    private String mID;
    private String mImageUrl;

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
    public Picture (String ID){
        this.mID = ID;

    }
//    public Picture (String ID, String imageURL){
//        this.mID = ID;
//        this.mImageUrl = imageURL;
//
//    }
}
