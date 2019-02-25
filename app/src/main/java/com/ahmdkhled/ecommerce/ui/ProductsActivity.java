package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ahmdkhled.ecommerce.FilterDialogFragment;
import android.widget.ProgressBar;
import com.ahmdkhled.ecommerce.EndlessRecyclerViewScrollListener;
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
    public static final String TARGET_KEY="target_key";
    FloatingActionButton floatingActionButton;
    Spinner spinner;
    ArrayList<Product> productsList;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager gridLayoutManager;
    ProgressBar loadMorePB;
    int categoryId =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        spinner=findViewById(R.id.sortBySpinner);
        floatingActionButton=findViewById(R.id.fab);
        recyclerView=findViewById(R.id.products_recyclerView);
        loadMorePB=findViewById(R.id.fav_loadMore_PB);
        productsList=new ArrayList<>();

        Intent intent = getIntent();
        categoryId = intent.getIntExtra(CATEGORY_ID_KEY,-1);
        Log.d("checkout","id "+ categoryId);

        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
                gridLayoutManager =new GridLayoutManager(this,2);
            else
            gridLayoutManager =new GridLayoutManager(this,4);


            if (categoryId >-1)
        getProducts(String.valueOf(categoryId),1);

        productAdapter=new ProductAdapter(productsList,getApplicationContext());
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                loadMorePB.setVisibility(View.VISIBLE);
                Log.d("ENDLESSSCROLL","page "+page);
                if (categoryId >-1)
                getProducts(String.valueOf(categoryId),page);

            }
        });


        setUpSpinner();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogFragment filterDialogFragment=new FilterDialogFragment();
                filterDialogFragment.show(getSupportFragmentManager(),"tag");
            }
        });




    }


    public void getProducts(String categoryid,int page){
        RetrofetClient.getApiService().getProducts(categoryid,page)
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> productsList= response.body();
                        if (productsList!=null&&productsList.size()>0)
                        productAdapter.addItems(productsList);
                        loadMorePB.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        loadMorePB.setVisibility(View.GONE);
                        Log.d("categoryyy","amira"+t.getMessage());

                    }
                });
    }

    private void setUpSpinner(){
        ArrayList<String> sortList=new ArrayList<>();
        sortList.add("date");
        sortList.add("price(Low)");
        sortList.add("price(High)");
        sortList.add("Rating");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sortList);
        spinner.setAdapter(adapter);
    }

    public void getFakeData (){
//        Product p=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
//        Product p1=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
//        Product p2=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
//        Product p3=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
//        Product p4=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
//        Product p5=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);
//        Product p6=new Product(25,20,25,255,115,"fgg","12/5","this is ..",null);


//        productsList.add(p);
//        productsList.add(p1);
//        productsList.add(p2);
//        productsList.add(p3);
//        productsList.add(p4);
//        productsList.add(p5);
//        productsList.add(p6);

    }


}
