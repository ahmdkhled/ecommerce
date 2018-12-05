package com.ahmdkhled.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.CartItemAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    ArrayList<CartItem> cartItemArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        fakeData();
        String ids=arrToString(cartItemArrayList);
                                                                                                    Log.d("CARTTT",ids);
        getCartItems(ids);

    }

    public void getCartItems(String ids) {
        RetrofetClient.getApiService().getCartItems(ids)
                .enqueue(new Callback<ArrayList<CartItem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CartItem>> call, Response<ArrayList<CartItem>> response) {
                        ArrayList<CartItem> cartItems = response.body();
                        CartItemAdapter cartItemAdapter = new CartItemAdapter(getApplicationContext(), cartItems);
                        recyclerView.setAdapter(cartItemAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CartItem>> call, Throwable t) {
                        Log.d("CARTTT",t.getMessage());
                    }
                });
    }

    private String arrToString(ArrayList<CartItem> cartItems){
        StringBuilder sb=new StringBuilder();
        for (int i=0 ;i<cartItems.size(); i++){
            if (i<cartItems.size()-1){
                sb.append(cartItems.get(i).getId()).append(",");
            }else {
                sb.append(cartItems.get(i).getId());
            }
        }
        return sb.toString();
    }

    void fakeData(){
        CartItem toy =new CartItem(4,"toy",200,10,"");
        cartItemArrayList.add(toy);
        CartItem flower =new CartItem(5,"flower",20,5,"");
        cartItemArrayList.add(flower);
//        CartItem t_shirt =new CartItem(5000,"t_shirt",150,1,"");
//        cartItemArrayList.add(t_shirt);
    }
}
