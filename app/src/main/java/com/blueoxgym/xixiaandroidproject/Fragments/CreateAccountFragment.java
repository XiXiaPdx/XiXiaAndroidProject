package com.blueoxgym.xixiaandroidproject.Fragments;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blueoxgym.xixiaandroidproject.MainActivity;
import com.blueoxgym.xixiaandroidproject.MainActivity$$ViewBinder;
import com.blueoxgym.xixiaandroidproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = CreateAccountFragment.class.getSimpleName();

    public Button mLoginButton;
    public Button mSubmitButton;
    public EditText mPassword;
    public EditText mEmail;
    public EditText mName;
    public EditText mConfirmPassword;
    public FirebaseAuth mAuth;
    public Context context;
    public String name;


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
        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }

    public void openLoginFragment (){
        FragmentManager fm = getFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.show(fm, "open login fragment");
    }

    public void createNewUser(){
        final String password = mPassword.getText().toString().trim();
        String confirmPassword= mConfirmPassword.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
         name = mName.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Log.d(TAG, "Authentication successful");
                            createFirebaseUserProfile(task.getResult().getUser());

                        } else {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createFirebaseUserProfile (final FirebaseUser user){
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, user.getDisplayName());
                }
            }

        });

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
