package com.blueoxgym.xixiaandroidproject;


import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blueoxgym.xixiaandroidproject.Adapters.PictureListAdapter;
import com.blueoxgym.xixiaandroidproject.Fragments.DescribeFoodFragment;
import com.blueoxgym.xixiaandroidproject.Fragments.LoginFragment;
import com.blueoxgym.xixiaandroidproject.Interfaces.OpenDescribeFragment;
import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.Services.UnSplashService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity implements OpenDescribeFragment{
    public static final String TAG = MainActivity.class.getSimpleName();

    public ArrayList<Picture> mPictures = new ArrayList<>();
    @Bind(R.id.pictureRecycleView)
    RecyclerView mPictureRecycleView;
    @Bind(R.id.appNameTextView)
    TextView mAppName;
    @Bind(R.id.byLineTextView) TextView mByLine;
    private PictureListAdapter mAdapter;
    private StaggeredGridLayoutManager picGridLayOut;
    private OpenDescribeFragment mOpenDescribe;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getFoodPictures();
        picGridLayOut = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mPictureRecycleView.setLayoutManager(picGridLayOut);
        Typeface righteous = Typeface.createFromAsset(getAssets(), "Fonts/Righteous-Regular.ttf");
        mAppName.setTypeface(righteous);
        mByLine.setTypeface(righteous);
        createAuthStateListener();
        mAuth = FirebaseAuth.getInstance();


    }

    public void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "The user is " + user.getEmail());
                } else {
                    Log.d(TAG, "The user logged out");

                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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


    public void openDescribeFragment(View v, Picture picture) {
        FragmentManager fm = getFragmentManager();
        DescribeFoodFragment describeFoodFragment= new DescribeFoodFragment();
        Bundle args = new Bundle();
        args.putParcelable("picture", Parcels.wrap(picture));
        describeFoodFragment.setArguments(args);
        describeFoodFragment.show(fm, "Describe Food Fragment");
    }

    public void openLoginFragment() {
        FragmentManager fm = getFragmentManager();
        LoginFragment loginFragment= new LoginFragment();
        loginFragment.show(fm, "something");
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_login) {
            openLoginFragment();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
