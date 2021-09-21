package com.rootdevs.storyapp.Presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Interfaces.ApiHandler;
import com.rootdevs.storyapp.Interfaces.StoryView;
import com.rootdevs.storyapp.Models.StoryModel;
import com.rootdevs.storyapp.Utils.APICaller;
import com.rootdevs.storyapp.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class StoryPresenter implements ApiHandler {

    private APICaller apiCaller;
    private StoryView view;


    public StoryPresenter(Context context, StoryView view) {
        this.view = view;
        apiCaller = new APICaller(context, this);
    }

    public void getCategories(){
        view.showProgress();
        apiCaller.getCall(Constants.getCategoriesApi, Constants.getCategoriesRequestId);
    }

    public void addCategory(String cname, String cdesc){
        JSONObject object = new JSONObject();
        try {
            object.put("name", cname);
            object.put("desc", cdesc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.showProgress();
        apiCaller.postCall(Constants.addCategoriesApi, object, Constants.addCategoriesRequestId);
    }

    public void deleteCategory(String id){
        view.showProgress();
        apiCaller.deleteCall(Constants.deleteCategoriesApi + id, Constants.deleteCategoriesRequestId);
    }

    public void uploadImage(Bitmap bitmap){
        view.showProgress();
        apiCaller.uploadBitmap(bitmap, Constants.uploadImageRequestId);
    }

    public void uploadVideo(Uri uri){
        view.showProgress();
        apiCaller.uploadVideo(uri, Constants.uploadImageRequestId);
    }

    public void getStories(String categoryId){
        view.showProgress();
        apiCaller.getCall(Constants.getStoriesApi + categoryId, Constants.getStoriesRequestId);
    }

    public void addStory(StoryModel story){
        view.showProgress();
        JSONObject object = new JSONObject();
        try {
            object.put("storyName", story.getStoryName());
            object.put("categoryId", story.getCategoryId());
            object.put("storySummary", story.getStorySummary());
            object.put("content", story.getContent());
            object.put("featuredLink", story.getFeaturedLink());
            apiCaller.postCall(Constants.addStoriesApi, object, Constants.addStoriesRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteStory(String id){
        view.showProgress();
        apiCaller.deleteCall(Constants.deleteStoriesApi + id, Constants.deleteStoriesRequestId);
    }

    @Override
    public void success(JSONObject object, int requestId) {
        switch(requestId){
            case Constants.getCategoriesRequestId:
                view.hideProgress();
                view.categoryFetchSuccess(object);
                break;

            case Constants.addCategoriesRequestId:
                view.hideProgress();
                view.categoryAddSuccess(object);
                break;

            case Constants.deleteCategoriesRequestId:
                view.hideProgress();
                view.deleteCategorySuccess(object);
                break;

            case Constants.getStoriesRequestId:
                view.hideProgress();
                view.getStoriesSuccess(object);
                break;

            case Constants.addStoriesRequestId:
                view.hideProgress();
                view.addStorySuccess(object);
                break;

            case Constants.deleteStoriesRequestId:
                view.hideProgress();
                view.deleteStorySuccess(object);
                break;

            case Constants.uploadImageRequestId:
                view.hideProgress();
                view.uploadImageSuccess(object);
                break;
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch(requestId){
            case Constants.getCategoriesRequestId:
                view.hideProgress();
                view.categoryFetchFailure(e);
                break;

            case Constants.addCategoriesRequestId:
                view.hideProgress();
                view.categoryAddFailure(e);
                break;

            case Constants.deleteCategoriesRequestId:
                view.hideProgress();
                view.deleteCategoryFailure(e);
                break;

            case Constants.getStoriesRequestId:
                view.hideProgress();
                view.getStoriesFailure(e);
                break;

            case Constants.addStoriesRequestId:
                view.hideProgress();
                view.addStoryFailure(e);
                break;

            case Constants.deleteStoriesRequestId:
                view.hideProgress();
                view.deleteStoryFailure(e);
                break;

            case Constants.uploadImageRequestId:
                view.hideProgress();
                view.uploadImageFailure(e);
                break;
        }
    }
}
