package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.OrdersAdapter;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.OrdersViewModel;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView ordersRecycler;
    OrdersViewModel ordersViewModel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ordersRecycler=findViewById(R.id.orders_recycler);
        progressBar=findViewById(R.id.orders_PB);

        ordersViewModel= ViewModelProviders.of(this).get(OrdersViewModel.class);
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
        observeLoading();
    }

    void showOrders(ArrayList<Order> orders){
        OrdersAdapter ordersAdapter=new OrdersAdapter(getApplicationContext(),orders);
        ordersRecycler.setAdapter(ordersAdapter);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    void observeLoading(){
        ordersViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean!=null&&aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
