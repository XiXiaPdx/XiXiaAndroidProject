package com.blueoxgym.xixiaandroidproject.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blueoxgym.xixiaandroidproject.Models.Picture;
import com.blueoxgym.xixiaandroidproject.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * Created by macbook on 6/2/17.
 */

public class DescribeFoodFragment extends DialogFragment implements View.OnClickListener{
    private Button closeButton;
    private EditText describeEditText;
    private Button openCamera;
    private Button searchButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_describefood, container,
                false);
        Context context = rootView.getContext();
        ImageView describePictureView = (ImageView) rootView.findViewById(R.id.describePictureView);
        describeEditText = (EditText) rootView.findViewById(R.id.describeEditText);
        closeButton = (Button) rootView.findViewById(R.id.closeButton);
        openCamera = (Button) rootView.findViewById(R.id.openCamera);
        searchButton = (Button) rootView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        openCamera.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        Bundle bundle = getArguments();
         Picture picture = Parcels.unwrap(bundle.getParcelable("picture"));
        Picasso.with(context).load(picture.getImageUrl()).into(describePictureView);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            if (v == closeButton) {
                dismiss();
            }
        }
        if (v == openCamera ){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) !=
                    null) {
                startActivityForResult(takePictureIntent, 1);
            }
        }
        if (v == searchButton ){
            Log.d("Search", "Button Clicked");
        }

    }

    public boolean validate() {
        boolean valid = false;

        String describeInput = describeEditText.getText().toString();

        if (describeInput.isEmpty() ) {
            describeEditText.setError("please describe your food");
        } else {
            describeEditText.setError(null);
            valid = true;
        }
        return valid;
    }
}
