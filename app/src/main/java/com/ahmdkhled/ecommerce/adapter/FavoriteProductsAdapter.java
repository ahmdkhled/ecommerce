package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteProductsAdapter  extends RecyclerView.Adapter<FavoriteProductsAdapter.ProductHolder>{


    private Context context;
    private ArrayList<Product>  productsList;

    public FavoriteProductsAdapter(Context context, ArrayList<Product> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.fav_product_row,parent,false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=productsList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        Glide.with(context)
                .load(product.getMedia().get(0).getUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ImageView img,unLike;
        TextView name,price;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.fav_product_img);
            unLike=itemView.findViewById(R.id.unlike);
            name=itemView.findViewById(R.id.fav_product_name);
            price=itemView.findViewById(R.id.fav_product_price);
        }
    }
}
