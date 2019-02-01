package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddressActivity.class.getSimpleName();
    @BindView(R.id.address_recycler_view)
    RecyclerView mAddressRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.add_address_fab)
    FloatingActionButton mAddAddressFAB;

    AddressAdapter mAddressAdapter;
    ArrayList<Address> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Log.d(TAG,"onCreate method");

        // bind views
        ButterKnife.bind(this);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.address_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // setup recycler view
        mAddressAdapter = new AddressAdapter(this,addresses);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
        getAddresses("2");

        mAddAddressFAB.setOnClickListener(this);

    }

    public void getAddresses(String userId){
        mProgressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<ArrayList<Address>>() {
            @Override
            public void onResponse(Call<ArrayList<Address>> call, retrofit2.Response<ArrayList<Address>> response) {
                if(response.isSuccessful()){
                    addresses = response.body();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mAddressAdapter.notifyAdapter(addresses);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Address>> call, Throwable t) {
                Log.d(TAG,"failure "+t.getMessage());
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddressActivity.this, "error while loading data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent addAddressIntent = new Intent(this,AddAddressActivity.class);
        startActivity(addAddressIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume method");
        getAddresses("2");
    }
}
