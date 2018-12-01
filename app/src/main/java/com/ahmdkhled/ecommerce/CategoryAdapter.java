package com.ahmdkhled.ecommerce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.model.CategoryResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    ArrayList<CategoryResponse> categoriesArrayList;
    Context context;

    public CategoryAdapter(ArrayList<CategoryResponse> categoriesArrayList, Context context) {
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("catadapter","oncreate");
        View v = LayoutInflater.from(context).inflate(R.layout.category_item,viewGroup,false)  ;
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

     //   Glide.with(context).load(categoriesArrayList.get(position).getImage()).into(holder.image);
         holder.name.setText(categoriesArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        Log.d("tagg","count "+categoriesArrayList.size());
        return categoriesArrayList.size();
    }
     class CategoryHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;


         public CategoryHolder(@NonNull View itemView) {
             super(itemView);
             image=itemView.findViewById(R.id.category_img);
             name=itemView.findViewById(R.id.category_name);
         }
     }
}
