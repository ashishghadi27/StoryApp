package com.rootdevs.storyapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.rootdevs.storyapp.Fragments.SignIn;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseActivity;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        addFragmentAct(new SignIn(), "Signin");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if(getUserId() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        getBackDialog(this).show();
    }
}