package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.CartItemAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button checkoutButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkoutButton = findViewById(R.id.checkout);
        recyclerView = findViewById(R.id.recycler);



        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             Intent intent = new Intent(getApplicationContext(),CheckoutActivity.class);
//             startActivity(intent);
            }
        });



        CartItemsManger cartItemsManger=new CartItemsManger(this);
        ArrayList<CartItem> cartItems=cartItemsManger.getCartItems();
        if (cartItems != null) {
            getCartItems(cartItems);
            Log.d("JSONN","ok "+cartItems.get(0).getProduct().getId());
        }else {
            Log.d("JSONN","nulllllllllllllll");
        }
    }

    public void getCartItems(final ArrayList<CartItem> cartItems) {
        String ids = arrToString(cartItems);
        RetrofetClient.getApiService().getCartItems(ids)
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> products = response.body();
                        for (int i = 0; i < cartItems.size(); i++) {
                            cartItems.get(i).setProduct(products.get(i));
                        }
                        Log.d("CARTTT","0 "+cartItems.get(0).getProduct().getMedia().get(0).getUrl());
                        CartItemAdapter cartItemAdapter = new CartItemAdapter(getApplicationContext(), cartItems);
                        recyclerView.setAdapter(cartItemAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Log.d("CARTTT",t.getMessage());
                    }
                });
    }

    private String arrToString(ArrayList<CartItem> cartItems){
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

    ArrayList<CartItem> fakeData(){
        ArrayList<CartItem> cartItemArrayList=new ArrayList<>();
//        CartItem toy =new CartItem(4,"toy",200,10,"");
//        cartItemArrayList.add(toy);
//        CartItem flower =new CartItem(5,"flower",20,5,"");
//        cartItemArrayList.add(flower);
//        CartItem t_shirt =new CartItem(5000,"t_shirt",150,1,"");
//        cartItemArrayList.add(t_shirt);
        return cartItemArrayList;
    }
}
