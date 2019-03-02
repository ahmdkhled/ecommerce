package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
            final TextView paymentmethod = (TextView) findViewById(R.id.payment_method);
            final RadioGroup group = (RadioGroup) findViewById(R.id.cash_on);
            final TextView shipping = (TextView) findViewById(R.id.shipping);
            final TextView subtotal = (TextView) findViewById(R.id.subtotal);
            final TextView total = (TextView) findViewById(R.id.total);
            final TextView shippingPrice = (TextView) findViewById(R.id.shipping_price);
            final TextView subtotalPrice = (TextView) findViewById(R.id.subtotal_price);
            final TextView totalPrice = (TextView) findViewById(R.id.total_price);
            final Button placeOrder = (Button)findViewById(R.id.place_order);

            placeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });

        }
    }
}