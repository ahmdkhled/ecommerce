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
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Media;
import com.ahmdkhled.ecommerce.model.Shipment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentHolder> {

    List<CartItem> shipments ;
    Context mContext;

    public ShipmentAdapter(List<CartItem> shipments, Context mContext) {
        this.shipments = shipments;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ShipmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shipment_row,parent,false);
        return new ShipmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentHolder holder, int position) {
        CartItem shipment=shipments.get(position);
        holder.product_name.setText(shipment.getProduct().getName());
        holder.product_price.setText(shipment.getProduct().getPrice()+"");
        holder.quantity_value.setText(shipment.getQuantity()+"");
        holder.seller_name.setText(shipment.getProduct().getSellerName());

        Glide.with(mContext)
                .load(shipment.getProduct().getMedia().get(0).getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        if(shipments != null && shipments.size() > 0)return shipments.size();
        else return 0;

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
        }
    }
}
