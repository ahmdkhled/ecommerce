package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Checkout;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Checkout checkout = getIntent().getParcelableExtra("add_key");
        if (checkout != null) {
            Log.d("CHECKOUT", checkout.getmAddress().get(0).getCity());
        }
    }
}