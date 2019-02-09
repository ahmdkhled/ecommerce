package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import android.support.annotation.Nullable;
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
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.viewmodel.AddressActivityViewModel;

import java.util.ArrayList;
import java.util.List;

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
    AddressActivityViewModel mAddressActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        // bind views
        ButterKnife.bind(this);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.address_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // link address view model with this activity
        mAddressActivityViewModel = ViewModelProviders.of(this).get(AddressActivityViewModel.class);
        mAddressActivityViewModel.init();
        mAddressActivityViewModel.getAddresses("2").observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                mAddressAdapter.notifyAdapter(addresses);
            }
        });

        mAddressActivityViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) showProgressBar();
                else hideProgressBar();
            }
        });

        initRecyclerView();


        mAddAddressFAB.setOnClickListener(this);

    }

    private void initRecyclerView() {
        // setup recycler view
        Log.d("viewmodeldemo","inside init RV");

        mAddressAdapter = new AddressAdapter(this,mAddressActivityViewModel.getAddresses("2").getValue());
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }


    @Override
    public void onClick(View view) {
        Intent addAddressIntent = new Intent(this,AddAddressActivity.class);
        startActivity(addAddressIntent);
    }

    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
