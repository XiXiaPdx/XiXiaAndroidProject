package com.blueoxgym.xixiaandroidproject;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.FragmentManager;
import android.app.ProgressDialog;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.blueoxgym.xixiaandroidproject.Adapters.PictureListAdapter;
import com.blueoxgym.xixiaandroidproject.Fragments.DescribeFoodFragment;
import com.blueoxgym.xixiaandroidproject.Fragments.LoginFragment;
import com.blueoxgym.xixiaandroidproject.Interfaces.OpenDescribeFragment;
import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.Services.EndlessRecyclerViewScrollListener;
import com.blueoxgym.xixiaandroidproject.Services.UnSplashService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity implements OpenDescribeFragment {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog mLoadingFoodsDialog;

    public ArrayList<Picture> mPictures = new ArrayList<>();
    @Bind(R.id.pictureRecycleView)
    RecyclerView mPictureRecycleView;
    @Bind(R.id.appNameTextView)
    TextView mAppName;
    @Bind(R.id.byLineTextView) TextView mByLine;
    @Bind(R.id.textView) TextView mLoginInstruction;
    private PictureListAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private StaggeredGridLayoutManager picGridLayOut;
    private OpenDescribeFragment mOpenDescribe;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth;
    public ArrayList<Picture> userFoods;
    public UnSplashService unSplashService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadingFoodsProgressDialog();

        unSplashService = new UnSplashService();
        userFoods = new ArrayList<>();
        picGridLayOut = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mPictureRecycleView.setLayoutManager(picGridLayOut);
        Typeface righteous = Typeface.createFromAsset(getAssets(), "Fonts/Righteous-Regular.ttf");
        mAppName.setTypeface(righteous);
        mByLine.setTypeface(righteous);
        mAuth = FirebaseAuth.getInstance();
        mAdapter = new PictureListAdapter();
        createAuthStateListener();
        getFoodPictures();
        scrollListener = new EndlessRecyclerViewScrollListener(picGridLayOut) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                    loadNextDataFromApi(page);

            }
        };
        mPictureRecycleView.addOnScrollListener(scrollListener);
    }

    public void loadNextDataFromApi(int page) {
        Log.d("Loading More", "ENDLESS ENDLESS");
//        unSplashService.getPictures(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                mPictures.addAll(unSplashService.processResults(response));
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.showHideFoodListener(mPictures);
//                    }
//                });
//            }
//        });
    }

    public void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    getSupportActionBar().setTitle(user.getDisplayName()+", you hungry?");
                    getUserFoods();
                    mAppName.animate().translationY(-300).withLayer();
                    mByLine.animate().translationY(-400).withLayer();
                    mLoginInstruction.animate().translationY(-500).withLayer();
                    mPictureRecycleView.animate().translationY(-400).withLayer();

                } else {
                    Log.d("Not Logged In", "FIRING FIRING");
                    getSupportActionBar().setTitle("");

                    mAppName.animate().translationY(30).withLayer();
                    mByLine.animate().translationY(35).withLayer();

                    mLoginInstruction.animate().translationY(6).withLayer();
                    mPictureRecycleView.animate().translationY(0).withLayer();
                    mAdapter.showHideFoodListener(userFoods);
                }
            }
        };
    }

    private void loadingFoodsProgressDialog(){
        mLoadingFoodsDialog = new ProgressDialog(this);
        mLoadingFoodsDialog.setTitle("Creating your menu...");
        mLoadingFoodsDialog.setCancelable(false);
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
        mLoadingFoodsDialog.show();
        unSplashService.getPictures(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) {
                mPictures.addAll( unSplashService.processResults(response));
                MainActivity.this.runOnUiThread(new Runnable() {
                         @Override
                        public void run() {
                          mAdapter = new PictureListAdapter(mOpenDescribe, getApplicationContext
                                  (), mPictures);
                            AlphaInAnimationAdapter animateAdapter = new AlphaInAnimationAdapter(mAdapter);
                            animateAdapter.setDuration(1500);
                            mPictureRecycleView.setAdapter(new AlphaInAnimationAdapter(animateAdapter));
                            mPictureRecycleView.setHasFixedSize(true);
                             mLoadingFoodsDialog.dismiss();

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

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


    public void getUserFoods(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DESCRIBED_FOODS).child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Picture> tempUserFoods = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    tempUserFoods.add(snapshot.getValue(Picture.class));
                }
                mAdapter.showHideFoodListener(tempUserFoods);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
