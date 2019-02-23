package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.ui.ProductDetail;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (product.getMedia()!=null){
            String url=product.getMedia().get(0).getUrl();
            Glide.with(context)
                    .load(url)
                    .into(holder.img);
        }else {
            holder.img.setImageResource(R.drawable.placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ImageView img,unLike;
        TextView name,price;
        ProductHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.fav_product_img);
            unLike=itemView.findViewById(R.id.unlike);
            name=itemView.findViewById(R.id.fav_product_name);
            price=itemView.findViewById(R.id.fav_product_price);

            unLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long userId=new SessionManager(context).getId();
                    long productId=productsList.get(getAdapterPosition()).getId();
                    //Log.d("FAVORITEE","productId "+productId+" userId "+userId);
                    deleteProduct(productId,userId);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,ProductDetail.class);
                    intent.putExtra(ProductDetail.PRODUCT_KEY,productsList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }

    private void deleteProduct(final long productId, long userId ){
        RetrofetClient.getApiService()
                .deleteFavoriteProduct(productId,userId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Product p=new Product((int) productId);
                            productsList.remove(p);
                            notifyDataSetChanged();
                            Toast.makeText(context,"product removed ",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"error removing product from favorites ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context,"error removing product from favorites ",Toast.LENGTH_SHORT).show();
                        Log.d("FAVORITEE",t.getMessage());
                    }
                });
    }
}
