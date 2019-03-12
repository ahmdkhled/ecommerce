package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.OrdersAdapter;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.OrdersViewModel;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView ordersRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ordersRecycler=findViewById(R.id.orders_recycler);

        OrdersViewModel ordersViewModel= ViewModelProviders.of(this).get(OrdersViewModel.class);
        SessionManager sessionManager=new SessionManager(this);
        if (sessionManager.sessionExist()){
            ordersViewModel.getOrders(String.valueOf(sessionManager.getId()))
                    .observe(this, new Observer<ArrayList<Order>>() {
                        @Override
                        public void onChanged(@Nullable ArrayList<Order> orders) {
                            showOrders(orders);
                        }
                    });
        }else{
            Toast.makeText(this, "please login first to view your orders", Toast.LENGTH_SHORT).show();
        }
    }

    void showOrders(ArrayList<Order> orders){
        OrdersAdapter ordersAdapter=new OrdersAdapter(getApplicationContext(),orders);
        ordersRecycler.setAdapter(ordersAdapter);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
