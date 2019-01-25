package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.MainCategoriesAdapter;
import com.ahmdkhled.ecommerce.adapter.MainSliderAdapter;
import com.ahmdkhled.ecommerce.adapter.RecentlyAddedProducsAdapter;
import com.ahmdkhled.ecommerce.model.Ad;
import com.ahmdkhled.ecommerce.model.Category;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewPager mainSliderPager;
    RecyclerView categoryRecycler;
    RecyclerView recentlyAddedRecycler;
    Button seeAllCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.mainToolbar);
        drawerLayout=findViewById(R.id.mainDrawerLayout);
        navigationView=findViewById(R.id.mainNavView);
        mainSliderPager=findViewById(R.id.mainSliderPager);
        categoryRecycler=findViewById(R.id.mainCategoryRecycler);
        recentlyAddedRecycler=findViewById(R.id.recentlyAddedProductsRecycler);
        seeAllCategories=findViewById(R.id.seeAllCategories);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        navigationView.setNavigationItemSelectedListener(this);

        seeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), CategoriesActivity.class);
                startActivity(intent);
            }
        });




        handleNavHeader();
        getRecentlyAdedProducts();
        getCategories();
        getAds();
    }

    void getCategories(){
        RetrofetClient.getApiService()
                .getCategories()
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        ArrayList<Category> categories=response.body();
                        if (categories!=null){
                            populateMenu(categories);
                            showCategories(categories);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

                    }
                });
    }

    void getRecentlyAdedProducts(){
        RetrofetClient.getApiService()
                .getRecentlyAdedProducts()
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        if (response.isSuccessful()){
                            ArrayList<Product> products=response.body();
                            Log.d("RECENTLYADDD",products.get(0).getPrice()+" --- ");
                            showRecentlyAdedProducts(products);

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Log.d("RECENTLYADDD",t.getMessage());
                    }
                });

    }

    private void showSlider(ArrayList<Ad> ads){
        MainSliderAdapter mainSliderAdapter=new MainSliderAdapter(this,ads);
        mainSliderPager.setAdapter(mainSliderAdapter);
    }

    private void showCategories(ArrayList<Category> categories){
        MainCategoriesAdapter mainCategoriesAdapter=new MainCategoriesAdapter(this,categories);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false);
        categoryRecycler.setAdapter(mainCategoriesAdapter);
        categoryRecycler.setLayoutManager(linearLayoutManager);
    }

    private void showRecentlyAdedProducts(ArrayList<Product> productsList){
         RecentlyAddedProducsAdapter recentlyAddedProducsAdapter =new RecentlyAddedProducsAdapter(this,productsList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false);
        recentlyAddedRecycler.setAdapter(recentlyAddedProducsAdapter);
        recentlyAddedRecycler.setLayoutManager(linearLayoutManager);
    }

    private void getAds(){
        RetrofetClient.getApiService()
                .getAds()
                .enqueue(new Callback<ArrayList<Ad>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Ad>> call, Response<ArrayList<Ad>> response) {
                        if (response.isSuccessful()){
                            ArrayList<Ad> ads=response.body();
                            showSlider(ads);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Ad>> call, Throwable t) {

                    }
                });
    }

    private void populateMenu(ArrayList<Category> categoriesList){
        Menu menu=navigationView.getMenu();
        SubMenu subMenu=menu.addSubMenu("categories");
        for (int i = 0; i < categoriesList.size(); i++) {
            Category c=categoriesList.get(i);
            //(categoryId,itemId,order,itemTitle)
            subMenu.add(0,c.getId(),0,c.getName());
        }
        navigationView.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.cart){
            Intent intent=new Intent(this,CartActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent=new Intent(this, ProductsActivity.class);
        intent.putExtra(ProductsActivity.CATEGORY_ID_KEY,item.getItemId());
        startActivity(intent);
        return true;
    }

    private void handleNavHeader(){
        View navHeader=navigationView.getHeaderView(0);
        TextView userName=navHeader.findViewById(R.id.nav_header_title);
        TextView email=navHeader.findViewById(R.id.nav_header_desc);
        ImageView img=navHeader.findViewById(R.id.nav_header_image);
        final SessionManager sessionManager=new SessionManager(this);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AccountActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.sessionExist()){
                    Intent intent=new Intent(getApplicationContext(),AccountActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.sessionExist()){
                    Intent intent=new Intent(getApplicationContext(),AccountActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

            }
        });

        if (sessionManager.sessionExist()){
            userName.setText(sessionManager.getUserName());
            email.setText(sessionManager.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    ArrayList<Ad> getFakeAds(){
        ArrayList<Ad> ads=new ArrayList<>();
        ads.add(new Ad("https://cms.souqcdn.com/cms/boxes/img/desktop/L_1541434120_Home-Best-Selling-ar.jpg"));
        ads.add(new Ad("https://cms.souqcdn.com/cms/boxes/img/desktop/L_1541346447_Men-Shirts-ar.jpg"));
        ads.add(new Ad("https://cms.souqcdn.com/cms/boxes/img/desktop/L_1541433887_Supermarket-ar.jpg"));
        return ads;
    }
}
