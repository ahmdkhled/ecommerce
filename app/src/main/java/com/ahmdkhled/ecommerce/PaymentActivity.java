package com.ahmdkhled.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.ui.LoginActivity;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    Button placeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        placeOrder=findViewById(R.id.placeOrder);

        ArrayList<CartItem> cartItems=getIntent().getParcelableArrayListExtra("oitems");
        final String ids=getIdsAsString(cartItems);
        final String qs=getQuantitiesAsString(cartItems);
        final long userId=new SessionManager(this).getId();
        final int addressId=91;
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId>-1){
                    makeOrder(ids,qs, (int) userId,addressId);

                }else {
                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    void makeOrder(String orderItems,String quantity,int userId,int addressId){
        RetrofetClient
                .getApiService()
                .makeOrder(orderItems,quantity,userId,addressId)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order=response.body();
                        if (order!=null){
                            Log.d("ORDEERR","order was placed successfully");
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.d("ORDEERR","erro "+t.getMessage());

                    }
                });
    }


    private String getIdsAsString(ArrayList<CartItem> cartItems){
        StringBuilder sb=new StringBuilder();
        for (int i=0 ;i<cartItems.size(); i++){
            if (i<cartItems.size()-1){
                sb.append(cartItems.get(i).getProduct().getId()).append(",");
            }else {
                sb.append(cartItems.get(i).getProduct().getId());
            }
        }
        return sb.toString();
    }

    private String getQuantitiesAsString(ArrayList<CartItem> cartItems){
        StringBuilder sb=new StringBuilder();
        for (int i=0 ;i<cartItems.size(); i++){
            if (i<cartItems.size()-1){
                sb.append(cartItems.get(i).getQuantity()).append(",");
            }else {
                sb.append(cartItems.get(i).getQuantity());
            }
        }
        return sb.toString();
    }
}
