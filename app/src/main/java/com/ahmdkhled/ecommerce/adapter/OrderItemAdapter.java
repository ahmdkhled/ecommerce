package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemHOlder> {

    Context context;
    private ArrayList<OrderItem> orderitemsList;

    public OrderItemAdapter(Context context, ArrayList<OrderItem> orderitemsList) {
        this.context = context;
        this.orderitemsList = orderitemsList;
    }

    @NonNull
    @Override
    public OrderItemHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.order_item_row,parent,false);
        return new OrderItemHOlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHOlder holder, int position) {
        OrderItem orderItem=orderitemsList.get(position);
        holder.name.setText(orderItem.getName());
        holder.price.setText(String.valueOf(orderItem.getPrice()));
        holder.quantity.setText(String.valueOf(orderItem.getOrderItem_quantity()));
    }

    @Override
    public int getItemCount() {
        return orderitemsList.size();
    }

    class OrderItemHOlder extends RecyclerView.ViewHolder{
        TextView name,quantity,price;
        public OrderItemHOlder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.orderItem_name);
            quantity=itemView.findViewById(R.id.orderItem_quantity);
            price=itemView.findViewById(R.id.orderItem_price);
        }
    }
}
