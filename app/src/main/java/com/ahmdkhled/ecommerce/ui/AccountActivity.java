package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.OrdersAdapter;
import com.ahmdkhled.ecommerce.utils.SessionManager;

public class AccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    SessionManager sessionManager;
    TextView userName,email;
    Button login;
    Menu menu;
    ConstraintLayout favoritesContainer;
    ConstraintLayout ordersContainer;
    TextView mUserAddressTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar=findViewById(R.id.account_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        userName=findViewById(R.id.account_userName);
        email=findViewById(R.id.account_email);
        login=findViewById(R.id.accountLogin);
        favoritesContainer=findViewById(R.id.favorites_container);
        ordersContainer=findViewById(R.id.orders_container);
        mUserAddressTxt = findViewById(R.id.address_label);
        sessionManager=new SessionManager(this);
        populateDetails();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        favoritesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FavoritesActivity.class);
                startActivity(intent);
            }
        });

        ordersContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), OrdersActivity.class);
                startActivity(intent);
            }
        });

        mUserAddressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddressActivity.class));
            }
        });
    }

    private void populateDetails(){
        if(sessionManager.sessionExist()){
            userName.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            userName.setText(sessionManager.getUserName());
            email.setText(sessionManager.getEmail());
        }else{
            userName.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.account_menu,menu);
        if (!sessionManager.sessionExist()){
            menu.findItem(R.id.logOut).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }else if (item.getItemId()==R.id.logOut){
            sessionManager.logOut();
            Toast.makeText(getApplicationContext(),"logged out ",Toast.LENGTH_SHORT).show();
            userName.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            menu.findItem(R.id.logOut).setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }
}
