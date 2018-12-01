package com.ahmdkhled.ecommerce.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.CategoryResponse;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.mainToolbar);
        drawerLayout=findViewById(R.id.mainDrawerLayout);
        navigationView=findViewById(R.id.mainNavView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        navigationView.setNavigationItemSelectedListener(this);


//        getCategories();
    }

//    void getCategories(){
//        RetrofetClient.getApiService()
//                .getCategories()
//                .enqueue(new Callback<ArrayList<CategoryResponse>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<CategoryResponse>> call, Response<ArrayList<CategoryResponse>> response) {
//                        ArrayList<CategoryResponse> categories=response.body();
//                        populateMenu(categories);
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<CategoryResponse>> call, Throwable t) {
//
//                    }
//                });
//    }

    private void populateMenu(ArrayList<CategoryResponse> categoriesList){
//        Menu menu=navigationView.getMenu();
//        SubMenu subMenu=menu.addSubMenu("categories");
//        for (int i = 0; i < categoriesList.size(); i++) {
//            CategoryResponse c=categoriesList.get(i);
//            //(categoryId,itemId,order,itemTitle)
//            subMenu.add("0",c.getId(),0,c.getName());
//        }
//        navigationView.invalidate();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getApplicationContext()," "+item.getTitle(),Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
