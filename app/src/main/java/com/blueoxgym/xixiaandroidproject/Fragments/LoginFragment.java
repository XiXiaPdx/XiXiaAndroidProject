package com.blueoxgym.xixiaandroidproject.Fragments;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blueoxgym.xixiaandroidproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends DialogFragment implements View.OnClickListener {
    public Button mCreateButton;
    public Button mLoginButton;
    public FirebaseAuth mAuth;
    public EditText mEmail;
    public EditText mPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_login, container, false);
         mCreateButton = (Button) rootView.findViewById(R.id.createButton);
        mLoginButton = (Button) rootView.findViewById(R.id.logInButton);
        mEmail = (EditText) rootView.findViewById(R.id.emailEditText);
        mPassword = (EditText) rootView.findViewById(R.id.passwordEditText);
        mAuth = FirebaseAuth.getInstance();
        mLoginButton.setOnClickListener(this);
        mCreateButton.setOnClickListener(this);

        return rootView;
    }

    public void openCreateAccountFragment(){
        FragmentManager fm = getFragmentManager();
        CreateAccountFragment createAccountFragment = new CreateAccountFragment();
        createAccountFragment.show(fm, "create account");

    }

    public void loginWithPassword(){
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Successful!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v == mCreateButton){
            dismiss();
            openCreateAccountFragment();
        }
        if (v == mLoginButton){
            loginWithPassword();

        }

    }
}
