package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.annotation.Nullable;
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
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.Network;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;

import com.ahmdkhled.ecommerce.utils.SnackBarUtil;
import com.ahmdkhled.ecommerce.viewmodel.CartItemsViewModel;

import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.ArrayList;

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
    ConstraintLayout constraintLayout;
    CartItemAdapter.OnCartItemsChange onCartItemsChange;
    LinearLayoutManager linearLayoutManager;
    int total=0;
    CartItemsViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkoutButton = findViewById(R.id.checkout);
        recyclerView = findViewById(R.id.recycler);
        cart_subtotal = findViewById(R.id.cart_subtotal);
        cartSubtotal_label = findViewById(R.id.cart_subtotal_label);
        emptyCartContainer = findViewById(R.id.emptyCartContainer);
        constraintLayout = findViewById(R.id.activity_cart);
        LE = findViewById(R.id.L_E);
        cartProgressBar = findViewById(R.id.cart_progress_bar);

        onCartItemsChange=this;
        vm= ViewModelProviders.of(this).get(CartItemsViewModel.class);
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
            if(Network.isConnected(this)) {
                String ids = getIdsAsString(cartItems);
                String q=getQuantitiesAsString(cartItems);
                vm.getCartItems(ids,q,"1");
                vm.getCartItems().removeObservers(this);
                observeCartItems(cartItems,"1");
            }else {
                SnackBarUtil.showSnackBar(constraintLayout);
            }

            //getCartItems(cartItems,"1");
        }

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Network.isConnected(getApplicationContext())) {
                    //Log.d("CARRTTT","onloadmore ");
                        page++;
                        //getCartItems(cartItems, String.valueOf(page));

                }else{
                    SnackBarUtil.showSnackBar(constraintLayout);
                }
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



    private void showCartItems(ArrayList<CartItem> cartItems) {
        cartItemAdapter = new CartItemAdapter(getApplicationContext(),cartItems,
                onCartItemsChange);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(cartItemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void handleCartItems(CartResponse cartResponse,ArrayList<CartItem> cartItems,String page){
        ArrayList<Product> products = cartResponse.getProducts();
        ArrayList<CartItem> newCartItems=new ArrayList<>();
        Log.d("CARTTT","price "+cartResponse.getTotal());
        for (int i = 0; i < products.size(); i++) {
            int j=(Integer.valueOf(page)-1) *10 +i;
            //newCartItems.get(j).setProduct(products.get(i));
            newCartItems.add(new CartItem(products.get(i),cartItems.get(j).getQuantity()));
        }
        //Log.d("CARRTTT",newCartItems.size()+"");

        cartItemAdapter.addItems(newCartItems);
        if (!products.isEmpty())
            cart_subtotal.setText(String.valueOf(cartResponse.getTotal()));
        cartProgressBar.setVisibility(View.GONE);
        checkoutButton.setEnabled(true);
    }

    private void observeCartItems(final ArrayList<CartItem> cartItems, final String page){
        if (vm.getCartItems().hasActiveObservers()){
            Log.d("CARRTTT","hasActiveObservers  ");
            return;
        }

        vm.getCartItems().observe(this, new Observer<CartResponse>() {
            @Override
            public void onChanged(@Nullable CartResponse cartResponse) {
                Log.d("CARRTTT","onChanged  ");
                handleCartItems(cartResponse,cartItems,page);

            }
        });
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

    private void showSnakbar(){
        Snackbar snackbar = Snackbar.make(constraintLayout,"there is no connection",Snackbar.LENGTH_LONG);
        snackbar.show();
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
