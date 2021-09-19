package com.rootdevs.storyapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.storyapp.Adapters.CategoriesAdapter;
import com.rootdevs.storyapp.Adapters.SetAdapter;
import com.rootdevs.storyapp.Interfaces.SetClickListener;
import com.rootdevs.storyapp.Interfaces.SetView;
import com.rootdevs.storyapp.Models.CategoryModel;
import com.rootdevs.storyapp.Models.SetModel;
import com.rootdevs.storyapp.Presenters.MCQAssignSetPresenter;
import com.rootdevs.storyapp.Presenters.StoryPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MCQSetFragment extends BaseFragment implements SetView, SetClickListener {

    private String storyId;
    private String storyName;
    private RecyclerView recyclerView;
    private SetAdapter adapter;
    private List<SetModel> list;
    private MCQAssignSetPresenter presenter;
    private ProgressDialog dialog;
    private ImageView addSet;
    private BottomSheetDialog bottom_sheet_dialog;

    public MCQSetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.storyId = bundle.getString("storyId");
        this.storyName = bundle.getString("storyName");
        dialog = getProgressDialog("Please wait", "API Call in progress", false, getContext());
        presenter = new MCQAssignSetPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m_c_q_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addSet = view.findViewById(R.id.addSet);
        if(isAdmin()) addSet.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new SetAdapter(list, this, isAdmin());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.getMCQSets(storyId);

        addSet.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    @Override
    public void getMCQSetsSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                list.clear();
                JSONArray array = object.getJSONArray("response");
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    list.add(new SetModel(
                            obj.getString("setId"),
                            obj.getString("setName"),
                            obj.getString("storyId")
                    ));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void getMCQSetsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void getAssignSetsSuccess(JSONObject object) {

    }

    @Override
    public void getAssignSetsFailure(VolleyError e) {

    }

    @Override
    public void addMCQSetsSuccess(JSONObject object) {
        bottom_sheet_dialog.dismiss();
        try {
            if(object.getString("message").equals("Success"))
                getAlertDialog("Success", "Set Added", getContext()).show();
            presenter.getMCQSets(storyId);
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void addMCQSetsFailure(VolleyError e) {
        bottom_sheet_dialog.dismiss();
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void addAssignSetsSuccess(JSONObject object) {

    }

    @Override
    public void addAssignSetsFailure(VolleyError e) {

    }

    @Override
    public void deleteMCQSetsSuccess(JSONObject object) {
        getAlertDialog("Success", "Set Deleted", getContext()).show();
        presenter.getMCQSets(storyId);
    }

    @Override
    public void deleteMCQSetsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void deleteAssignSetsSuccess(JSONObject object) {

    }

    @Override
    public void deleteAssignSetsFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void onSetClick(String id, String name) {
        MCQQtsFragment fragment = new MCQQtsFragment();
        Bundle args = new Bundle();
        args.putString("setId", id);
        args.putString("setName", name);
        fragment.setArguments(args);
        addFragment(fragment, "mcqqts");
    }

    @Override
    public void deleteSet(String id) {
        presenter.deleteMCQSets(id);
    }

    public void showBottomSheet(){
        bottom_sheet_dialog = new BottomSheetDialog(requireContext());
        View dialog = View.inflate(getContext(), R.layout.addset_lay, null);
        bottom_sheet_dialog.setContentView(dialog);
        bottom_sheet_dialog.show();

        EditText setname;
        RelativeLayout submit;

        setname = dialog.findViewById(R.id.setName);
        submit = dialog.findViewById(R.id.submit);

        submit.setOnClickListener(v -> {
            String name = setname.getText().toString().trim();
            if(!TextUtils.isEmpty(name)){
                SetModel set = new SetModel(null, name, storyId);
                presenter.addMCQSets(set);
            }
            else setname.setError("Please enter Set Name");
        });

    }
}