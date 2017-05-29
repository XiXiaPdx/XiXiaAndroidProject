package com.blueoxgym.xixiaandroidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.Services.UnSplashService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Picture> mPictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFoodPictures();
    }
    public void getFoodPictures(){
        final UnSplashService unSplashService = new UnSplashService();
        unSplashService.getPictures(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    JSONArray picJSONArray = new JSONArray(jsonData);
                     mPictures = unSplashService.processResults(picJSONArray);
                    for(Picture picture: mPictures){
                        Log.d("Pic URL ", picture.getImageUrl());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
