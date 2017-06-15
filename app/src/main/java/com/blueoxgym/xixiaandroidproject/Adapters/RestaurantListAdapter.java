package com.blueoxgym.xixiaandroidproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueoxgym.xixiaandroidproject.Interfaces.adapterToFragmentListener;
import com.blueoxgym.xixiaandroidproject.Models.Restaurant;
import com.blueoxgym.xixiaandroidproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 6/14/17.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private Context mContext;
    private static final int MAX_WIDTH = 500;
    private static final int MAX_HEIGHT = 500;
    public adapterToFragmentListener testListener;



    public RestaurantListAdapter(Context context, ArrayList<Restaurant> restaurants, adapterToFragmentListener testListener) {
        mContext = context;
        mRestaurants = restaurants;
        this.testListener = testListener;

    }

    public void setTestListener(adapterToFragmentListener testListener){
        this.testListener = testListener;
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context mContext;
        @Bind(R.id.restaurantImage)
        ImageView mImage;
        @Bind (R.id.restaurantName)
        TextView mName;
        @Bind (R.id.restaurantRating)
        TextView mRating;



        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mImage.setOnClickListener(this);

        }

        public void bindRestaurant(Restaurant restaurant) {
            mName.setText(restaurant.getName());
            Picasso.with(mContext).load(restaurant.getImageUrl()).resize(MAX_WIDTH, MAX_HEIGHT).centerCrop().into(mImage);
             mRating.setText(restaurant.getRating() + "/5");
        }

        @Override
        public void onClick(View v) {
            if(v == mImage) {
                int itemPosition = getLayoutPosition();
                Log.d("Restaurant Clicked", Integer.toString(itemPosition));
                testListener.sendMessage("From Adapter, the view position "+ Integer.toString(itemPosition));
                Intent googleMap = new Intent (Intent.ACTION_VIEW,
                        Uri.parse("geo:" + mRestaurants.get(itemPosition).getLatitude()
                                +","+ mRestaurants.get(itemPosition).getLongitude()
                                + "?q=(" + mRestaurants.get(itemPosition).getName()+")"));
                mContext.startActivity(googleMap);
            }
        }
    }
    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }
}