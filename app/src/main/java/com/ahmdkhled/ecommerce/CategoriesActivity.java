package com.ahmdkhled.ecommerce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Category;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity  extends AppCompatActivity {

    ArrayList<Category> categoriesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        recyclerView = findViewById(R.id.recycler_view);
        getCategories();

    }

    private void setAdapter() {
        CategoryAdapter adapter = new CategoryAdapter(categoriesArrayList, this);
        RecyclerView.LayoutManager layoutManage = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(adapter);
    }

    private void getCategories() {
        Call<ArrayList<Category>> call = RetrofetClient.getApiService().getCategories();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                Log.d("catTTTTT","on respone "+response.isSuccessful());
                if (response.isSuccessful()) {
                    categoriesArrayList = response.body();
                    setAdapter();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.d("catTTTTT","error is "+t.getMessage());
            }
        });
    }
}
