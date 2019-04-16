package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.FavoriteProductsAdapter;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.Network;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.FavProductsViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView=findViewById(R.id.favorites_recyclerView);
        progressBar=findViewById(R.id.fav_ProgressBar);

        FavProductsViewModel favProductsViewModel= ViewModelProviders.of(this).get(FavProductsViewModel.class);
        constraintLayout = findViewById(R.id.favorites_activity);
        if (!Network.isConnected(this)){

            showSnakbar();
            return;
        }

        favProductsViewModel.getFavProducts(this)
                .observe(this, new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Product> products) {
                showProducts(products);
            }
        });

        favProductsViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading!=null&&isLoading)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void showSnakbar() {
        Snackbar snackbar = Snackbar.make(constraintLayout, "there is no connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }



    private void showProducts(ArrayList<Product> products) {
        FavoriteProductsAdapter favoriteProductsAdapter=new FavoriteProductsAdapter(this,products);
        recyclerView.setAdapter(favoriteProductsAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }
}
