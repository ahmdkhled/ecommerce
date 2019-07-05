package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Media;
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
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        v.setLayoutParams(layoutParams);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=productsList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(context.getString(R.string.product_price,product.getPrice()));
        if (product.getPrice_after()!=null){
            holder.price_after.setVisibility(View.VISIBLE);
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.price_after.setText(product.getPrice_after());
            holder.price_after.setText(context.getString(R.string.product_price,product.getPrice_after()));

        }
        ArrayList<Media> media=product.getMedia();
        if (media!=null&&media.size()>0){
            String imageUrl=media.get(0).getUrl();
            Glide.with(context).load(imageUrl).into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name,price,price_after;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            price_after=itemView.findViewById(R.id.product_price_after);

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
