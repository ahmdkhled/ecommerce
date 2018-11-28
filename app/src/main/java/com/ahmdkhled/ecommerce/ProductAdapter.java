package com.ahmdkhled.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsHolder> {

    ArrayList<Product> productsList;
    Context context;


    public ProductAdapter(ArrayList<Product> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }


    @NonNull
    @Override
    public ProductAdapter.ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.product_row,parent,false);

        return new ProductsHolder(v);    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductsHolder holder, int position) {
        holder.discount.setText(productsList.get(position).getDiscount());
        holder.category.setText(productsList.get(position).getCategory());
        holder.price.setText(productsList.get(position).getPrice());


    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductsHolder extends RecyclerView.ViewHolder {

        TextView discount,price,category;
        ImageView productImage;


        public ProductsHolder(View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.product_image);
            discount=itemView.findViewById(R.id.discount);
            price=itemView.findViewById(R.id.price);
            category=itemView.findViewById(R.id.category);





        }



    }
}
