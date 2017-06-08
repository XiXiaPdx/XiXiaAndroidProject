package com.blueoxgym.xixiaandroidproject.Models;

import org.parceler.Parcel;

/**
 * Created by macbook on 5/28/17.
 */
@Parcel
public class Picture {
    public String id;
    public String imageUrl;
    public String description;
    public String pushId;

    public String getID() {
        return id;
    }
    public String getDescription(){
        return description;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setId (String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public Picture () {}

    public Picture (String ID, String imageURL){
        this.id = ID;
        this.imageUrl = imageURL;
        this.description = "";
    }
}
