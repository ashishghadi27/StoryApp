package com.rootdevs.storyapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;

public class FullStoryFragment extends BaseFragment {

    private TextView name, summary, content;
    private ImageView storyImage, mcqs, assign;
    private String storyName, storySummary, storyContent, featuredLink, storyId;

    public FullStoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        storyName = bundle.getString("storyName");
        storyId = bundle.getString("storyId");
        storySummary = bundle.getString("storySummary");
        storyContent = bundle.getString("content");
        featuredLink = bundle.getString("featuredLink");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.storyName);
        summary = view.findViewById(R.id.summary);
        content = view.findViewById(R.id.content);
        storyImage = view.findViewById(R.id.storyImage);
        mcqs = view.findViewById(R.id.mcqs);
        assign = view.findViewById(R.id.assign);

        name.setText(storyName);
        summary.setText(storySummary);
        content.setText(storyContent);

        Glide.with(view).load(featuredLink).error(R.drawable.user).into(storyImage);

        mcqs.setOnClickListener(view1 -> {
            MCQSetFragment fragment = new MCQSetFragment();
            Bundle bundle = new Bundle();
            bundle.putString("storyId", storyId);
            bundle.putString("storyName",storyName);
            fragment.setArguments(bundle);
            addFragment(fragment, "MCQSet");
        });

        assign.setOnClickListener(view1 -> {
            AssignSetFragment fragment = new AssignSetFragment();
            Bundle bundle = new Bundle();
            bundle.putString("storyId", storyId);
            bundle.putString("storyName",storyName);
            fragment.setArguments(bundle);
            addFragment(fragment, "MCQSet");
        });
    }
}