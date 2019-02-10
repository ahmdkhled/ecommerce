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
import com.ahmdkhled.ecommerce.ui.ProductDetail;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class RecentlyAddedProducsAdapter extends RecyclerView.Adapter<RecentlyAddedProducsAdapter.ProductHolder> {

    private Context context;
    private ArrayList<Product> productsList;

    public RecentlyAddedProducsAdapter(Context context, ArrayList<Product> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.product_row,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=productsList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        String imageUrl=product.getMedia().get(0).getUrl();
        Glide.with(context).load(imageUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name,price;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ProductDetail.class);
                    Product product=productsList.get(getAdapterPosition());
                    intent.putExtra(ProductDetail.PRODUCT_KEY,product);
                    context.startActivity(intent);
                }
            });
        }
    }
}
