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

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.ui.ProductsActivity;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsHolder> {

    private ArrayList<Product> productsList;
    private Context context;


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
        holder.price.setText(String.valueOf(productsList.get(position).getPrice()));
        holder.name.setText(productsList.get(position).getName());



    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductsHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView productImage;
        ProductsHolder(View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context,ProductsActivity.class);
                    context.startActivity(intent);
                }
            });

        }



    }
}