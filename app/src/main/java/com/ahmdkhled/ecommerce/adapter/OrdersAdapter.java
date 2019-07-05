package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.ui.OrderDetailActivity;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Order;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderHolder> {

    private Context context;
    private ArrayList<Order> ordersList;

    public OrdersAdapter(Context context, ArrayList<Order> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.order_row,parent,false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order=ordersList.get(position);
        holder.orderNumber.setText(String.valueOf(order.getOrder_id()));
        holder.orderDate.setText(order.getOrder_date());
        holder.handleStatus(order.getStatus());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView orderNumber,orderDate;
        ImageView pending,processing,delivered;
        View line1,line2;
        TextView deliveredLabel;
        OrderHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber =itemView.findViewById(R.id.order_number);
             orderDate=itemView.findViewById(R.id.order_date);
             pending=itemView.findViewById(R.id.pending);
             processing=itemView.findViewById(R.id.processing);
             delivered=itemView.findViewById(R.id.delivered);
             line1=itemView.findViewById(R.id.line1);
             line2=itemView.findViewById(R.id.line2);
             deliveredLabel=itemView.findViewById(R.id.delivered_label);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent=new Intent(context, OrderDetailActivity.class);
                     intent.putExtra(OrderDetailActivity.ORDER_Key,ordersList.get(getAdapterPosition()));
                     context.startActivity(intent);
                 }
             });
        }

        void handleStatus(String status){
            if (status.equals("pending")){
                pending.setImageResource(R.drawable.circular_green_bg);
                processing.setImageResource(R.drawable.circular_grey_bg);
                delivered.setImageResource(R.drawable.circular_grey_bg);
                line1.setBackgroundColor(context.getResources().getColor(R.color.green));
                line2.setBackgroundColor(context.getResources().getColor(R.color.grey));
            }else if (status.equals("processing")){
                pending.setImageResource(R.drawable.circular_green_bg);
                processing.setImageResource(R.drawable.circular_green_bg);
                delivered.setImageResource(R.drawable.circular_grey_bg);
                line1.setBackgroundColor(context.getResources().getColor(R.color.green));
                line2.setBackgroundColor(context.getResources().getColor(R.color.grey));
            }else if (status.equals("delivered")){
                pending.setImageResource(R.drawable.circular_green_bg);
                processing.setImageResource(R.drawable.circular_green_bg);
                delivered.setImageResource(R.drawable.circular_green_bg);
                line1.setBackgroundColor(context.getResources().getColor(R.color.green));
                line2.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else if (status.equals("failed")){
                pending.setImageResource(R.drawable.circular_green_bg);
                processing.setImageResource(R.drawable.circular_green_bg);
                delivered.setImageResource(R.drawable.circular_red_bg);
                line1.setBackgroundColor(context.getResources().getColor(R.color.green));
                line2.setBackgroundColor(context.getResources().getColor(R.color.green));
                deliveredLabel.setText("failed");
            }
        }
    }
}
