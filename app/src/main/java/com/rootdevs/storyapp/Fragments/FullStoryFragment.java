package com.rootdevs.storyapp.Fragments;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;
import com.rootdevs.storyapp.Utils.Constants;

public class FullStoryFragment extends BaseFragment {

    private TextView name, summary, content, summLabel, storyLabel;
    private ImageView mcqs, assign;
    private VideoView storyImage;
    private Button mcqSec, assignSec;
    private ProgressDialog dialog;
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
        dialog = getProgressDialog("Please wait", "Loading Video", false, getContext());
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
        summLabel = view.findViewById(R.id.summLabel);
        storyLabel= view.findViewById(R.id.storyLabel);
        storyImage = view.findViewById(R.id.storyImage);
        mcqSec = view.findViewById(R.id.mcqSec);
        assignSec = view.findViewById(R.id.assignSec);

        dialog.show();

        if(featuredLink.contains("http://localhost:"))
            featuredLink = featuredLink.replace("http://localhost:", Constants.domain);

        storyImage.setVideoPath(featuredLink);
        storyImage.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                dialog.dismiss();
            }
        });
        storyImage.setMediaController(new MediaController(requireContext()));
        storyImage.start();

        mcqs = view.findViewById(R.id.mcqs);
        assign = view.findViewById(R.id.assign);

        name.setText(storyName);
        if(!TextUtils.isEmpty(storySummary)){
            summary.setText(storySummary);
        }
        else {
            summLabel.setVisibility(View.GONE);
            summary.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(storyContent)){
            content.setText(storyContent);
        }
        else{
            storyLabel.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
        }

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

        mcqSec.setOnClickListener(view1 -> {
            mcqs.performClick();
        });

        assignSec.setOnClickListener(view1 -> {
            assign.performClick();
        });


    }
}