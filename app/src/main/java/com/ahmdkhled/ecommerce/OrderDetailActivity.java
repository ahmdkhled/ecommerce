package com.ahmdkhled.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.model.Order;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String ORDER_Key="order_key";
    TextView orderNum,orderDate;
    TextView orderTotal,orderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderNum=findViewById(R.id.order_detail_Num);
        orderDate=findViewById(R.id.order_detail_date);
        orderTotal=findViewById(R.id.order_detail_total);
        orderStatus=findViewById(R.id.order_detail_status);

        Order order=getIntent().getParcelableExtra(ORDER_Key);
        populateOrderDetails(order);
        Log.d("ORDERRRR","order num "+order.getOrder_id());

    }

    private void populateOrderDetails(Order order){
        orderNum.setText(String.valueOf(order.getOrder_id()));
        orderDate.setText(order.getOrder_date());
        orderStatus.setText(order.getStatus());

    }
}
