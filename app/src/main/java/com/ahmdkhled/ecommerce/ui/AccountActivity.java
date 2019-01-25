package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.utils.SessionManager;

public class AccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    SessionManager sessionManager;
    TextView userName,email;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar=findViewById(R.id.account_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userName=findViewById(R.id.account_userName);
        email=findViewById(R.id.account_email);
        login=findViewById(R.id.accountLogin);
        sessionManager=new SessionManager(this);
        populateDetails();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
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
        getMenuInflater().inflate(R.menu.account_menu,menu);
        if (!sessionManager.sessionExist()){
            menu.findItem(R.id.logOut).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
