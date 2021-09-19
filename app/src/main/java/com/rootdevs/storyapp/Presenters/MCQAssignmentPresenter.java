package com.rootdevs.storyapp.Presenters;

import android.content.Context;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Interfaces.ApiHandler;
import com.rootdevs.storyapp.Interfaces.QtsAnsView;
import com.rootdevs.storyapp.Models.AssignModel;
import com.rootdevs.storyapp.Models.MCQModel;
import com.rootdevs.storyapp.Utils.APICaller;
import com.rootdevs.storyapp.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class MCQAssignmentPresenter implements ApiHandler {

    private APICaller apiCaller;
    private QtsAnsView view;

    public MCQAssignmentPresenter(Context context, QtsAnsView view) {
        this.view = view;
        apiCaller = new APICaller(context, this);
    }

    public void getMCQs(String setId){
        view.showProgress();
        apiCaller.getCall(Constants.getMCQsApi + setId, Constants.getMCQsRequestId);
    }

    public void addMCQs(MCQModel mcq){
        view.showProgress();
        JSONObject object = new JSONObject();
        try {
            object.put("ques", mcq.getQues());
            object.put("answer", mcq.getAnswer());
            object.put("option1", mcq.getOption1());
            object.put("option2", mcq.getOption2());
            object.put("option3", mcq.getOption3());
            object.put("option4", mcq.getOption4());
            object.put("marks", mcq.getMarks());
            object.put("setId", mcq.getSetId());
            apiCaller.postCall(Constants.addMCQsApi, object,Constants.addMCQsRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteMCQs(String id){
        view.showProgress();
        apiCaller.deleteCall(Constants.deleteMCQsApi + id, Constants.deleteMCQsRequestId);
    }

    public void getAssign(String setId){
        view.showProgress();
        apiCaller.getCall(Constants.getAssignsApi + setId, Constants.getAssignsRequestId);
    }

    public void addAssign(AssignModel mcq){
        view.showProgress();
        JSONObject object = new JSONObject();
        try {
            object.put("ques", mcq.getQues());
            object.put("answer", mcq.getAnswer());
            object.put("marks", mcq.getMarks());
            object.put("setId", mcq.getSetId());
            apiCaller.postCall(Constants.addAssignsApi, object,Constants.addAssignsRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteAssign(String id){
        view.showProgress();
        apiCaller.deleteCall(Constants.deleteAssignsApi + id, Constants.deleteAssignsRequestId);
    }

    public void saveMCQAnswer(String answer, String setId, String quesId, String userId){
        view.showProgress();
        JSONObject object = new JSONObject();
        try {
            object.put("quesId", quesId);
            object.put("answer", answer);
            object.put("userId", userId);
            object.put("setId", setId);
            apiCaller.postCall(Constants.setMCQAnswerApi, object,Constants.setMCQAnswerRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveAssignAnswer(String answer, String setId, String quesId, String userId){
        view.showProgress();
        JSONObject object = new JSONObject();
        try {
            object.put("quesId", quesId);
            object.put("answer", answer);
            object.put("userId", userId);
            object.put("setId", setId);
            apiCaller.postCall(Constants.setAssignAnswerApi, object,Constants.setAssignAnswerRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void success(JSONObject object, int requestId) {
        switch (requestId){
            case Constants.getMCQsRequestId:
                view.hideProgress();
                view.getMCQsSuccess(object);
                break;

            case Constants.addMCQsRequestId:
                view.hideProgress();
                view.addMCQsSuccess(object);
                break;

            case Constants.deleteMCQsRequestId:
                view.hideProgress();
                view.deleteMCQsSuccess(object);
                break;

            case Constants.getAssignsRequestId:
                view.hideProgress();
                view.getAssignsSuccess(object);
                break;

            case Constants.addAssignsRequestId:
                view.hideProgress();
                view.addAssignsSuccess(object);
                break;

            case Constants.deleteAssignsRequestId:
                view.hideProgress();
                view.deleteAssignsSuccess(object);
                break;

            case Constants.setMCQAnswerRequestId:
                 view.hideProgress();
                 view.answerMCQsSuccess(object);
                 break;

            case Constants.setAssignAnswerRequestId:
                view.hideProgress();
                view.answerAssignsSuccess(object);
                break;
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch (requestId){
            case Constants.getMCQsRequestId:
                view.hideProgress();
                view.getMCQsFailure(e);
                break;

            case Constants.addMCQsRequestId:
                view.hideProgress();
                view.addMCQsFailure(e);
                break;

            case Constants.deleteMCQsRequestId:
                view.hideProgress();
                view.deleteMCQsFailure(e);
                break;

            case Constants.getAssignsRequestId:
                view.hideProgress();
                view.getAssignsFailure(e);
                break;

            case Constants.addAssignsRequestId:
                view.hideProgress();
                view.addAssignsFailure(e);
                break;

            case Constants.deleteAssignsRequestId:
                view.hideProgress();
                view.deleteAssignsFailure(e);
                break;

            case Constants.setMCQAnswerRequestId:
                view.hideProgress();
                view.answerMCQsFailure(e);
                break;

            case Constants.setAssignAnswerRequestId:
                view.hideProgress();
                view.answerAssignsFailure(e);
                break;
        }
    }
}
