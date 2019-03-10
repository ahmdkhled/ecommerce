package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ShipmentAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Shipment;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
     TextView shipping , subtotal , total , shipping_value , subtotal_value, total_value;
     Button next_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        TextView changeAddress=findViewById(R.id.tv_change_address);
        ArrayList<CartItem> cartItems= getIntent().getParcelableArrayListExtra("items");
        ArrayList<Shipment> data = new ArrayList();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            data.add(new Shipment(cartItem,"2-Days"));
        }
        RecyclerView recyclerView =  findViewById(R.id.rv_shipment);
        ShipmentAdapter adapter = new ShipmentAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChangeAddressActivity.class);
                startActivity(intent);
            }
        });

        shipping = findViewById(R.id.shipping);
        subtotal = findViewById(R.id.subtotal);
        total = findViewById(R.id.total);
        shipping_value = findViewById(R.id.shipping_price);
        subtotal_value = findViewById(R.id.subtotal_price);
        total_value = findViewById(R.id.total_price);
        next_button = findViewById(R.id.continue_button);

    }

    private void updateTotal(ArrayList<CartItem> cartItems){
        int total=0;
        for(int i=0;i<cartItems.size();i++){
            total+=cartItems.get(i).getQuantity()*cartItems.get(i).getProduct().getPrice();
        }
        subtotal_value.setText(String.valueOf(total));
    }
}
