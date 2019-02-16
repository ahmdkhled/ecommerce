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
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder>{

        private Context context;
        private ArrayList<CartItem> cartItemList;
        CartItemsManger cartItemsManger;

        public CartItemAdapter(Context context, ArrayList<CartItem> cartItemList) {
            this.context = context;
            this.cartItemList = cartItemList;
            cartItemsManger=new CartItemsManger(context);
        }

        @NonNull
        @Override
        public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(context).inflate(R.layout.cart_item_row,parent,false);
            return new CartItemHolder(row);
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
            holder.name.setText(cartItemList.get(position).getProduct().getName());
            holder.price.setText(String.valueOf(cartItemList.get(position).getProduct().getPrice()));
            holder.quantity.setText(String.valueOf(cartItemList.get(position).getQuantity()));
            ArrayList<Media> imagesList=cartItemList.get(position).getProduct().getMedia();
            if (imagesList!=null&&imagesList.size()>0){
                Glide.with(context).load(imagesList.get(0).getUrl()).into(holder.image);
            }else{
                Log.d("CARTTT","no image");
            }

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
                    int Quantity=cartItemList.get(getAdapterPosition()).getQuantity();
                    Quantity++;
                    Log.d("ADAPTERR","quantitiy "+Quantity);
                    cartItemList.get(getAdapterPosition()).setQuantity(Quantity);
                    cartItemsManger.updateQuantity(Quantity,getAdapterPosition());
                    quantity.setText(String.valueOf(Quantity));
                }
            });
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Quantity=cartItemList.get(getAdapterPosition()).getQuantity();
                    if (Quantity>0){
                        Quantity -= 1 ;
                        cartItemsManger.updateQuantity(Quantity,getAdapterPosition());
                        cartItemList.get(getAdapterPosition()).setQuantity(Quantity);
                        quantity.setText(String.valueOf(Quantity));
                    }
                }
            });
        }
    }
}
