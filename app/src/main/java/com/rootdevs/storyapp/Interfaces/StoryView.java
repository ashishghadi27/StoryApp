package com.rootdevs.storyapp.Interfaces;

import com.android.volley.VolleyError;
import org.json.JSONObject;

public interface StoryView {

    void categoryFetchSuccess(JSONObject object);
    void categoryFetchFailure(VolleyError e);

    void categoryAddSuccess(JSONObject object);
    void categoryAddFailure(VolleyError e);

    void deleteCategorySuccess(JSONObject object);
    void deleteCategoryFailure(VolleyError e);

    void getStoriesSuccess(JSONObject object);
    void getStoriesFailure(VolleyError e);

    void addStorySuccess(JSONObject object);
    void addStoryFailure(VolleyError e);

    void deleteStorySuccess(JSONObject object);
    void deleteStoryFailure(VolleyError e);

    void uploadImageSuccess(JSONObject object);
    void uploadImageFailure(VolleyError e);

    void showProgress();
    void hideProgress();

}
