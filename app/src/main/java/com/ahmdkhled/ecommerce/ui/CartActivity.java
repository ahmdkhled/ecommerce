package com.ahmdkhled.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ahmdkhled.ecommerce.R;

public class CartActivity extends AppCompatActivity {
     int quantity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
}
