package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        


    }



}
