package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Media;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder>{


        Context context;
        ArrayList<CartItem> cartItemList;

        public CartItemAdapter(Context context, ArrayList<CartItem> cartItemList) {
            this.context = context;
            this.cartItemList = cartItemList;
        }

        @NonNull
        @Override
        public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(context).inflate(R.layout.cart_item_row,parent,false);
            return new CartItemHolder(row);
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
            holder.name.setText(cartItemList.get(position).getName());
            holder.price.setText(String.valueOf(cartItemList.get(position).getPrice()));
            holder.quantity.setText(String.valueOf(cartItemList.get(position).getquantity()));
            ArrayList<Media> image_url=cartItemList.get(position).getImage();
            Glide.with(context).load(image_url).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return cartItemList.size();
        }


    class CartItemHolder extends RecyclerView.ViewHolder{
        ImageView image ;
        TextView name , price , quantity ;
        Button increment , decrement ;

        public CartItemHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_tv);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            image=itemView.findViewById(R.id.product_image);
            increment=itemView.findViewById(R.id.Increment);
            decrement=itemView.findViewById(R.id.Decrement);

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Quantity=cartItemList.get(getAdapterPosition()).getquantity();
                    Quantity++;
                    Log.d("ADAPTERR","quantitiy "+Quantity);
                    cartItemList.get(getAdapterPosition()).setquantity(Quantity);
                    quantity.setText(String.valueOf(Quantity));
                }
            });
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Quantity=cartItemList.get(getAdapterPosition()).getquantity();
                    if (Quantity>0){
                        Quantity -= 1 ;
                        cartItemList.get(getAdapterPosition()).setquantity(Quantity);
                        quantity.setText(String.valueOf(Quantity));
                    }
                }
            });
        }
    }
}
