package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bumptech.glide.request.RequestOptions;

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

        Product product=productsList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        Log.d("SALLEE","sale "+product.getPrice_after());
        if (product.getPrice_after()!=null){
            holder.price_after.setVisibility(View.VISIBLE);
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.price_after.setText(product.getPrice_after());
        }
        ArrayList<Media> media=product.getMedia();
        if (media!=null&&media.size()>0){
            String imageUrl=media.get(0).getUrl();
            Log.d("SALLEE","url "+imageUrl);
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.productImage);
        }else {
            holder.productImage.setImageResource(R.drawable.placeholder);
        }


    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void addItems(ArrayList<Product> productsList){
        this.productsList.addAll(productsList);
        notifyDataSetChanged();
    }

    class ProductsHolder extends RecyclerView.ViewHolder {
        TextView name,price,price_after;
        ImageView productImage;
        ProductsHolder(View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            price_after=itemView.findViewById(R.id.product_price_after);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context,ProductDetail.class);
                    Product product = productsList.get(getAdapterPosition());
                    intent.putExtra(ProductDetail.PRODUCT_KEY,product);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }


    }
}
