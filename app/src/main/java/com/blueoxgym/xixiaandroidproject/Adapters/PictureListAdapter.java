package com.blueoxgym.xixiaandroidproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueoxgym.xixiaandroidproject.Constants;
import com.blueoxgym.xixiaandroidproject.Interfaces.OpenDescribeFragment;
import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.R;
import com.blueoxgym.xixiaandroidproject.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by macbook on 5/29/17.
 */

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.PictureViewHolder> {
    public ArrayList<Picture> mPictures = new ArrayList<>();
    private Context mContext;
    private OpenDescribeFragment mOpenDescribe;
    public FirebaseAuth mAuth;
    public ArrayList<Picture> mUserFoods = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public PictureListAdapter() { }

    public PictureListAdapter (OpenDescribeFragment listener, Context context, ArrayList<Picture>
            pictures){
        super ();
        mOpenDescribe = listener;
        mContext = context;
        mPictures = pictures;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSharedPreferences.edit();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.pictureItemView)
        ImageView mPictureView;
        @Bind(R.id.findFoodButton)
        ImageButton mFindFoodButton;
        @Bind(R.id.descriptionTextView)
        TextView descriptionTextView;
        private Context context;
        @Bind(R.id.quickSearch)
        ImageButton mQuickSearch;

        public PictureViewHolder(View itemView)   {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mFindFoodButton.setOnClickListener(this);
            mQuickSearch.setOnClickListener(this);
            mAuth = FirebaseAuth.getInstance();
        }

        public void bindPicture(Picture picture) {
            Picasso.with(mContext).load(picture.getImageUrl()).into(mPictureView);
            mQuickSearch.setVisibility(View.INVISIBLE);
            if (mAuth.getCurrentUser() == null ){
                mFindFoodButton.setVisibility(View.INVISIBLE);
                descriptionTextView.setText("");
            } else {
                mFindFoodButton.setVisibility(View.VISIBLE);
                if (mUserFoods.size() > 0) {
                    String searchID = picture.getID();
                    descriptionTextView.setText(matchCheck(searchID));
               } else {
                    descriptionTextView.setText("");

                }
            }
        }
        public String matchCheck (String searchID){
            String resultDescription="";
            for(Picture userFood: mUserFoods){
                if( userFood.getID().equals(searchID)){
                    resultDescription = userFood.getDescription();
                    mQuickSearch.setVisibility(View.VISIBLE);
                    break;
                }
            }
            return resultDescription;
        }
        @Override
        public void onClick(View v) {
            if(v == mFindFoodButton) {
                mOpenDescribe.openDescribeFragment(v, mPictures.get(getAdapterPosition()));
            }
            if (v == mQuickSearch){
                String searchFood = descriptionTextView.getText().toString();
                mEditor.putString(Constants.LAST_FOOD_SEARCH, searchFood).apply();
                Intent intent = new Intent (mContext, SearchActivity.class);
                mContext.startActivity(intent);

            }

        }
    }
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
        PictureViewHolder viewHolder = new PictureViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder,  int position) {
        holder.bindPicture(mPictures.get(position));
    }

    public void showHideFoodListener(ArrayList<Picture> userFoods) {
        mUserFoods = userFoods;
        Log.d("Reseting REcycler View", "NOW NOW" + mUserFoods.toString());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPictures.size();
    }
}
