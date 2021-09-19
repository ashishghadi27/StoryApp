package com.rootdevs.storyapp.Utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.rootdevs.storyapp.R;

public class BaseFragment extends Fragment {

    public void addFragment(Fragment fragment, String tag){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_right)
                .add(R.id.fragment_container, fragment, tag).addToBackStack(tag)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String tag){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_right)
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }

    public ProgressDialog getProgressDialog(String title, String message, boolean isCancellable, Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancellable);
        return progressDialog;
    }

    public AlertDialog getAlertDialog(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialog = View.inflate(getContext(), R.layout.alert_lay, null);
        TextView titleText = dialog.findViewById(R.id.title);
        TextView messageText = dialog.findViewById(R.id.message);
        titleText.setText(title);
        messageText.setText(message);
        builder.setView(dialog);
        builder.setCancelable(true);
        return builder.create();
    }

    public void saveUserDetails(String id, String name,  String email, String isAdmin){
        SharedPreferences preferences = requireContext().getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("isAdmin", isAdmin);
        editor.apply();
    }

    public String getUserId(){
        SharedPreferences preferences = requireContext().getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("id", null);
    }

    public boolean isAdmin(){
        SharedPreferences preferences = requireContext().getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("isAdmin", "0").equals("1");
    }

    public String getEmailAddress(){
        SharedPreferences preferences = requireContext().getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("email", null);
    }

    public void signOut(){
        SharedPreferences preferences = requireContext().getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
