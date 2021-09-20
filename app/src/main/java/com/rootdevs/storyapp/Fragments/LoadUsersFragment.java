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

import com.android.volley.VolleyError;
import com.rootdevs.storyapp.Adapters.CategoriesAdapter;
import com.rootdevs.storyapp.Adapters.UserAdapter;
import com.rootdevs.storyapp.Interfaces.ApiHandler;
import com.rootdevs.storyapp.Interfaces.UserClicListener;
import com.rootdevs.storyapp.Models.CategoryModel;
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

public class LoadUsersFragment extends BaseFragment implements ApiHandler, UserClicListener {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<UserModel> list;
    private ProgressDialog dialog;
    private APICaller apiCaller;

    public LoadUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = getProgressDialog("Please wait", "API Call in progress", false, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new UserAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        apiCaller = new APICaller(requireContext(), this);
        apiCaller.getCall(Constants.getAllUsers, Constants.getAllUsersRequestId);
        dialog.show();
    }

    @Override
    public void success(JSONObject object, int requestId) {
        if(requestId == Constants.getAllUsersRequestId){
            dialog.dismiss();
            try {
                if(object.getString("message").equals("Success")){
                    list.clear();
                    JSONArray array = object.getJSONArray("response");
                    for(int i = 0; i < array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        list.add(new UserModel(
                                obj.getString("userId"),
                                obj.getString("email"),
                                obj.getString("name")
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
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void onUserClick(String userId, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("name", name);
        MCQScoreFragment fragment = new MCQScoreFragment();
        fragment.setArguments(bundle);
        addFragment(fragment,"score");
    }
}