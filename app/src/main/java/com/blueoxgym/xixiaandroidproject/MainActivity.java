package com.blueoxgym.xixiaandroidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.blueoxgym.xixiaandroidproject.Adapters.PictureListAdapter;
import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.Services.UnSplashService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Picture> mPictures = new ArrayList<>();
    @Bind(R.id.pictureRecycleView)
    RecyclerView mPictureRecycleView;
    private PictureListAdapter mAdapter;
    private StaggeredGridLayoutManager picGridLayOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getFoodPictures();
        picGridLayOut = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mPictureRecycleView.setLayoutManager(picGridLayOut);
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
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          mAdapter = new PictureListAdapter(getApplicationContext(), mPictures);
                            mPictureRecycleView.setAdapter(mAdapter);
                            mPictureRecycleView.setHasFixedSize(true);


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
