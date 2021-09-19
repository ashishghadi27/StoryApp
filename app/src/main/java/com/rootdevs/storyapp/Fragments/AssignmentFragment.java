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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.storyapp.Adapters.AssignAdapter;
import com.rootdevs.storyapp.Adapters.MCQAdapter;
import com.rootdevs.storyapp.Interfaces.AssignClickListener;
import com.rootdevs.storyapp.Interfaces.MCQClickListener;
import com.rootdevs.storyapp.Interfaces.QtsAnsView;
import com.rootdevs.storyapp.Models.AssignModel;
import com.rootdevs.storyapp.Models.MCQModel;
import com.rootdevs.storyapp.Presenters.MCQAssignmentPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends BaseFragment implements QtsAnsView, AssignClickListener {

    private List<AssignModel> list;
    private RecyclerView recyclerView;
    private String setId, setName;
    private TextView setNameTv;
    private ProgressDialog dialog;
    private MCQAssignmentPresenter presenter;
    private ImageView addMCQ;
    private BottomSheetDialog bottom_sheet_dialog;
    private AssignAdapter adapter;

    public AssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        setId = bundle.getString("setId");
        setName = bundle.getString("setName");
        dialog = getProgressDialog("Please wait", "API Call in progress", false, getContext());
        presenter = new MCQAssignmentPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignmnet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addMCQ = view.findViewById(R.id.addAssign);
        setNameTv = view.findViewById(R.id.setName);
        setNameTv.setText(setName);
        if(isAdmin()) addMCQ.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new AssignAdapter(list, this, isAdmin(), requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.getAssign(setId);

        addMCQ.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    @Override
    public void onSaveUserAnswer(String answer, String setId, String quesId) {
        presenter.saveAssignAnswer(answer, setId, quesId, getUserId());
    }

    @Override
    public void deleteMCQ(String id) {
        presenter.deleteAssign(id);
    }

    @Override
    public void getMCQsSuccess(JSONObject object) {

    }

    @Override
    public void getMCQsFailure(VolleyError e) {

    }

    @Override
    public void getAssignsSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                list.clear();
                JSONArray array = object.getJSONArray("response");
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    AssignModel mcqModel = new AssignModel();
                    mcqModel.setAnswer(obj.getString("answer"));
                    mcqModel.setMarks(obj.getString("marks"));
                    mcqModel.setQues(obj.getString("ques"));
                    mcqModel.setQuesId(obj.getString("quesId"));
                    mcqModel.setSetId(obj.getString("setId"));
                    list.add(mcqModel);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void getAssignsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void addMCQsSuccess(JSONObject object) {

    }

    @Override
    public void addMCQsFailure(VolleyError e) {

    }

    @Override
    public void addAssignsSuccess(JSONObject object) {
        bottom_sheet_dialog.dismiss();
        try {
            if(object.getString("message").equals("Success"))
                getAlertDialog("Success", "Assignment Question Added", getContext()).show();
            presenter.getAssign(setId);
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void addAssignsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void deleteMCQsSuccess(JSONObject object) {

    }

    @Override
    public void deleteMCQsFailure(VolleyError e) {

    }

    @Override
    public void deleteAssignsSuccess(JSONObject object) {
        getAlertDialog("Success", "Question Deleted", getContext()).show();
        presenter.getAssign(setId);
    }

    @Override
    public void deleteAssignsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void answerMCQsSuccess(JSONObject object) {

    }

    @Override
    public void answerMCQsFailure(VolleyError e) {

    }

    @Override
    public void answerAssignsSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success"))
                getAlertDialog("Success", "Answer Saved", getContext()).show();
            presenter.getMCQs(setId);
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void answerAssignsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.hide();
    }

    public void showBottomSheet(){
        bottom_sheet_dialog = new BottomSheetDialog(requireContext());
        View dialog = View.inflate(getContext(), R.layout.add_assign_lay, null);
        bottom_sheet_dialog.setContentView(dialog);
        bottom_sheet_dialog.show();

        EditText ques, answer, marks;
        RelativeLayout submit;

        ques = dialog.findViewById(R.id.ques);
        answer = dialog.findViewById(R.id.answer);
        marks = dialog.findViewById(R.id.marks);
        submit = dialog.findViewById(R.id.submit);

        submit.setOnClickListener(v -> {
            String qs = ques.getText().toString().trim();
            String ans = answer.getText().toString().trim();
            String mks = marks.getText().toString().trim();
            if(TextUtils.isEmpty(qs) ||
                    TextUtils.isEmpty(ans) ||
                    TextUtils.isEmpty(mks)){
                getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            }
            else {
                AssignModel mcqModel = new AssignModel();
                mcqModel.setAnswer(ans);
                mcqModel.setMarks(mks);
                mcqModel.setQues(qs);
                mcqModel.setSetId(setId);
                presenter.addAssign(mcqModel);
            }
        });

    }
}