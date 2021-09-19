package com.rootdevs.storyapp.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rootdevs.storyapp.Interfaces.AssignClickListener;
import com.rootdevs.storyapp.Models.AssignModel;
import com.rootdevs.storyapp.R;

import java.util.List;

public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.MyViewHolder>{

    private List<AssignModel> list;
    private AssignClickListener listener;
    private boolean isAdmin;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ques, marks;
        EditText answer;
        RelativeLayout cardView;
        ImageView delete;
        Button save;

        public MyViewHolder(View view) {
            super(view);
            ques = view.findViewById(R.id.ques);
            marks = view.findViewById(R.id.marks);
            cardView = view.findViewById(R.id.card);
            delete = view.findViewById(R.id.delete);
            save = view.findViewById(R.id.saveMcq);
            answer = view.findViewById(R.id.answer);
            if(!isAdmin) delete.setVisibility(View.GONE);

        }
    }

    public AssignAdapter(List<AssignModel> list, AssignClickListener listener, boolean isAdmin, Context context) {
        this.list = list;
        this.listener = listener;
        this.isAdmin = isAdmin;
        this.context = context;
    }

    @NonNull
    @Override
    public AssignAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assign_lay, parent, false);
        return new AssignAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignAdapter.MyViewHolder holder, int position) {
        AssignModel mcq = list.get(position);

        holder.ques.setText(mcq.getQues());
        holder.delete.setOnClickListener(view -> {
            listener.deleteMCQ(mcq.getQuesId());
        });
        holder.marks.setText(mcq.getMarks() + " marks");

        holder.delete.setOnClickListener(view -> {
            listener.deleteMCQ(mcq.getQuesId());
        });

        holder.save.setOnClickListener(view -> {
            String ans = holder.answer.getText().toString();
            if(!TextUtils.isEmpty(ans)){
                holder.save.setVisibility(View.GONE);
                listener.onSaveUserAnswer(ans, mcq.getSetId(), mcq.getQuesId());
            }
            else Toast.makeText(context, "Please answer question", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

