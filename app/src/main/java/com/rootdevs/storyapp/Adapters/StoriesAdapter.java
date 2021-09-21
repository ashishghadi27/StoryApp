package com.rootdevs.storyapp.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rootdevs.storyapp.Interfaces.CategoryClickListener;
import com.rootdevs.storyapp.Interfaces.StoryClickListener;
import com.rootdevs.storyapp.Models.CategoryModel;
import com.rootdevs.storyapp.Models.StoryModel;
import com.rootdevs.storyapp.R;
import com.rootdevs.storyapp.Utils.Constants;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> {

    private List<StoryModel> list;
    private boolean isAdmin;
    private StoryClickListener listener;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView storyName, summary;
        RelativeLayout cardView;
        ImageView delete, storyImage;

        public MyViewHolder(View view) {
            super(view);
            storyName = view.findViewById(R.id.storyName);
            summary = view.findViewById(R.id.summary);
            cardView = view.findViewById(R.id.card);
            delete = view.findViewById(R.id.delete);
            storyImage = view.findViewById(R.id.storyImage);
            if(!isAdmin) delete.setVisibility(View.GONE);
        }

    }

    public StoriesAdapter(List<StoryModel> list, StoryClickListener listener, boolean isAdmin, Context context) {
        this.list = list;
        this.listener = listener;
        this.isAdmin = isAdmin;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_lay, parent, false);
        return new StoriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesAdapter.MyViewHolder holder, int position) {

        StoryModel story = list.get(position);
        holder.storyName.setText(story.getStoryName());
        if(!TextUtils.isEmpty(story.getStorySummary()))
            holder.summary.setText(story.getStorySummary());
        else holder.summary.setVisibility(View.GONE);
        String link = story.getFeaturedLink();
        if(link.contains("http://localhost"))
            link = link.replace("http://localhost", Constants.domain);
        Glide.with(context)  //2
                .load(link) //3
                .centerCrop() //4
                .placeholder(R.drawable.user) //5
                .error(R.drawable.user)
                .into(holder.storyImage);

        holder.delete.setOnClickListener(view -> {
            listener.deleteStory(story.getId());
        });

        holder.cardView.setOnClickListener( view -> {
            listener.onStoryClick(story);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
