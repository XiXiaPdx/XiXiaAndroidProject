package com.blueoxgym.xixiaandroidproject.Services;

import com.blueoxgym.xixiaandroidproject.Constants;
import com.blueoxgym.xixiaandroidproject.Models.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by macbook on 5/28/17.
 */

public class UnSplashService {
    public static void getPictures(Callback callback){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.UNSPLASH_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.UNSPLASH_CLIENT_ID, Constants.UNSPLASH_ID);
        String url = urlBuilder.build().toString();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request= new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Picture>processResults(JSONArray picsJSONArray){
        ArrayList<Picture> pictures = new ArrayList<>();
        try {
            for (int i = 0; i < picsJSONArray.length(); i++) {
                JSONObject pictureJSON = picsJSONArray.getJSONObject(i);
                String ID = pictureJSON.getString("id");
//                JSONObject pictureUrlsJSON = pictureJSON.getJSONObject();
//
//                String imageURL = pictureJSON.getString()
                Picture picture = new Picture (ID);
                pictures.add(picture);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pictures;
    }
}
