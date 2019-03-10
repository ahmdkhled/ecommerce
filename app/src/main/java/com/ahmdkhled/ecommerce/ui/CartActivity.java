package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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

public class CartActivity extends AppCompatActivity implements CartItemAdapter.OnCartItemsChange{
    RecyclerView recyclerView;
    Button checkoutButton ;
    TextView cart_subtotal;
    TextView cartSubtotal_label;
    ViewGroup emptyCartContainer;
    TextView LE;
    CartItemAdapter cartItemAdapter;
    ProgressBar cartProgressBar ;
    CartItemAdapter.OnCartItemsChange onCartItemsChange;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkoutButton = findViewById(R.id.checkout);
        recyclerView = findViewById(R.id.recycler);
        cart_subtotal = findViewById(R.id.cart_subtotal);
        cartSubtotal_label = findViewById(R.id.cart_subtotal_label);
        emptyCartContainer = findViewById(R.id.emptyCartContainer);
        LE = findViewById(R.id.L_E);
        cartProgressBar = findViewById(R.id.cart_progress_bar);

        onCartItemsChange=this;

        CartItemsManger cartItemsManger=new CartItemsManger(this);
        final ArrayList<CartItem> cartItems=cartItemsManger.getCartItems();
        handleVisibility(cartItems);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(getApplicationContext(),CheckoutActivity.class);
             intent.putParcelableArrayListExtra("items", cartItems);
             startActivity(intent);
            }
        });


        if (cartItems != null&&!cartItems.isEmpty()) {
            cartProgressBar.setVisibility(View.VISIBLE);
            getCartItems(cartItems);
            Log.d("CARTTT","getting cartItems ");
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
                         cartItemAdapter = new CartItemAdapter(getApplicationContext(),cartItems,
                                onCartItemsChange);
                        recyclerView.setAdapter(cartItemAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        cartProgressBar.setVisibility(View.GONE);
                        updateTotal(cartItems);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        cartProgressBar.setVisibility(View.GONE);
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

    private void updateTotal(ArrayList<CartItem> cartItems){
        int total=0;
        for(int i=0;i<cartItems.size();i++){
            total+=cartItems.get(i).getQuantity()*cartItems.get(i).getProduct().getPrice();
        }
        cart_subtotal.setText(String.valueOf(total));
    }

    private void handleVisibility(ArrayList<CartItem> cartItems){
     if (cartItems==null||cartItems.isEmpty()){
         Log.d("CARTTTT","empty");
         emptyCartContainer.setVisibility(View.VISIBLE);
         recyclerView.setVisibility(View.GONE);
         checkoutButton.setVisibility(View.GONE);
         cart_subtotal.setVisibility(View.GONE);
         cartSubtotal_label.setVisibility(View.GONE);
         LE.setVisibility(View.GONE);
     }  else{
         Log.d("CARTTTT","not empty");
         emptyCartContainer.setVisibility(View.GONE);
         recyclerView.setVisibility(View.VISIBLE);
         checkoutButton.setVisibility(View.VISIBLE);
         cart_subtotal.setVisibility(View.VISIBLE);
         cartSubtotal_label.setVisibility(View.VISIBLE);
         LE.setVisibility(View.VISIBLE);
     }
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

    @Override
    public void onQuantityIncreased(int total) {
        cart_subtotal.setText(String.valueOf(total));
    }

    @Override
    public void onQuantityDecreased(int total) {
        cart_subtotal.setText(String.valueOf(total));
    }

    @Override
    public void onCartItemDeleted(int total) {
        cart_subtotal.setText(String.valueOf(total));
        handleVisibility(cartItemAdapter.getCartItemList());
    }
}
