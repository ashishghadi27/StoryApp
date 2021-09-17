package com.rootdevs.storyapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Interfaces.AuthView;
import com.rootdevs.storyapp.Presenters.AuthPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;
import com.rootdevs.storyapp.Utils.EmailValidation;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUp extends BaseFragment implements AuthView {

    private EditText name, email, password, confirmPass;
    private RelativeLayout signUp;
    private TextView signIn;
    private AuthPresenter presenter;
    private ProgressDialog dialog;

    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPass = view.findViewById(R.id.confirmPass);
        signUp = view.findViewById(R.id.signUp);
        signIn = view.findViewById(R.id.signIn);
        dialog = getProgressDialog("Sign Up", "Registering", false, getContext());

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignIn(), "Signin");
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp(){
        String nameStr, emailStr, passwordStr, confirmPassStr;
        nameStr = name.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        confirmPassStr = confirmPass.getText().toString().trim();
        boolean isEmpty = TextUtils.isEmpty(nameStr) ||
                TextUtils.isEmpty(emailStr) ||
                TextUtils.isEmpty(passwordStr) ||
                TextUtils.isEmpty(confirmPassStr);
        if(!isEmpty){
            if(EmailValidation.validateEmail(emailStr)){
                if(passwordStr.equals(confirmPassStr)){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", nameStr);
                        jsonObject.put("email", emailStr);
                        jsonObject.put("password", passwordStr);
                        jsonObject.put("isAdmin", "0");
                        presenter.register(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else confirmPass.setError("Password Mismatch");
            }
            else email.setError("Invalid Email Address");
        }
        else getAlertDialog("Invalid Input", "Fields Cannot be empty", getContext()).show();
    }


    @Override
    public void signInSuccess(JSONObject jsonObject) {

    }

    @Override
    public void signInFailure(VolleyError e) {

    }

    @Override
    public void signUpSuccess(JSONObject jsonObject) {
        Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
        replaceFragment(new SignIn(), "Signin");
    }

    @Override
    public void signUpFailure(VolleyError e) {
        getAlertDialog("Sign Up Error", "Something Went wrong.\nIf you already have an account, please Sign In.", getContext()).show();
    }

    @Override
    public void otpReceived(JSONObject object) {

    }

    @Override
    public void otpError(VolleyError e) {

    }

    @Override
    public void passUpdated(JSONObject object) {

    }

    @Override
    public void passUpdateFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }
}