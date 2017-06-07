package com.blueoxgym.xixiaandroidproject.Fragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blueoxgym.xixiaandroidproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends DialogFragment implements View.OnClickListener {
    public Button mCreateButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_login, container, false);
         mCreateButton = (Button) rootView.findViewById(R.id.createButton);
        mCreateButton.setOnClickListener(this);


        return rootView;
    }

    public void openCreateAccountFragment(){
        FragmentManager fm = getFragmentManager();
        CreateAccountFragment createAccountFragment = new CreateAccountFragment();
        createAccountFragment.show(fm, "create account");

    }


    @Override
    public void onClick(View v) {
        if (v == mCreateButton){
            dismiss();
            openCreateAccountFragment();
        }

    }
}
