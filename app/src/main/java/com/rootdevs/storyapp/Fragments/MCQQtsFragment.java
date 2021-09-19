package com.rootdevs.storyapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.storyapp.Adapters.MCQAdapter;
import com.rootdevs.storyapp.Interfaces.MCQClickListener;
import com.rootdevs.storyapp.Interfaces.QtsAnsView;
import com.rootdevs.storyapp.Models.MCQModel;
import com.rootdevs.storyapp.Presenters.MCQAssignmentPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MCQQtsFragment extends BaseFragment implements QtsAnsView, MCQClickListener {

    private List<MCQModel> list;
    private RecyclerView recyclerView;
    private String setId, setName;
    private TextView setNameTv;
    private ProgressDialog dialog;
    private MCQAssignmentPresenter presenter;
    private ImageView addMCQ;
    private BottomSheetDialog bottom_sheet_dialog;
    private MCQAdapter adapter;

    public MCQQtsFragment() {
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
        return inflater.inflate(R.layout.fragment_m_c_q_qts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addMCQ = view.findViewById(R.id.addMCQ);
        setNameTv = view.findViewById(R.id.setName);
        setNameTv.setText(setName);
        if(isAdmin()) addMCQ.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new MCQAdapter(list, this, isAdmin());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.getMCQs(setId);

        addMCQ.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    @Override
    public void onSaveUserAnswer(String answer, String setId, String quesId) {
        presenter.saveMCQAnswer(answer, setId, quesId, getUserId());
    }

    @Override
    public void deleteMCQ(String id) {
        presenter.deleteMCQs(id);
    }

    @Override
    public void getMCQsSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                list.clear();
                JSONArray array = object.getJSONArray("response");
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    MCQModel mcqModel = new MCQModel();
                    mcqModel.setAnswer(obj.getString("answer"));
                    mcqModel.setOption1(obj.getString("option1"));
                    mcqModel.setOption2(obj.getString("option2"));
                    mcqModel.setOption3(obj.getString("option3"));
                    mcqModel.setOption4(obj.getString("option4"));
                    mcqModel.setMarks(obj.getString("marks"));
                    mcqModel.setQues(obj.getString("ques"));
                    mcqModel.setMcqId(obj.getString("quesId"));
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
    public void getMCQsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void getAssignsSuccess(JSONObject object) {

    }

    @Override
    public void getAssignsFailure(VolleyError e) {

    }

    @Override
    public void addMCQsSuccess(JSONObject object) {
        bottom_sheet_dialog.dismiss();
        try {
            if(object.getString("message").equals("Success"))
                getAlertDialog("Success", "MCQ Added", getContext()).show();
            presenter.getMCQs(setId);
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void addMCQsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void addAssignsSuccess(JSONObject object) {

    }

    @Override
    public void addAssignsFailure(VolleyError e) {

    }

    @Override
    public void deleteMCQsSuccess(JSONObject object) {
        getAlertDialog("Success", "MCQ Deleted", getContext()).show();
        presenter.getMCQs(setId);
    }

    @Override
    public void deleteMCQsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void deleteAssignsSuccess(JSONObject object) {

    }

    @Override
    public void deleteAssignsFailure(VolleyError e) {

    }

    @Override
    public void answerMCQsSuccess(JSONObject object) {
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
    public void answerMCQsFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void answerAssignsSuccess(JSONObject object) {

    }

    @Override
    public void answerAssignsFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    public void showBottomSheet(){
        bottom_sheet_dialog = new BottomSheetDialog(requireContext());
        View dialog = View.inflate(getContext(), R.layout.add_mcq_lay, null);
        bottom_sheet_dialog.setContentView(dialog);
        bottom_sheet_dialog.show();

        EditText ques, answer, option1, option2, option3, option4, marks;
        RelativeLayout submit;

        ques = dialog.findViewById(R.id.ques);
        answer = dialog.findViewById(R.id.answer);
        option1 = dialog.findViewById(R.id.option1);
        option2 = dialog.findViewById(R.id.option2);
        option3 = dialog.findViewById(R.id.option3);
        option4 = dialog.findViewById(R.id.option4);
        marks = dialog.findViewById(R.id.marks);
        submit = dialog.findViewById(R.id.submit);

        submit.setOnClickListener(v -> {
            String qs = ques.getText().toString().trim();
            String ans = answer.getText().toString().trim();
            String op1 = option1.getText().toString().trim();
            String op2 = option2.getText().toString().trim();
            String op3 = option3.getText().toString().trim();
            String op4 = option4.getText().toString().trim();
            String mks = marks.getText().toString().trim();
            if(TextUtils.isEmpty(qs) ||
                    TextUtils.isEmpty(ans) ||
                    TextUtils.isEmpty(op1) ||
                    TextUtils.isEmpty(op2) ||
                    TextUtils.isEmpty(op3) ||
                    TextUtils.isEmpty(op4) ||
                    TextUtils.isEmpty(mks)){
                getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            }
            else {
                MCQModel mcqModel = new MCQModel();
                mcqModel.setAnswer(ans);
                mcqModel.setOption1(op1);
                mcqModel.setOption2(op2);
                mcqModel.setOption3(op3);
                mcqModel.setOption4(op4);
                mcqModel.setMarks(mks);
                mcqModel.setQues(qs);
                mcqModel.setSetId(setId);
                presenter.addMCQs(mcqModel);
            }
        });

    }
}