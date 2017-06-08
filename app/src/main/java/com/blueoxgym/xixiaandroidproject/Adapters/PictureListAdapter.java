package com.blueoxgym.xixiaandroidproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueoxgym.xixiaandroidproject.Interfaces.OpenDescribeFragment;
import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by macbook on 5/29/17.
 */

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.PictureViewHolder> {
    private ArrayList<Picture> mPictures = new ArrayList<>();
    private Context mContext;
    private OpenDescribeFragment mOpenDescribe;
    public Boolean mShowFindFood;
    public FirebaseAuth mAuth;


    public PictureListAdapter() { }

    public PictureListAdapter (OpenDescribeFragment listener, Context context, ArrayList<Picture>
            pictures){
        super ();
        mOpenDescribe = listener;
        mContext = context;
        mPictures = pictures;

    }

    public class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @Bind(R.id.pictureItemView)
        ImageView mPictureView;
        @Bind(R.id.findFoodButton)
        ImageButton mFindFoodButton;
        @Bind(R.id.descriptionTextView)
        TextView descriptionTextView;
        private Context context;



        public PictureViewHolder(View itemView)   {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mFindFoodButton.setOnClickListener(this);
            mAuth = FirebaseAuth.getInstance();
        }


        public void bindPicture(Picture picture) {
            Picasso.with(mContext).load(picture.getImageUrl()).into(mPictureView);
            descriptionTextView.setText(picture.getID());

        }


        @Override
        public void onClick(View v) {
            if(v == mFindFoodButton) {
                mOpenDescribe.openDescribeFragment(v, mPictures.get(getAdapterPosition()));
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

        if (mAuth.getCurrentUser() == null){
        holder.mFindFoodButton.setVisibility(View.INVISIBLE);
        } else {
            holder.mFindFoodButton.setVisibility(View.VISIBLE);
        }
    }

    public void showHideFoodListener() {
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return mPictures.size();
    }


}
