package com.blueoxgym.xixiaandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantsActivity extends AppCompatActivity {
@Bind(R.id.textView3)
TextView mDisplayPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int positionValue = intent.getIntExtra("position",0);
        mDisplayPosition.setText("You clicked on picture at position "+positionValue);
    }
}
