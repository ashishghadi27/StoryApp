package com.rootdevs.storyapp.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface QtsAnsView {

    void getMCQsSuccess(JSONObject object);
    void getMCQsFailure(VolleyError e);

    void getAssignsSuccess(JSONObject object);
    void getAssignsFailure(VolleyError e);

    void addMCQsSuccess(JSONObject object);
    void addMCQsFailure(VolleyError e);

    void addAssignsSuccess(JSONObject object);
    void addAssignsFailure(VolleyError e);

    void deleteMCQsSuccess(JSONObject object);
    void deleteMCQsFailure(VolleyError e);

    void deleteAssignsSuccess(JSONObject object);
    void deleteAssignsFailure(VolleyError e);

    void answerMCQsSuccess(JSONObject object);
    void answerMCQsFailure(VolleyError e);

    void answerAssignsSuccess(JSONObject object);
    void answerAssignsFailure(VolleyError e);

    void showProgress();
    void hideProgress();

}
