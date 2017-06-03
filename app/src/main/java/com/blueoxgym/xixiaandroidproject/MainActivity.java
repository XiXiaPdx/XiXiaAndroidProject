package com.blueoxgym.xixiaandroidproject;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.blueoxgym.xixiaandroidproject.Adapters.PictureListAdapter;
import com.blueoxgym.xixiaandroidproject.Fragments.DescribeFoodFragment;
import com.blueoxgym.xixiaandroidproject.Interfaces.OpenDescribeFragment;
import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.Services.UnSplashService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OpenDescribeFragment{
    public ArrayList<Picture> mPictures = new ArrayList<>();
    @Bind(R.id.pictureRecycleView)
    RecyclerView mPictureRecycleView;
    private PictureListAdapter mAdapter;
    private StaggeredGridLayoutManager picGridLayOut;
    private OpenDescribeFragment mOpenDescribe;

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
        mOpenDescribe = this;
        final UnSplashService unSplashService = new UnSplashService();
        unSplashService.getPictures(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {

                     mPictures = unSplashService.processResults(response);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                          mAdapter = new PictureListAdapter(mOpenDescribe, getApplicationContext(), mPictures
                                  );
                            AlphaInAnimationAdapter animateAdapter = new AlphaInAnimationAdapter(mAdapter);
                            animateAdapter.setDuration(1500);
                            mPictureRecycleView.setAdapter(new AlphaInAnimationAdapter(animateAdapter));
                            mPictureRecycleView.setHasFixedSize(true);
                        }
                    });
                }
            });
    }


    @Override
    public void openDescribeFragment(View v) {
        FragmentManager fm = getFragmentManager();
        DescribeFoodFragment describeFoodFragment= new DescribeFoodFragment();
        describeFoodFragment.show(fm, "Describe Food Fragment");
    }
}
