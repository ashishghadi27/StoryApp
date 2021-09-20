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
import com.rootdevs.storyapp.Models.SetModel;
import com.rootdevs.storyapp.Models.UserModel;
import com.rootdevs.storyapp.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private List<UserModel> list;
    private UserClicListener listener;

    public UserAdapter(List<UserModel> list, UserClicListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_lay, parent, false);
        return new UserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel set = list.get(position);
        holder.userName.setText(set.getName());
        holder.email.setText(set.getEmail());
        holder.cardView.setOnClickListener(view -> {
            listener.onUserClick(set.getUserId(), set.getName());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName, email;
        RelativeLayout cardView;

        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.userName);
            email = view.findViewById(R.id.email);
            cardView = view.findViewById(R.id.card);
        }

    }

}
