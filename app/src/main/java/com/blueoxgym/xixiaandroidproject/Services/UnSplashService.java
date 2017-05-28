package com.blueoxgym.xixiaandroidproject.Services;

import com.blueoxgym.xixiaandroidproject.Constants;

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
}
