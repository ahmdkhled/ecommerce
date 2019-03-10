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
import com.ahmdkhled.ecommerce.model.Media;
import com.ahmdkhled.ecommerce.model.Shipment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentHolder> {

    ArrayList<Shipment> data ;
    Context context;

    public ShipmentAdapter(ArrayList data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ShipmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shipment_row,parent,false);
        return new ShipmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentHolder holder, int position) {
        Shipment shipment=data.get(position);
        holder.product_name.setText(shipment.getCartItem().getProduct().getName());
        holder.product_price.setText(String.valueOf(shipment.getCartItem().getProduct().getPrice()));
        holder.quantity_value.setText(String.valueOf(shipment.getCartItem().getQuantity()));
        holder.seller_name.setText(shipment.getCartItem().getProduct().getSellerName());
        holder.expected_time.setText(shipment.getExpectedDelivery());
        ArrayList<Media> media=shipment.getCartItem().getProduct().getMedia();
        if (media!=null&&!media.isEmpty()){
            Glide.with(context).load(media.get(0).getUrl()).into(holder.img_product);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ShipmentHolder extends RecyclerView.ViewHolder{
         TextView product_name,product_price,seller_name,quantity_value,expected_time;
         ImageView img_product;
        public ShipmentHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.name_tv);
            product_price = itemView.findViewById(R.id.price);
            seller_name = itemView.findViewById(R.id.tv_seller_name);
            quantity_value = itemView.findViewById(R.id.tv_q_value);
            expected_time = itemView.findViewById(R.id.expected_delivery_value);
        }
    }
}
