package com.rootdevs.storyapp.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.storyapp.Adapters.CategoriesAdapter;
import com.rootdevs.storyapp.Adapters.StoriesAdapter;
import com.rootdevs.storyapp.Interfaces.StoryClickListener;
import com.rootdevs.storyapp.Interfaces.StoryView;
import com.rootdevs.storyapp.Models.CategoryModel;
import com.rootdevs.storyapp.Models.StoryModel;
import com.rootdevs.storyapp.Presenters.StoryPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;
import com.rootdevs.storyapp.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class StoryFragment extends BaseFragment implements StoryView, StoryClickListener {

    private String categoryId;
    private String categoryName, featuredImage = null;
    private RecyclerView recyclerView;
    private StoriesAdapter adapter;
    private List<StoryModel> list;
    private ProgressDialog dialog;
    private View bDialog;
    private StoryPresenter presenter;
    private ImageView addStory;
    private TextView cName;
    private BottomSheetDialog bottom_sheet_dialog;

    public StoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.categoryId = bundle.getString("categoryId");
        this.categoryName = bundle.getString("categoryName");
        dialog = getProgressDialog("Please wait", "API Call in progress", false, getContext());
        presenter = new StoryPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addStory = view.findViewById(R.id.addStory);
        cName = view.findViewById(R.id.categoryName);
        cName.setText(categoryName);
        if(isAdmin()) addStory.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new StoriesAdapter(list, this, isAdmin(), requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        bottom_sheet_dialog = new BottomSheetDialog(requireContext());
        bDialog = View.inflate(getContext(), R.layout.add_story_lay, null);
        bottom_sheet_dialog.setContentView(bDialog);

        presenter.getStories(categoryId);

        addStory.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    @Override
    public void categoryFetchSuccess(JSONObject object) {

    }

    @Override
    public void categoryFetchFailure(VolleyError e) {

    }

    @Override
    public void categoryAddSuccess(JSONObject object) {

    }

    @Override
    public void categoryAddFailure(VolleyError e) {

    }

    @Override
    public void deleteCategorySuccess(JSONObject object) {

    }

    @Override
    public void deleteCategoryFailure(VolleyError e) {

    }

    @Override
    public void getStoriesSuccess(JSONObject object) {
        try {
            //Toast.makeText(requireContext(), "here",Toast.LENGTH_SHORT).show();
            if(object.getString("message").equals("Success")){
                list.clear();
                JSONArray array = object.getJSONArray("response");
                for(int i = 0; i < array.length(); i++){
                    //Toast.makeText(requireContext(), "In loop",Toast.LENGTH_SHORT).show();
                    JSONObject obj = array.getJSONObject(i);
                    list.add(new StoryModel(
                            obj.getString("storyId"),
                            obj.getString("storyName"),
                            obj.getString("categoryId"),
                            obj.getString("storySummary"),
                            obj.getString("content"),
                            obj.getString("featuredLink")));
                }
                //Toast.makeText(requireContext(), "OutSide loop",Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void getStoriesFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void addStorySuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                bottom_sheet_dialog.dismiss();
                getAlertDialog("Success", "Story Added Successfully", getContext()).show();
                presenter.getStories(categoryId);
            }
            else getAlertDialog("Error", "Some Error Occurred", getContext()).show();
        } catch (JSONException e) {
            e.printStackTrace();
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
        }

    }

    @Override
    public void addStoryFailure(VolleyError e) {
        bottom_sheet_dialog.dismiss();
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void deleteStorySuccess(JSONObject object) {
        getAlertDialog("Success", "Story Deleted", getContext()).show();
        presenter.getStories(categoryId);
    }

    @Override
    public void deleteStoryFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void uploadImageSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("File Uploaded")){
                ImageView v = bDialog.findViewById(R.id.uploadImageView);
                String link = object.getJSONObject("response").getString("fileDownloadUri");
                this.featuredImage = link;
                if(link.contains("http://localhost"))
                    link = link.replace("http://localhost", Constants.domain);
                Glide.with(requireContext()).load(link).centerCrop().into(v);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadImageFailure(VolleyError e) {
        getAlertDialog("Image Upload Error", "Please try again", getContext()).show();
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    public void showBottomSheet(){

        bottom_sheet_dialog.show();

        EditText storyName, summary, content;
        RelativeLayout submit;
        ImageView preview;
        Button uploadImage;

        storyName = bDialog.findViewById(R.id.storyName);
        summary = bDialog.findViewById(R.id.storySummary);
        content = bDialog.findViewById(R.id.content);
        preview = bDialog.findViewById(R.id.uploadImageView);
        uploadImage = bDialog.findViewById(R.id.uploadImage);
        submit = bDialog.findViewById(R.id.submit);

        uploadImage.setOnClickListener( view -> {
            imagePicker();
        });

        submit.setOnClickListener(v -> {
            String name = storyName.getText().toString().trim();
            String summry = summary.getText().toString().trim();
            String cont = content.getText().toString().trim();

            if(!TextUtils.isEmpty(name)){
                if(!TextUtils.isEmpty(summry)){
                    if(!TextUtils.isEmpty(cont)){
                        if(featuredImage != null && !featuredImage.isEmpty()){
                            StoryModel story = new StoryModel(null, name, categoryId, summry, cont, featuredImage);
                            presenter.addStory(story);
                        }
                        else getAlertDialog("Missing Image", "Please Upload Image", getContext()).show();
                    }
                    else content.setError("Please Enter Story content");
                }
                else summary.setError("Please enter Story Summary");
            }
            else storyName.setError("Please enter Story Name");
        });

    }

    public void imagePicker(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + requireActivity().getPackageName()));
            startActivity(intent);
        }
        else {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 100);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                presenter.uploadImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStoryClick(StoryModel story) {
        Bundle args = new Bundle();
        args.putString("storyId",story.getId());
        args.putString("storyName",story.getStoryName());
        args.putString("categoryId",story.getCategoryId());
        args.putString("storySummary",story.getStorySummary());
        args.putString("content",story.getContent());
        args.putString("featuredLink",story.getFeaturedLink());

        FullStoryFragment fragment = new FullStoryFragment();
        fragment.setArguments(args);

        addFragment(fragment, "Fullstory");
    }

    @Override
    public void deleteStory(String id) {
        presenter.deleteStory(id);
    }
}