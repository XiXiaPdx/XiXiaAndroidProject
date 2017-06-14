package com.blueoxgym.xixiaandroidproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blueoxgym.xixiaandroidproject.Fragments.RestaurantListFragment;
import com.blueoxgym.xixiaandroidproject.Models.Restaurant;
import com.blueoxgym.xixiaandroidproject.Services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.textView3)
    TextView mDisplayPosition;
    public String location = "97232";
    private SharedPreferences mSharedPreferences;
    public ArrayList<Restaurant> mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDisplayPosition.setText("You want to search for " + mSharedPreferences.getString(Constants.LAST_FOOD_SEARCH, null));
        getRestaurants(location);
        loadFragment();
    }

    public void loadFragment(){
        FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
        fragmentTrans.add(R.id.fragmentHolder, new RestaurantListFragment());
        fragmentTrans.commit();
    }


    public void getRestaurants(String location) {
        final YelpService yelpService = new YelpService();

        yelpService.findRestaurants(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mRestaurants = yelpService.processResults(response);



//                this.runOnUiThread(new Runnable() {
//                    // Line above states 'getActivity()' instead of previous 'RestaurantListActivity.this'
//                    // because fragments do not have own context, and must inherit from corresponding activity.
//
//
//                    @Override
//                    public void run() {
//                        mAdapter = new RestaurantListAdapter(getActivity(), mRestaurants);
//                        // Line above states `getActivity()` instead of previous
//                        // 'getApplicationContext()' because fragments do not have own context,
//                        // must instead inherit it from corresponding activity.
//
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                        // Line above states 'new LinearLayoutManager(getActivity());' instead of previous
//                        // 'new LinearLayoutManager(RestaurantListActivity.this);' when method resided
//                        // in RestaurantListActivity because Fragments do not have context
//                        // and must instead inherit from corresponding activity.
//
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                    }
//                });
            }
        });
    }
}