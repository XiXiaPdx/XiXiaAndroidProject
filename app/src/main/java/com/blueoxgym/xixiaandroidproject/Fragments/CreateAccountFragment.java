package com.blueoxgym.xixiaandroidproject.Fragments;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.blueoxgym.xixiaandroidproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends DialogFragment implements View.OnClickListener {
    public Button mLoginButton;
    public Button mSubmitButton;
    public EditText mPassword;
    public EditText mEmail;
    public EditText mName;
    public EditText mConfirmPassword;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_create_account, container, false);
        mLoginButton = (Button) rootView.findViewById(R.id.logInButton);
        mSubmitButton = (Button) rootView.findViewById(R.id.submitButton);
        mPassword = (EditText) rootView.findViewById(R.id.passwordEditText);
        mConfirmPassword = (EditText) rootView.findViewById(R.id.confirmEditText);
        mName = (EditText) rootView.findViewById(R.id.nameEditText);
        mEmail =(EditText) rootView.findViewById(R.id.emailEditText);
        mLoginButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);

        return rootView;
    }

    public void openLoginFragment (){
        FragmentManager fm = getFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.show(fm, "open login fragment");
    }

    public void createNewUser(){
        String password = mPassword.getText().toString().trim();
        String confirmPassword= mConfirmPassword.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String name = mName.getText().toString().trim();
        Log.d("user info", "I got your inputs" + email);
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginButton){
            dismiss();
            openLoginFragment();
        }
        if (v == mSubmitButton){
            createNewUser();
            dismiss();
        }
    }
}
