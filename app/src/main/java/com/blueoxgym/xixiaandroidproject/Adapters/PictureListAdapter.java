package com.blueoxgym.xixiaandroidproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by macbook on 5/29/17.
 */

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.PictureViewHolder> {
    private ArrayList<Picture> mPictures = new ArrayList<>();
    private Context mContext;

    public PictureListAdapter (Context context, ArrayList<Picture> pictures){
        mContext = context;
        mPictures = pictures;
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.pictureItemView)
        ImageView mPictureView;
        @Bind(R.id.findFoodButton)
        Button mFindFoodButton;
        private Context context;
        public PictureViewHolder (View itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mFindFoodButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("find button", Integer.toString(getAdapterPosition()));
                }
            });
        }

        public void bindPicture (Picture picture){
            Picasso.with(mContext).load(picture.getImageUrl()).into(mPictureView);

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

    @Override
    public int getItemCount() {
        return mPictures.size();
    }

}
