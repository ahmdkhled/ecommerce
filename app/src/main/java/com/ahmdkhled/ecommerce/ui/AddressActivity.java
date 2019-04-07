package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.utils.AddressCommunication;
import com.ahmdkhled.ecommerce.viewmodel.AddressViewModel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressActivity extends AppCompatActivity implements AddressCommunication {

    private static final String TAG = "ADDRESS_ACTIVITY_TAG";
    private static final int ADD_ADDRESS_REQUEST_CODE = 1000;
    private static final int EDIT_ADDRESS_REQUEST_CODE = 1001;

    @BindView(R.id.address_recycler_view)
    RecyclerView mAddressRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.add_address_txt)
    TextView mBtn;
    @BindView(R.id.address_layout)
    ConstraintLayout layout;

    private AddressAdapter mAddressAdapter;
    private String source = "address_activity";
    AddressViewModel mAddressViewModel;
    private String userId = "2";
    private int editedAddressPosition;
    private int deletedAddressPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        ButterKnife.bind(this);

        setupFonts();

        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getString(R.string.address_activity_title));

         mAddressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
         mAddressViewModel.init();

         // load address
        mAddressViewModel.loadAddresses(userId,null);

        // observe load address precess response
        mAddressViewModel.getAddressList().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                if(addresses != null && addresses.size() > 0){
                    Log.d("add_address", "getAddressList size "+addresses.size());
                    mAddressAdapter.notifyAdapter(addresses);
                }
            }
        });

        // observe loading address process status
        mAddressViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) {
                    showProgressBar();
                    hideLayout();
                }
                else {
                    hideProgressBar();
                    showLayout();
                }
            }
        });


        setupRecyclerView();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new address
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivityForResult(intent,ADD_ADDRESS_REQUEST_CODE);
            }
        });


    }

    private void setupFonts() {
        mBtn.setTypeface(Typeface.createFromAsset(getAssets()
                ,getString(R.string.roboto_black)));
        mToolbarTitle.setTypeface(Typeface.createFromAsset(getAssets()
                ,getString(R.string.roboto_black)));
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    void showLayout(){
        layout.setVisibility(View.VISIBLE);
    }

    void hideLayout(){
        layout.setVisibility(View.INVISIBLE);
    }

    public  void setupRecyclerView(){
        mAddressAdapter = new AddressAdapter(this,null,this,source);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.setHasFixedSize(true);
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }


    @Override
    public void selectAddress(Address mAddress) {
        // set address as default
        if(mAddress != null){
            mAddressViewModel.setDefaultAddress(Long.parseLong(userId),mAddress);
            mAddressViewModel.getSetDefaultResponse().observe(AddressActivity.this, new Observer<Response>() {
                @Override
                public void onChanged(@Nullable Response response) {
                    Log.d("address_tag",response.getMessage());
                    if(response != null){
                        if(!response.isError()){
                            Toast.makeText(AddressActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            mAddressViewModel.getmIsAddressSatDefault().observe(AddressActivity.this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if(aBoolean)hideProgressBar();
                    else showProgressBar();
                }
            });
        }
    }

    @Override
    public void editAddress(AddressItem addressItem) {
        editedAddressPosition = addressItem.getPosition();
        Intent intent = new Intent(this,AddAddressActivity.class);
        intent.putExtra("edit_address",addressItem.getmAddress());
        startActivityForResult(intent,EDIT_ADDRESS_REQUEST_CODE);
    }

    @Override
    public void deleteAddress(final AddressItem addressItem) {
        if(addressItem != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this address");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mAddressViewModel.deleteAddress(addressItem.getmAddress());
                    mAddressViewModel.getDeleteResponse().observe(AddressActivity.this, new Observer<Response>() {
                        @Override
                        public void onChanged(@Nullable Response response) {
                            if(response != null){
                                if(!response.isError()) {
                                    mAddressAdapter.removeAddress(addressItem.getPosition());
                                }else {
                                    Log.d("address_tag", "delete address response " + response.getMessage());
                                    Toast.makeText(AddressActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    mAddressViewModel.getIsDeleting().observe(AddressActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if(aBoolean)showProgressBar();
                            else hideProgressBar();
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel",null);
            builder.show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null) {
            if (data.getParcelableExtra("new_address") != null) {
                Address address = data.getParcelableExtra("new_address");
                if (requestCode == EDIT_ADDRESS_REQUEST_CODE) {

                    mAddressAdapter.editAddress(address, editedAddressPosition);
                } else if (requestCode == ADD_ADDRESS_REQUEST_CODE) {
                    mAddressAdapter.addAddress(address);
                }
            }
        }
    }
}
