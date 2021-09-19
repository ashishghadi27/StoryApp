package com.rootdevs.storyapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rootdevs.storyapp.Interfaces.CategoryClickListener;
import com.rootdevs.storyapp.Models.CategoryModel;
import com.rootdevs.storyapp.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private List<CategoryModel> list;
    private CategoryClickListener listener;
    private boolean isAdmin;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName, categoryDesc;
        RelativeLayout cardView;
        ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            categoryName = view.findViewById(R.id.categoryName);
            categoryDesc = view.findViewById(R.id.description);
            cardView = view.findViewById(R.id.card);
            delete = view.findViewById(R.id.delete);

            if(!isAdmin) delete.setVisibility(View.GONE);

        }

    }

    public CategoriesAdapter(List<CategoryModel> list, CategoryClickListener listener, boolean isAdmin) {
        this.list = list;
        this.listener = listener;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_lay, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.MyViewHolder holder, int position) {
        CategoryModel category = list.get(position);
        holder.categoryName.setText(category.getCategoryName());
        holder.categoryDesc.setText(category.getCategoryDesc());

        holder.delete.setOnClickListener(view -> {
            listener.deleteCategory(category.getCategoryId());
        });

        holder.cardView.setOnClickListener(view -> {
            listener.onCategoryClick(category.getCategoryId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
