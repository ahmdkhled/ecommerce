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
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.model.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentHolder> {

    CartResponse shipments ;
    Context mContext;

    public ShipmentAdapter(CartResponse shipments, Context mContext) {
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
        Product product = shipments.getProducts().get(position);
        holder.product_name.setText(product.getName());
        holder.product_price.setText(product.getPrice()+"");
        holder.quantity_value.setText(product.getQuantity()+"");
        holder.seller_name.setText(product.getSellerName());

        if(product.getMedia() != null && product.getMedia().size() > 0) {
            Glide.with(mContext)
                    .load(product.getMedia().get(0).getUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .fitCenter())
                    .into(holder.img_product);
        }
    }

    @Override
    public int getItemCount() {
        if(shipments != null && shipments.getProducts().size() > 0) return shipments.getProducts().size();
        else return 0;

    }

    public void notifyAdapter(CartResponse cartResponse) {
        if(cartResponse != null){
            shipments = cartResponse;
            notifyDataSetChanged();
        }
    }

    class ShipmentHolder extends RecyclerView.ViewHolder{
         TextView product_name,product_price,seller_name,quantity_value,expected_time;
         ImageView img_product;
        public ShipmentHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.name_tv);
            product_price = itemView.findViewById(R.id.product_price);
            seller_name = itemView.findViewById(R.id.tv_seller_name);
            quantity_value = itemView.findViewById(R.id.tv_q_value);
        }
    }
}
