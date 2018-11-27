package com.ahmdkhled.ecommerce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
            holder.quantity.setText(String.valueOf(cartItemList.get(position).getQuantity()));
        }


        @Override
        public int getItemCount() {
            return cartItemList.size();
        }


    class CartItemHolder extends RecyclerView.ViewHolder{

        ImageView image ;
        TextView name , price , quantity ;

        public CartItemHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_tv);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            image=itemView.findViewById(R.id.product_image);

        }
    }
}
