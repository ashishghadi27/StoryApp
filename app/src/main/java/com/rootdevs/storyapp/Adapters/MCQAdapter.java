package com.rootdevs.storyapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rootdevs.storyapp.Interfaces.MCQClickListener;
import com.rootdevs.storyapp.Models.MCQModel;
import com.rootdevs.storyapp.R;

import java.util.List;

public class MCQAdapter extends RecyclerView.Adapter<MCQAdapter.MyViewHolder>{

    private List<MCQModel> list;
    private MCQClickListener listener;
    private boolean isAdmin;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ques, marks, userAns;
        RelativeLayout cardView;
        ImageView delete;
        RadioGroup radioGroup;
        RadioButton option1, option2, option3, option4;
        Button save;

        public MyViewHolder(View view) {
            super(view);
            ques = view.findViewById(R.id.ques);
            marks = view.findViewById(R.id.marks);
            radioGroup = view.findViewById(R.id.radioGroup);
            option1 = view.findViewById(R.id.option1);
            option2 = view.findViewById(R.id.option2);
            option3 = view.findViewById(R.id.option3);
            option4 = view.findViewById(R.id.option4);
            cardView = view.findViewById(R.id.card);
            delete = view.findViewById(R.id.delete);
            save = view.findViewById(R.id.saveMcq);
            save.setVisibility(View.GONE);
            userAns = view.findViewById(R.id.userAnswer);
            if(!isAdmin) delete.setVisibility(View.GONE);

        }
    }

    public MCQAdapter(List<MCQModel> list, MCQClickListener listener, boolean isAdmin) {
        this.list = list;
        this.listener = listener;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public MCQAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mcq_lay, parent, false);
        return new MCQAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MCQAdapter.MyViewHolder holder, int position) {
        MCQModel mcq = list.get(position);

        holder.ques.setText(mcq.getQues());
        holder.delete.setOnClickListener(view -> {
            listener.deleteMCQ(mcq.getMcqId());
        });
        holder.marks.setText(mcq.getMarks() + " marks");
        holder.option1.setText(" " + mcq.getOption1());
        holder.option2.setText(" " + mcq.getOption2());
        holder.option3.setText(" " + mcq.getOption3());
        holder.option4.setText(" " + mcq.getOption4());

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                holder.save.setVisibility(View.VISIBLE);
                String answer;
                Log.v("OPTION", radioGroup.getCheckedRadioButtonId() + "");
                if(i == holder.option1.getId()){
                    answer = holder.option1.getText().toString();
                }
                else if(i == holder.option2.getId()){
                    answer = holder.option2.getText().toString();
                }
                else if(i == holder.option3.getId()){
                    answer = holder.option3.getText().toString();
                }
                else answer = holder.option4.getText().toString();
                Log.v("OPTION", answer);
                holder.userAns.setText(answer);
            }
        });

        holder.save.setOnClickListener(view -> {
            holder.save.setVisibility(View.GONE);
            listener.onSaveUserAnswer(holder.userAns.getText().toString(), mcq.getSetId(), mcq.getMcqId());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
