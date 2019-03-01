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
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.viewmodel.AddressViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressActivity extends AppCompatActivity {

    private static final String TAG = AddressActivity.class.getSimpleName();
    private static final int ADD_ADDRESS_REQUEST_CODE = 1000;

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
    AddressViewModel mAddressViewModel;
    private String userId = "2";
    private int mAddressPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        // bind views
        ButterKnife.bind(this);

        // setup toolbar
        setupToolbar();


        /**
         * link address view model with this activity.
         * observe getAddress function to notify recyclerview's adapter with new list.
          */
        mAddressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
        mAddressViewModel.init();
        mAddressViewModel.loadAddresses(userId);

        // observe changes in address list
        mAddressViewModel.getAddressList().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                mAddressAdapter.notifyAdapter(addresses);
            }
        });



        /**
         * observe isLoading function to know if loading is finish or not.
         * so that i can handle progress bar status.
         * if Loading is finished progress bar will be hidden.
         * Otherwise progress bar will be shown.
         */
        mAddressViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                if(aBoolean) showProgressBar();
                else hideProgressBar();
            }
        });


        initRecyclerView();

        // observe if user wanna delete an address
        mAddressAdapter.getWannaDelete().observe(this, new Observer<AddressItem>() {
            @Override
            public void onChanged(@Nullable AddressItem address) {
                mAddressViewModel.deleteAddress(address.getmAddress());
                mAddressPosition = address.getPosition();
                observeAddressDeletionResponse();
                observeAddressDeletionStatus();
            }
        });



        // if user hit FAB to add new address, add address activity will be launched

        mAddAddressFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddressIntent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivityForResult(addAddressIntent,ADD_ADDRESS_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ADDRESS_REQUEST_CODE && resultCode == RESULT_OK && data !=  null){
            if(data.hasExtra("new_address")) {
                String newAddressAsString = data.getStringExtra("new_address");
                Gson gson=new Gson();
                Type type = new TypeToken<Address>() {}.getType();
                Address newAddress =  gson.fromJson(newAddressAsString,type);

                mAddressAdapter.addAddress(newAddress);
            }

        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.address_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        // setup recycler view
        mAddressAdapter = new AddressAdapter(this, mAddressViewModel.getAddressList().getValue());
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }



    // observe address's deletion process response
    public void observeAddressDeletionResponse(){
        mAddressViewModel.getDeleteResponse().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                mAddressAdapter.notifyAddressHasRemoved(mAddressPosition);
                Toast.makeText(AddressActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    // observe address's deletion process status
    public void observeAddressDeletionStatus(){
        mAddressViewModel.getIsDeleting().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)showProgressBar();
                else hideProgressBar();

            }
        });
    }

    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
