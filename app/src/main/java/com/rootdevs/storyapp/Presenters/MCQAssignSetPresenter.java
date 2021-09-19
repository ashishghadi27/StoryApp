package com.rootdevs.storyapp.Presenters;

import android.content.Context;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Interfaces.ApiHandler;
import com.rootdevs.storyapp.Interfaces.SetView;
import com.rootdevs.storyapp.Models.SetModel;
import com.rootdevs.storyapp.Utils.APICaller;
import com.rootdevs.storyapp.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class MCQAssignSetPresenter implements ApiHandler {

    private APICaller apiCaller;
    private SetView view;

    public MCQAssignSetPresenter(Context context, SetView view) {
        this.view = view;
        apiCaller = new APICaller(context, this);
    }

    public void getMCQSets(String id){
        view.showProgress();
        apiCaller.getCall(Constants.getMCQSetsApi + id, Constants.getMCQSetsRequestId);
    }

    public void getAssignSets(String id){
        view.showProgress();
        apiCaller.getCall(Constants.getAssignSetsApi + id, Constants.getAssignSetsRequestId);
    }

    public void addMCQSets(SetModel set){
        JSONObject object = new JSONObject();
        try {
            object.put("setName", set.getSetName());
            object.put("storyId", set.getStoryId());
            apiCaller.postCall(Constants.addMCQSetsApi, object, Constants.addMCQSetsRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addAssignSets(SetModel set){
        JSONObject object = new JSONObject();
        try {
            object.put("assignName", set.getSetName());
            object.put("storyId", set.getStoryId());
            apiCaller.postCall(Constants.addAssignSetsApi, object, Constants.addAssignSetsRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteMCQSets(String id){
        view.showProgress();
        apiCaller.deleteCall(Constants.deleteMCQSetsApi + id, Constants.deleteMCQSetsRequestId);
    }

    public void deleteAssignSets(String id){
        view.showProgress();
        apiCaller.deleteCall(Constants.deleteAssignSetsApi + id, Constants.deleteAssignSetsRequestId);
    }

    @Override
    public void success(JSONObject object, int requestId) {

        switch (requestId){
            case Constants.getMCQSetsRequestId:
                view.hideProgress();
                view.getMCQSetsSuccess(object);
                break;

            case Constants.getAssignSetsRequestId:
                view.hideProgress();
                view.getAssignSetsSuccess(object);
                break;

            case Constants.addMCQSetsRequestId:
                view.hideProgress();
                view.addMCQSetsSuccess(object);
                break;

            case Constants.addAssignSetsRequestId:
                view.hideProgress();
                view.addAssignSetsSuccess(object);
                break;

            case Constants.deleteMCQSetsRequestId:
                view.hideProgress();
                view.deleteMCQSetsSuccess(object);
                break;

            case Constants.deleteAssignSetsRequestId:
                view.hideProgress();
                view.deleteAssignSetsSuccess(object);
                break;

        }

    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch (requestId){
            case Constants.getMCQSetsRequestId:
                view.hideProgress();
                view.getMCQSetsFailure(e);
                break;

            case Constants.getAssignSetsRequestId:
                view.hideProgress();
                view.getAssignSetsFailure(e);
                break;

            case Constants.addMCQSetsRequestId:
                view.hideProgress();
                view.addMCQSetsFailure(e);
                break;

            case Constants.addAssignSetsRequestId:
                view.hideProgress();
                view.addAssignSetsFailure(e);
                break;

            case Constants.deleteMCQSetsRequestId:
                view.hideProgress();
                view.deleteMCQSetsFailure(e);
                break;

            case Constants.deleteAssignSetsRequestId:
                view.hideProgress();
                view.deleteAssignSetsFailure(e);
                break;
        }
    }
}
