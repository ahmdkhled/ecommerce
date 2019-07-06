package com.ahmdkhled.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.OrderItemAdapter;
import com.ahmdkhled.ecommerce.model.Order;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String ORDER_Key="order_key";
    TextView orderNum,orderDate;
    TextView orderTotal,orderStatus;
    TextView address1,address2,city,
            government,mobileNum;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderNum=findViewById(R.id.order_detail_Num);
        orderDate=findViewById(R.id.order_detail_date);
        orderTotal=findViewById(R.id.order_detail_total);
        orderStatus=findViewById(R.id.order_detail_status);
        recyclerView=findViewById(R.id.orderItemsRecycler);
        address1=findViewById(R.id.order_address1);
        address2=findViewById(R.id.order_address2);
        city=findViewById(R.id.order_city);
        government=findViewById(R.id.order_government);
        mobileNum=findViewById(R.id.order_mobile);

        Order order=getIntent().getParcelableExtra(ORDER_Key);
        populateOrderDetails(order);
        populateAddress(order);
        Log.d("ORDERRRR","order num "+order.getOrder_id());

    }

    private void populateOrderDetails(Order order){
        orderNum.setText(String.valueOf(order.getOrder_id()));
        orderDate.setText(order.getOrder_date());
        orderStatus.setText(order.getStatus());
        OrderItemAdapter orderItemAdapter=new OrderItemAdapter(this,order.getOrderItems());
        recyclerView.setAdapter(orderItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void populateAddress(Order order) {
        if (order.getAddress()==null){
            Toast.makeText(this, "no adddress", Toast.LENGTH_SHORT).show();
            return;
        }
        address1.setText(order.getAddress().getAddress_1());
        address2.setText(order.getAddress().getAddress_2());
        city.setText(order.getAddress().getCity());
        government.setText(order.getAddress().getState());
        mobileNum.setText(order.getAddress().getPhone_number());
    }



    }
