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
import com.rootdevs.storyapp.Models.SetModel;
import com.rootdevs.storyapp.R;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.MyViewHolder>{

    private List<SetModel> list;
    private SetClickListener listener;
    private ImageView delete;
    private boolean isAdmin;

    public SetAdapter(List<SetModel> list, SetClickListener listener, boolean isAdmin) {
        this.list = list;
        this.listener = listener;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_lay, parent, false);
        return new SetAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SetModel set = list.get(position);
        holder.setName.setText(set.getSetName());
        holder.cardView.setOnClickListener(view -> {
            listener.onSetClick(set.getSetId(), set.getSetName());
        });
        holder.delete.setOnClickListener(view -> {
            listener.deleteSet(set.getSetId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView setName;
        RelativeLayout cardView;
        ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            setName = view.findViewById(R.id.setName);
            cardView = view.findViewById(R.id.card);
            delete = view.findViewById(R.id.delete);

            if(!isAdmin) delete.setVisibility(View.GONE);

        }

    }

}
