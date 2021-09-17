package com.rootdevs.storyapp.Presenters;

import android.content.Context;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Interfaces.ApiHandler;
import com.rootdevs.storyapp.Interfaces.AuthView;
import com.rootdevs.storyapp.Utils.APICaller;
import com.rootdevs.storyapp.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthPresenter implements ApiHandler {

    private APICaller apiCaller;
    private AuthView view;

    public AuthPresenter(Context context, AuthView view) {
        this.view = view;
        apiCaller = new APICaller(context, this);
    }

    public void login(String email, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        view.showProgress();
        apiCaller.postCall(Constants.loginPostApi, jsonObject, Constants.loginPostApiRequestId);
    }

    public void register(JSONObject jsonObject){
        view.showProgress();
        apiCaller.postCall(Constants.registerPostApi, jsonObject, Constants.registerPostRequestId);
    }

    public void getOTP(String email){
        view.showProgress();
        apiCaller.getCall(Constants.getOtpApi + email, Constants.getOtpRequestId);
    }

    public void updatePass(String email, String password) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        view.showProgress();
        apiCaller.postCall(Constants.updatePassPostApi, jsonObject, Constants.updatePassRequestId);
    }

    @Override
    public void success(JSONObject object, int requestId) {
        switch (requestId){
            case Constants.loginPostApiRequestId:
                view.signInSuccess(object);
                view.hideProgress();
                break;
            case Constants.registerPostRequestId:
                view.signUpSuccess(object);
                view.hideProgress();
                break;
            case Constants.getOtpRequestId:
                view.otpReceived(object);
                view.hideProgress();
                break;
            case Constants.updatePassRequestId:
                view.passUpdated(object);
                view.hideProgress();
                break;
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch (requestId){
            case Constants.loginPostApiRequestId:
                view.signInFailure(e);
                view.hideProgress();
                break;
            case Constants.registerPostRequestId:
                view.signUpFailure(e);
                view.hideProgress();
                break;
            case Constants.getOtpRequestId:
                view.otpError(e);
                view.hideProgress();
                break;
            case Constants.updatePassRequestId:
                view.passUpdateFailure(e);
                view.hideProgress();
                break;
        }
    }
}
