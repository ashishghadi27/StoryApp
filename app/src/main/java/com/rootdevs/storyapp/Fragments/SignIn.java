package com.rootdevs.storyapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Activities.MainActivity;
import com.rootdevs.storyapp.Interfaces.AuthView;
import com.rootdevs.storyapp.Presenters.AuthPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;
import com.rootdevs.storyapp.Utils.EmailValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SignIn extends BaseFragment implements AuthView {

    private EditText email, pass;
    private RelativeLayout login;
    private TextView signUp, forgotPass;
    private AuthPresenter presenter;
    private ProgressDialog dialog;

    public SignIn() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = getProgressDialog("Please wait", "Api Call in progress", false, getContext());
        presenter = new AuthPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        signUp = view.findViewById(R.id.signUp);
        forgotPass = view.findViewById(R.id.forgotPass);
        dialog = getProgressDialog("Authenticating", "Verifying Credentials", false, getContext());
        signUp.setOnClickListener(view1 ->{
            addFragment(new SignUp(), "Signup");
        });

        login.setOnClickListener(view1 -> {
            loginIn(email.getText().toString().trim(), pass.getText().toString().trim());
        });

        forgotPass.setOnClickListener(view1 -> {
            replaceFragment(new OtpVerification(), "OtpVerification");
        });
    }

    private void loginIn(String emailId, String password){
        if(!TextUtils.isEmpty(emailId) && !TextUtils.isEmpty(password)) {
            try {
                if(EmailValidation.validateEmail(emailId))
                    presenter.login(emailId, password);
                else email.setError("Invalid Email Address");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else getAlertDialog("Invalid Input", "Fields Cannot be empty", getContext()).show();
    }

    @Override
    public void signInSuccess(JSONObject jsonObject) {
        Log.v("SUCCESS RESP", jsonObject.toString());
        try {
            if(jsonObject.getString("message").equals("Success")){
                JSONObject object = jsonObject.getJSONObject("response");
                saveUserDetails(object.getString("userId"),
                        object.getString("name"),
                        object.getString("email"),
                        object.getString("isAdmin"));
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
            else getAlertDialog("Sign In Failed", "Invalid Email or Password", getContext()).show();
        } catch (JSONException e) {
            getAlertDialog("Sign In Failed", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }


    @Override
    public void signInFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void signUpSuccess(JSONObject jsonObject) {

    }

    @Override
    public void signUpFailure(VolleyError e) {

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