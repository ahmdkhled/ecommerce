package com.ahmdkhled.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.CartItemAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ArrayList<CartItem> cartItemArrayList;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemArrayList = new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);

        CartItem toy =new CartItem(1,"toy",200,10,"");
        cartItemArrayList.add(toy);
        CartItem flower =new CartItem(10,"flower",20,5,"");
        cartItemArrayList.add(flower);
        CartItem t_shirt =new CartItem(5000,"t_shirt",150,1,"");
        cartItemArrayList.add(t_shirt);

        CartItemAdapter cartItemAdapter = new CartItemAdapter(this,cartItemArrayList);
        recyclerView.setAdapter(cartItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}
