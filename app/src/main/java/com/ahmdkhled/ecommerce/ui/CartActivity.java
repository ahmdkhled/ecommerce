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
import android.widget.Toast;

import com.ahmdkhled.ecommerce.EndlessRecyclerViewScrollListener;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.CartItemAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.OnCartItemsChange{
    private static final int LOGIN_REQUEST_CODE = 1009;
    RecyclerView recyclerView;
    Button checkoutButton ;
    TextView cart_subtotal;
    TextView cartSubtotal_label;
    ViewGroup emptyCartContainer;
    TextView LE;
    CartItemAdapter cartItemAdapter;
    ProgressBar cartProgressBar ;
    CartItemAdapter.OnCartItemsChange onCartItemsChange;
    LinearLayoutManager linearLayoutManager;
    int total=0;

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

        CartItemsManger cartItemsManger=CartItemsManger.getInstance(this);
        final ArrayList<CartItem> cartItems=cartItemsManger.getCartItems();
        handleVisibility(cartItems);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             if(new SessionManager(CartActivity.this).getId() != -1) {
                 Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                 intent.putParcelableArrayListExtra("items", cartItems);
                 intent.putExtra("total", total);
                 startActivity(intent);
             }else {
                 Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                 intent.putExtra("source", "cartItemsActivity");
                 startActivityForResult(intent,LOGIN_REQUEST_CODE);
             }
            }
        });


        if (cartItems != null&&!cartItems.isEmpty()) {
            cartProgressBar.setVisibility(View.VISIBLE);
            showCartItems(null);
            getCartItems(cartItems,"1");
        }

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                getCartItems(cartItems, String.valueOf(page));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == LOGIN_REQUEST_CODE && data != null){
            Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
            intent.putExtra("total", total);
            startActivity(intent);
        }
    }

    public void getCartItems(final ArrayList<CartItem> cartItems, final String page) {
        String ids = getIdsAsString(cartItems);
        String q=getQuantitiesAsString(cartItems);
        RetrofetClient.getApiService().getCartItems(ids,q,page)
                .enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse (Call<CartResponse> call, Response<CartResponse> response) {
                        CartResponse cartResponse=response.body();
                        ArrayList<Product> products = cartResponse.getProducts();
                        ArrayList<CartItem> newCartItems=new ArrayList<>();
                        Log.d("CARTTT","price "+cartResponse.getTotal());
                        for (int i = 0; i < products.size(); i++) {
                            int j=(Integer.valueOf(page)-1) *10 +i;
                            //newCartItems.get(j).setProduct(products.get(i));
                            newCartItems.add(new CartItem(products.get(i),cartItems.get(j).getQuantity()));
                        }
                        Log.d("CARTTT",page);

                        cartItemAdapter.addItems(newCartItems);
                        if (!products.isEmpty())
                            cart_subtotal.setText(String.valueOf(cartResponse.getTotal()));
                        cartProgressBar.setVisibility(View.GONE);
                        checkoutButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {
                        cartProgressBar.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                        Log.d("CARTTT",t.getMessage());
                    }
                });
    }


    private void showCartItems(ArrayList<CartItem> cartItems) {
        cartItemAdapter = new CartItemAdapter(getApplicationContext(),cartItems,
                onCartItemsChange);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(cartItemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
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

    

    private void handleVisibility(ArrayList<CartItem> cartItems){
     if (cartItems==null||cartItems.isEmpty()){
         //Log.d("CARTTTT","empty");
         emptyCartContainer.setVisibility(View.VISIBLE);
         recyclerView.setVisibility(View.GONE);
         checkoutButton.setVisibility(View.GONE);
         cart_subtotal.setVisibility(View.GONE);
         cartSubtotal_label.setVisibility(View.GONE);
         LE.setVisibility(View.GONE);
     }  else{
         //Log.d("CARTTTT","not empty");
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
        this.total=total;
        cart_subtotal.setText(String.valueOf(total));
    }

    @Override
    public void onQuantityDecreased(int total) {
        this.total=total;
        cart_subtotal.setText(String.valueOf(total));
    }

    @Override
    public void onCartItemDeleted(int total) {
        this.total=total;
        cart_subtotal.setText(String.valueOf(total));
        handleVisibility(cartItemAdapter.getCartItemList());
    }
}
