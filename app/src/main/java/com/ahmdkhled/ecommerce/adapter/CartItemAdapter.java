package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.ui.ProductDetail;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder>{

        private Context context;
        private ArrayList<CartItem> cartItemList;
        private CartItemsManger cartItemsManger;
        private OnCartItemsChange onCartItemsChange;

    public CartItemAdapter(Context context, ArrayList<CartItem> cartItemList, OnCartItemsChange onCartItemsChange) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.onCartItemsChange = onCartItemsChange;
        cartItemsManger=CartItemsManger.getInstance(context);
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
            //Log.d("CARTTT","no image");
        }

    }

    @Override
    public int getItemCount() {
    if (cartItemList==null)
        return 0;
    return cartItemList.size();
    }

    public ArrayList<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void addItems(ArrayList<CartItem> newCartItems){
        if (cartItemList==null)
            cartItemList=new ArrayList<>();
        this.cartItemList.addAll(newCartItems);
        notifyDataSetChanged();
    }

    private int getTotal (){
            int total=0;
            for(int i=0;i<cartItemList.size();i++){
                total+=cartItemList.get(i).getQuantity()*cartItemList.get(i).getProduct().getPrice();
            }
            return total;
        }

    class CartItemHolder extends RecyclerView.ViewHolder{
        ImageView image ,delete;
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
            delete=itemView.findViewById(R.id.deleteCartItem);

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Quantity=cartItemList.get(getAdapterPosition()).getQuantity();
                    Quantity++;
                    Log.d("ADAPTERR","quantitiy "+Quantity);
                    cartItemList.get(getAdapterPosition()).setQuantity(Quantity);
                    cartItemsManger.updateQuantity(Quantity,getAdapterPosition());
                    quantity.setText(String.valueOf(Quantity));
                    onCartItemsChange.onQuantityIncreased(getTotal());
                }
            });
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Quantity=cartItemList.get(getAdapterPosition()).getQuantity();
                    if (Quantity>1){
                        Quantity -= 1 ;
                        cartItemsManger.updateQuantity(Quantity,getAdapterPosition());
                        cartItemList.get(getAdapterPosition()).setQuantity(Quantity);
                        quantity.setText(String.valueOf(Quantity));
                        onCartItemsChange.onQuantityDecreased(getTotal());
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartItemsManger.deleteCartItem(getAdapterPosition());
                    cartItemList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    onCartItemsChange.onCartItemDeleted(getTotal());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ProductDetail.class);
                    Product product=cartItemList.get(getAdapterPosition()).getProduct();
                    intent.putExtra(ProductDetail.PRODUCT_KEY,product);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    public interface OnCartItemsChange{
            void onQuantityIncreased(int total);
            void onQuantityDecreased(int total);
            void onCartItemDeleted(int total);
    }
}
