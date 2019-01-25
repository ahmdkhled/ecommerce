package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ProductAdapter;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity  extends AppCompatActivity {

    public static final String CATEGORY_ID_KEY="category_id";
    ArrayList<Product> productsList=new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView=findViewById(R.id.recycler_view);
        productsList=new ArrayList<>();

        Intent intent = getIntent();

        int id = intent.getIntExtra(CATEGORY_ID_KEY,-1);
        Log.d("checkout","id "+id);



        getProducts(id);


    }


    public void getProducts(int id){
        RetrofetClient.getApiService().getProducts(String.valueOf(id))
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> productsList= response.body();
                        ProductAdapter productAdapter=new ProductAdapter(productsList,getApplicationContext());
                        recyclerView.setAdapter(productAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                        Log.d("categoryyy","name "+productsList.get(0).getName());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Log.d("categoryyy","amira"+t.getMessage());

                    }
                });
    }


    public void getFakeData (){
        Product p=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
        Product p1=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
        Product p2=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
        Product p3=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
        Product p4=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
        Product p5=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
        Product p6=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);


        productsList.add(p);
        productsList.add(p1);
        productsList.add(p2);
        productsList.add(p3);
        productsList.add(p4);
        productsList.add(p5);
        productsList.add(p6);

    }


}
