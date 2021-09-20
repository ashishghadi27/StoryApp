package com.rootdevs.storyapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rootdevs.storyapp.Interfaces.SetClickListener;
import com.rootdevs.storyapp.Interfaces.UserClicListener;
import com.rootdevs.storyapp.Models.ScoreModel;
import com.rootdevs.storyapp.Models.SetModel;
import com.rootdevs.storyapp.Models.UserModel;
import com.rootdevs.storyapp.R;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.MyViewHolder>{

    private List<ScoreModel> list;

    public ScoreAdapter(List<ScoreModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score_lay, parent, false);
        return new ScoreAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScoreModel set = list.get(position);
        holder.storyName.setText(set.getStoryName());
        holder.setName.setText(set.getSetName());
        holder.score.setText(set.getMarks() + " marks");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView setName, storyName, score;
        RelativeLayout cardView;

        public MyViewHolder(View view) {
            super(view);
            setName = view.findViewById(R.id.setName);
            storyName = view.findViewById(R.id.storyName);
            score = view.findViewById(R.id.score);
        }

    }

}

