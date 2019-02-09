package com.ahmdkhled.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.FavoriteProductsAdapter;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DividerItemDecoration dividerItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView=findViewById(R.id.favorites_recyclerView);
        dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        getFavoriteProducts();
    }

    private void getFavoriteProducts(){
        long userId=new SessionManager(this).getId();
        if (userId>-1) {
            RetrofetClient.getApiService()
                    .getFavoriteProducts(userId)
                    .enqueue(new Callback<ArrayList<Product>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                            ArrayList<Product> products = response.body();
                            showProducts(products);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                            Log.d("FAVORITEE", "error " + t.getMessage());
                        }
                    });
        }
    }

    private void showProducts(ArrayList<Product> products) {
        FavoriteProductsAdapter favoriteProductsAdapter=new FavoriteProductsAdapter(this,products);
        recyclerView.setAdapter(favoriteProductsAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.removeItemDecoration(dividerItemDecoration);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
