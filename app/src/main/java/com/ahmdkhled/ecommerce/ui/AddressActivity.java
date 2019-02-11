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

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.viewmodel.SharedAddressViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressActivity extends AppCompatActivity {

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
    SharedAddressViewModel mSharedAddressViewModel;
    private String userId = "2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        // bind views
        ButterKnife.bind(this);

        // setup toolbar
        setupToolbar();


        /*
         link address view model with this activity.
         observe getAddress function to notify recyclerview's adapter with new list
          */
        mSharedAddressViewModel = ViewModelProviders.of(this).get(SharedAddressViewModel.class);
        mSharedAddressViewModel.init();
        mSharedAddressViewModel.getAddresses(userId).observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                Log.d("mvvm","address activity on change");
                mAddressAdapter.notifyAdapter(addresses);
            }
        });


        /*
          observe isLoading function to know if loading is finish or not.
          so that i can handle progress bar status.
          if Loading is finished progress bar will be hidden.
          Otherwise progress bar will be shown

         */
        mSharedAddressViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) showProgressBar();
                else hideProgressBar();
            }
        });




        /*
          Reload addresses again after a new address is added
          getmIsAdding function show if there is a new successfully added address or not
         */
        mSharedAddressViewModel.getmIsAdding().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    mSharedAddressViewModel.getAddresses(userId).observe(AddressActivity.this, new Observer<List<Address>>() {
                        @Override
                        public void onChanged(@Nullable List<Address> addresses) {
                            mAddressAdapter.notifyAdapter(addresses);
                        }
                    });
                }
            }
        });
        initRecyclerView();


        /*
         if user hit FAB to add new address, add address activity will be launched
          */
        mAddAddressFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddressIntent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(addAddressIntent);
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.address_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        // setup recycler view
        Log.d("mvvm","inside init RV");

        mAddressAdapter = new AddressAdapter(this, mSharedAddressViewModel.getAddresses(userId).getValue());
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }




    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
