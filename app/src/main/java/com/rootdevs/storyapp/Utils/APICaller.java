package com.rootdevs.storyapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rootdevs.storyapp.Interfaces.ApiHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class APICaller {

    private RequestQueue queue;
    private ApiHandler handler;

    public APICaller(Context context, ApiHandler handler){
        this.queue = Volley.newRequestQueue(context);
        this.handler = handler;
    }

    public void getCall(String api, int requestId){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api, null,
                response -> {
                    Log.v("Response: ", response.toString());
                    handler.success(response, requestId);
                },
                error -> {
                    Log.v("Response:", error.getCause() + "    " + error.getMessage());
                    handler.failure(error, requestId);
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.queue.add(request);
    }

    public void deleteCall(String api, int requestId){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, api, null,
                response -> {
                    Log.v("Response: ", response.toString());
                    handler.success(response, requestId);
                },
                error -> {
                    Log.v("Response:", error.getCause() + "    " + error.getMessage());
                    handler.failure(error, requestId);
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.queue.add(request);
    }

    public void postCall(String api, JSONObject jsonObject, int requestId){
        Log.v("API", api);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, api, jsonObject,
                response -> {
                    Log.v("Response: ", response.toString());
                    handler.success(response, requestId);
                },
                error -> {
                    Log.v("Response Post:", error.getCause() + "    " + error.getMessage());
                    Log.v("ERROR", error.toString());
                    handler.failure(error, requestId);
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.queue.add(request);
    }

    public void uploadBitmap(final Bitmap bitmap, int requestId) {

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.uploadImageApi,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(new String(response.data));
                        handler.success(obj, requestId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    handler.failure(error, requestId);
                }) {

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        this.queue.add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
