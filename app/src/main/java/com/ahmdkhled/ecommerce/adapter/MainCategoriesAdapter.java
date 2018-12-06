package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.ProductsActivity;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Category;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainCategoriesAdapter extends RecyclerView.Adapter<MainCategoriesAdapter.CategoryHolder>{

    private Context context;
    private ArrayList<Category> categories;

    public MainCategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.main_category_item,parent,false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category=categories.get(position);
        holder.categoryName.setText(category.getName());
        Glide.with(context).load(category.getImage()).into(holder.categoryImage);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage=itemView.findViewById(R.id.mainCategoryImage);
            categoryName=itemView.findViewById(R.id.mainCategoryName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ProductsActivity.class);
                    intent.putExtra(ProductsActivity.CATEGORY_ID_KEY
                            ,categories.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
