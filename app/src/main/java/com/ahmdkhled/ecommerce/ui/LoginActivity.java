package com.ahmdkhled.ecommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.ahmdkhled.ecommerce.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginEmail)
    TextView loginEmail;
    @BindView(R.id.loginPass)
    TextView loginPass;
    @BindView(R.id.login_BU)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


    }




}
