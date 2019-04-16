package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.model.OrderItem;
import com.ahmdkhled.ecommerce.network.Network;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.OrdersViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity {

    @BindView(R.id.place_order_layout)
    ConstraintLayout layout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.order_num_value)
    TextView mOrderNumberTxt;
    ConstraintLayout constraintLayout;



    private int addressId;
    private int shippingMethod;
    private int paymentMethod;
    private OrdersViewModel mViewModel;
    private long userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Intent intent= getIntent();
        if(intent != null){
            addressId = intent.getIntExtra("shipping_address_id",0);
            shippingMethod = intent.getIntExtra("shipping_method",0);
            paymentMethod = intent.getIntExtra("payment_method",0);
        }

        // get user id
        userId = new SessionManager(this).getId();
        List<CartItem> cartItems =  CartItemsManger.getInstance(this).getCartItems();


        mViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        mViewModel.init();

        constraintLayout = findViewById(R.id.order_summary_activity);
        if (!Network.isConnected(this)){
            showSnakbar();
            return;
        }

        mViewModel.placeOrder(userId,addressId,cartItems);

        // observe result
        mViewModel.getPlaceOrderResponse().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(@Nullable Order order) {
                if(order != null) {
                    Log.d("order_summary","address id "+order.getAddressId());
                    Log.d("order_summary","date "+order.getOrder_date());
                    Log.d("order_summary","status "+order.getStatus());
                    Log.d("order_summary","user id "+order.getUserId());
                    for(OrderItem item : order.getOrderItems()){
                        Log.d("order_summary","order item  "+item.getId() +" quantity "+item.getOrderItem_quantity());
                    }
                    mOrderNumberTxt.setText(order.getOrder_id()+"");
                }
            }
        });

        mViewModel.getIsOrderPlaceing().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) {
                    hideProgressBar();
                }
                else showProgressBar();
            }
        });





    }
    private void showSnakbar() {
        Snackbar snackbar = Snackbar.make(constraintLayout, "there is no connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }



    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        layout.setVisibility(View.INVISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }


}
