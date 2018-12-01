package com.ahmdkhled.ecommerce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    ArrayList<Categories> categoriesArrayList;
    Context context;

    public CategoryAdapter(ArrayList<Categories> categoriesArrayList, Context context) {
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("adapter","oncreate");
        View v = LayoutInflater.from(context).inflate(R.layout.category_item,viewGroup,false)  ;
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        Log.d("URL",categoriesArrayList.get(position).getImage());
        Glide.with(context).load(categoriesArrayList.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        Log.d("tagg","count "+categoriesArrayList.size());
        return categoriesArrayList.size();
    }
     class CategoryHolder extends RecyclerView.ViewHolder {
        ImageView image;

         public CategoryHolder(@NonNull View itemView) {
             super(itemView);
             image=itemView.findViewById(R.id.imageView);
         }
     }
}
