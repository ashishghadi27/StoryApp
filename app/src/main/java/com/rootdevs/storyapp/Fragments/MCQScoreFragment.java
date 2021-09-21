package com.rootdevs.storyapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Adapters.ScoreAdapter;
import com.rootdevs.storyapp.Adapters.UserAdapter;
import com.rootdevs.storyapp.Interfaces.ApiHandler;
import com.rootdevs.storyapp.Models.ScoreModel;
import com.rootdevs.storyapp.Models.UserModel;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.APICaller;
import com.rootdevs.storyapp.Utils.BaseFragment;
import com.rootdevs.storyapp.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MCQScoreFragment extends BaseFragment implements ApiHandler {

    private RecyclerView recyclerView;
    private ScoreAdapter adapter;
    private List<ScoreModel> list;
    private ProgressDialog dialog;
    private APICaller apiCaller;
    private String userId, name;
    private TextView nameTv;

    public MCQScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        name = bundle.getString(name);
        dialog = getProgressDialog("Please wait", "API Call in progress", false, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m_c_q_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        nameTv = view.findViewById(R.id.userName);
        list = new ArrayList<>();
        adapter = new ScoreAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        apiCaller = new APICaller(requireContext(), this);
        apiCaller.getCall(Constants.getUsersScore + userId, Constants.getAllUsersScoreRequestId);
        dialog.show();
    }

    @Override
    public void success(JSONObject object, int requestId) {
        if(requestId == Constants.getAllUsersScoreRequestId){
            dialog.dismiss();
            try {
                if(object.getString("message").equals("Success")){
                    list.clear();
                    JSONArray array = object.getJSONArray("response");
                    for(int i = 0; i < array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        list.add(new ScoreModel(
                                obj.getString("setName"),
                                obj.getString("storyName"),
                                obj.getString("marks")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                getAlertDialog("Error", "Some Error Occurred", getContext()).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        dialog.dismiss();
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }
}