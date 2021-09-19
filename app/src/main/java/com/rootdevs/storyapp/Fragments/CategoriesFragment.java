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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.storyapp.Adapters.CategoriesAdapter;
import com.rootdevs.storyapp.Interfaces.CategoryClickListener;
import com.rootdevs.storyapp.Interfaces.StoryView;
import com.rootdevs.storyapp.Models.CategoryModel;
import com.rootdevs.storyapp.Presenters.StoryPresenter;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesFragment extends BaseFragment implements StoryView, CategoryClickListener {

    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private List<CategoryModel> list;
    private ProgressDialog dialog;
    private StoryPresenter presenter;
    private ImageView addCategory;
    private BottomSheetDialog bottom_sheet_dialog;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = getProgressDialog("Please wait", "API Call in progress", false, getContext());
        presenter = new StoryPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addCategory = view.findViewById(R.id.addCategory);
        if(isAdmin()) addCategory.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new CategoriesAdapter(list, this, isAdmin());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.getCategories();

        addCategory.setOnClickListener(v -> {
            showBottomSheet();
        });
    }

    @Override
    public void categoryFetchSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                list.clear();
                JSONArray array = object.getJSONArray("response");
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    list.add(new CategoryModel(
                            obj.getString("id"),
                            obj.getString("name"),
                            obj.getString("desc")
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
    public void categoryFetchFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void categoryAddSuccess(JSONObject object) {
        bottom_sheet_dialog.dismiss();
        try {
            if(object.getString("message").equals("Success"))
                getAlertDialog("Success", "Category Added", getContext()).show();
            presenter.getCategories();
        } catch (JSONException e) {
            getAlertDialog("Error", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void categoryAddFailure(VolleyError e) {
        bottom_sheet_dialog.dismiss();
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void deleteCategorySuccess(JSONObject object) {
        getAlertDialog("Success", "Category Deleted", getContext()).show();
        presenter.getCategories();
    }

    @Override
    public void deleteCategoryFailure(VolleyError e) {
        getAlertDialog("Error", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void getStoriesSuccess(JSONObject object) {

    }

    @Override
    public void getStoriesFailure(VolleyError e) {

    }

    @Override
    public void addStorySuccess(JSONObject object) {

    }

    @Override
    public void addStoryFailure(VolleyError e) {

    }

    @Override
    public void deleteStorySuccess(JSONObject object) {

    }

    @Override
    public void deleteStoryFailure(VolleyError e) {

    }

    @Override
    public void uploadImageSuccess(JSONObject object) {

    }

    @Override
    public void uploadImageFailure(VolleyError e) {

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
    public void onCategoryClick(String id) {

    }

    @Override
    public void deleteCategory(String id) {
        presenter.deleteCategory(id);
    }

    public void showBottomSheet(){
        bottom_sheet_dialog = new BottomSheetDialog(requireContext());
        View dialog = View.inflate(getContext(), R.layout.add_category_lay, null);
        bottom_sheet_dialog.setContentView(dialog);
        bottom_sheet_dialog.show();

        EditText cname, cdesc;
        RelativeLayout submit;

        cname = dialog.findViewById(R.id.categoryName);
        cdesc = dialog.findViewById(R.id.categoryDesc);
        submit = dialog.findViewById(R.id.submit);

        submit.setOnClickListener(v -> {
            String name = cname.getText().toString().trim();
            String desc = cdesc.getText().toString().trim();
            if(!TextUtils.isEmpty(name)){
                if(!TextUtils.isEmpty(desc)){
                    presenter.addCategory(name, desc);
                }
                else cname.setError("Please enter Category Description");
            }
            else cname.setError("Please enter Category Name");
        });

    }
}