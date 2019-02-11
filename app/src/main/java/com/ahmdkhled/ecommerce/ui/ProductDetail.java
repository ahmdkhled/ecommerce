package com.ahmdkhled.ecommerce.ui;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.ProductsImagesPagerAdapter;
import com.ahmdkhled.ecommerce.R;

public class ProductDetail extends AppCompatActivity {
    TextView textView;
    Button addToCart;
    Toolbar toolbar;
    ViewPager viewPager;
    ProductsImagesPagerAdapter productsImagesPagerAdapter;
    SharedPreferences sharedPreferences;
    public static final String PRODUCT_KEY="product_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.productImagesPager);
        productsImagesPagerAdapter = new ProductsImagesPagerAdapter(this);
        viewPager.setAdapter(productsImagesPagerAdapter);
        textView = findViewById(R.id.product_name);
        addToCart = findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
            });
    }
}
