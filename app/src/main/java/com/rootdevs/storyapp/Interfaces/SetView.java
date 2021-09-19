package com.rootdevs.storyapp.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface SetView {

    void getMCQSetsSuccess(JSONObject object);
    void getMCQSetsFailure(VolleyError e);

    void getAssignSetsSuccess(JSONObject object);
    void getAssignSetsFailure(VolleyError e);

    void addMCQSetsSuccess(JSONObject object);
    void addMCQSetsFailure(VolleyError e);

    void addAssignSetsSuccess(JSONObject object);
    void addAssignSetsFailure(VolleyError e);

    void deleteMCQSetsSuccess(JSONObject object);
    void deleteMCQSetsFailure(VolleyError e);

    void deleteAssignSetsSuccess(JSONObject object);
    void deleteAssignSetsFailure(VolleyError e);

    void showProgress();
    void hideProgress();

}
