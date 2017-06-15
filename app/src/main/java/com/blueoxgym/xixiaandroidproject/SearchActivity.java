package com.blueoxgym.xixiaandroidproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.blueoxgym.xixiaandroidproject.Fragments.RestaurantListFragment;
import com.blueoxgym.xixiaandroidproject.Models.Restaurant;
import com.blueoxgym.xixiaandroidproject.Services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    public ArrayList<Restaurant> mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getRestaurants(mSharedPreferences.getString(Constants.LAST_FOOD_SEARCH, null));
    }

    public void loadFragment(){
        FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
        fragmentTrans.replace(R.id.fragmentHolder, RestaurantListFragment.newInstance(mRestaurants));
        fragmentTrans.commit();
    }


    public void getRestaurants(String food) {
        final YelpService yelpService = new YelpService();

        yelpService.findRestaurants(food, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mRestaurants = yelpService.processResults(response);
                loadFragment();

            }
        });
    }
}