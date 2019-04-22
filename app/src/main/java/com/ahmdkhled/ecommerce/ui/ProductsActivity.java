package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ahmdkhled.ecommerce.FilterDialogFragment;
import android.widget.ProgressBar;
import com.ahmdkhled.ecommerce.EndlessRecyclerViewScrollListener;
import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ProductAdapter;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.Network;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SnackBarUtil;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity  extends AppCompatActivity {

    public static final String CATEGORY_ID_KEY="category_id";
    public static final String TARGET_KEY="target_key";
    public static final String RA_TARGET="recently_addded_target";
    String target="";
    FloatingActionButton floatingActionButton;
    Spinner spinner;
    ArrayList<Product> productsList;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager gridLayoutManager;
    ProgressBar loadMorePB;
    ProgressBar loadProductsPB;
    int categoryId =-1;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        spinner=findViewById(R.id.sortBySpinner);
        floatingActionButton=findViewById(R.id.fab);
        recyclerView=findViewById(R.id.products_recyclerView);
        loadMorePB=findViewById(R.id.fav_loadMore_PB);
        loadProductsPB =findViewById(R.id.load_Products);
        constraintLayout = findViewById(R.id.products_activity);



        productsList=new ArrayList<>();

        Intent intent = getIntent();

        if (intent.hasExtra(TARGET_KEY)&&intent.getStringExtra(TARGET_KEY).equals(RA_TARGET)){
            target=RA_TARGET;

            loadProductsPB.setVisibility(View.VISIBLE);
            if (Network.isConnected(this)) {
                getRecentlyAddedProducts(1);
            } else{
                SnackBarUtil.showSnackBar(constraintLayout);
            }
        }

        if (intent.hasExtra(CATEGORY_ID_KEY)&& intent.getIntExtra(CATEGORY_ID_KEY,-1)>-1){
            target=CATEGORY_ID_KEY;
            categoryId=intent.getIntExtra(CATEGORY_ID_KEY,-1);
            loadProductsPB.setVisibility(View.VISIBLE);
            if (Network.isConnected(this)){
                getProducts(String.valueOf(categoryId),1);
            }else{
                SnackBarUtil.showSnackBar(constraintLayout);
            }
        }


        showProducts();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("spinner","i "+l);
                if(l==1){
                    if (Network.isConnected(getApplicationContext())){
                        orderProductsByDate();


                    }else {
                        SnackBarUtil.showSnackBar(constraintLayout);
                    }

                }
                else if(l==2) {
                    if (Network.isConnected(getApplicationContext())) {
                        orderProductsByPriceLow();
                    } else {
                        SnackBarUtil.showSnackBar(constraintLayout);
                    }
                }
                else if(l==3) {
                    if (Network.isConnected(getApplicationContext())) {
                        orderProductsByPriceHigh();
                    }else {
                        SnackBarUtil.showSnackBar(constraintLayout);
                    }
                }
                else if (l==4) {
                    if (Network.isConnected(getApplicationContext())) {
                        orderProductsByRate();
                    }else {
                        SnackBarUtil.showSnackBar(constraintLayout);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                loadMorePB.setVisibility(View.VISIBLE);
                Log.d("ENDLESSSCROLL","page "+page);
                if (target.equals(CATEGORY_ID_KEY))
                    if (Network.isConnected(getApplicationContext())) {
                        getProducts(String.valueOf(categoryId), page);
                    }else {
                        SnackBarUtil.showSnackBar(constraintLayout);
                    }
                else if (target.equals(RA_TARGET))
                    if (Network.isConnected(getApplicationContext())) {
                        getRecentlyAddedProducts(page);
                    }else {
                        SnackBarUtil.showSnackBar(constraintLayout);
                    }

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
        RetrofetClient.getApiService().getProducts(categoryid,page,null,null)
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> productsList= response.body();
                        if (productsList!=null&&productsList.size()>0)
                            productAdapter.addItems(productsList);
                        loadMorePB.setVisibility(View.GONE);
                        loadProductsPB.setVisibility(View.GONE);


                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        loadMorePB.setVisibility(View.GONE);
                        Log.d("categoryyy","amira"+t.getMessage());
                        loadProductsPB.setVisibility(View.GONE);

                    }
                });
    }



    private void getRecentlyAddedProducts(int page){
        RetrofetClient
                .getApiService()
                .getRecentlyAddedProducts(page)
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        Log.d("categoryyy","on response "+response.body().size());
                        ArrayList<Product> productsList= response.body();
                        if (productsList!=null&&productsList.size()>0)
                            productAdapter.addItems(productsList);
                        loadMorePB.setVisibility(View.GONE);
                        loadProductsPB.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        loadMorePB.setVisibility(View.GONE);
                        Log.d("categoryyy","amira"+t.getMessage());
                        loadProductsPB.setVisibility(View.GONE);
                    }
                });
    }

    private void showProducts() {
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            gridLayoutManager =new GridLayoutManager(this,2);
        else
            gridLayoutManager =new GridLayoutManager(this,4);


        productAdapter=new ProductAdapter(productsList,this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    private void setUpSpinner(){
        ArrayList<String> sortList=new ArrayList<>();
        sortList.add("select..");
        sortList.add("date");
        sortList.add("price(Low)");
        sortList.add("price(High)");
        sortList.add("Rating");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sortList);
        spinner.setAdapter(adapter);
    }


    private void orderProductsByDate(){
        loadProductsPB.setVisibility(View.VISIBLE);
        RetrofetClient.getApiService().getProducts(String.valueOf(categoryId),1,"date",null).enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>>
                                           call, Response<ArrayList<Product>> response) {
                ArrayList<Product> products = response.body();
                if(products!=null){
                    productsList=products;
                    showProducts();
                }
                loadProductsPB.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });


    }
    private void orderProductsByPriceLow(){
        loadProductsPB.setVisibility(View.VISIBLE);
        RetrofetClient
                .getApiService()
                .getProducts(String.valueOf(categoryId),1,"price","asc")
                .enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> products =response.body();
                if(products!=null){
                    productsList=products;
                    showProducts();
                }
                loadProductsPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });


    }
    private void orderProductsByPriceHigh() {
        loadProductsPB.setVisibility(View.VISIBLE);
        RetrofetClient
                .getApiService()
                .getProducts(String.valueOf(categoryId),1,"price","desc")
                .enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> products=response.body();
                if(products!=null) {
                    productsList=products;
                    showProducts();
                }
                loadProductsPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });


    }


    private void orderProductsByRate() {
        RetrofetClient.getApiService()
                .getProducts(String.valueOf(categoryId),1,"Rating",null)
                .enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> productArrayList =response.body();
                if(productArrayList!=null) {
                    productsList=productArrayList;
                    showProducts();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }






      //  public void getFakeData (){
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
