package com.rootdevs.storyapp.Activities;

import android.os.Bundle;

import com.rootdevs.storyapp.Fragments.CategoriesFragment;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentAct(new CategoriesFragment(), "Categories");
    }
}