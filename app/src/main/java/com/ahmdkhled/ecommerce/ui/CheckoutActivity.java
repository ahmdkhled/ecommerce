package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ShipmentAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;


import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CHECKOUT_ACTIVITY_TAG";
    private static final int CHANGE_ADDRESS_REQUEST = 1002;
    private static final int ADD_ADDRESS_REQUEST = 1003;

    @BindView(R.id.address_user_name)
    TextView mAddressUserNameTxt;
    @BindView(R.id.address_details)
    TextView mAddressDetailsTxt;
    @BindView(R.id.address_phone_number)
    TextView mAddressPhoneTxt;
    @BindView(R.id.change_address_txt)
    TextView mChangeAddress;
    @BindView(R.id.shipment_recycler_view)
    RecyclerView mShipmentRecyclerView;
    @BindView(R.id.sub_total_value)
    TextView mSubTotalTxt;
    @BindView(R.id.payment_btn)
    Button mPaymentBtn;
    @BindView(R.id.checkout_add_progress_bar)
    ProgressBar mAddressProgressBar;
    @BindView(R.id.checkout_shipment_progress_bar)
    ProgressBar mShipmentsProgressBar;



    CartResponse shipments = new CartResponse(0,new ArrayList<Product>());
    ShipmentAdapter mShipmentAdapter;
    long userId;
    CheckoutViewModel mCheckoutViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // bind views
        ButterKnife.bind(this);

        /**
         * check first if user has session.
         * if he hasn't he should login first,
         */

        userId = getUserId();

        if(userId == -1){
            Intent loginIntent = new Intent(this,LoginActivity.class);
            loginIntent.putExtra("source",CheckoutActivity.class.getSimpleName());
            startActivity(loginIntent);
            finish();
        }


        // link view model
        mCheckoutViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);

        mCheckoutViewModel.init(getApplication(),userId);


        // observe loading address process' response
        mCheckoutViewModel.getAddress().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                if(addresses.size() > 0) {
                    fillAddress(addresses.get(0));
                }else addNewAddress();
            }
        });

        // observe loading address process' status
        mCheckoutViewModel.addressIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) {
                    showAddressProgressBar();
                    mChangeAddress.setVisibility(View.INVISIBLE);
                }
                else {
                    hideAddressProgressBar();
                    mChangeAddress.setVisibility(View.VISIBLE);
                }
            }
        });


        // observe loading cart items process' response
        mCheckoutViewModel.getCart().observe(this, new Observer<CartResponse>() {
            @Override
            public void onChanged(@Nullable CartResponse cartResponse) {
                mShipmentAdapter.notifyAdapter(cartResponse);
                mSubTotalTxt.setText(cartResponse.getTotal()+"");
            }
        });

        // observe loading cart items process' status
        mCheckoutViewModel.cartIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)showShipmentProgressBar();
                else hideShipmentProgressBar();
            }
        });






        initShipmentRecyclerView();
        mChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeAddressIntent = new Intent(CheckoutActivity.this,AddressActivity.class);
                changeAddressIntent.putExtra("source","checkout");
                changeAddressIntent.putExtra("user_id",userId);
                startActivityForResult(changeAddressIntent,CHANGE_ADDRESS_REQUEST);
            }
        });


    }

    private void addNewAddress() {
        Intent addAddressIntent = new Intent(CheckoutActivity.this,AddAddressActivity.class);
        addAddressIntent.putExtra("user_id",userId);
        startActivityForResult(addAddressIntent,ADD_ADDRESS_REQUEST);
    }


    private void fillAddress(Address address) {
        mAddressUserNameTxt.setText(getString(R.string.address_user_name,address.getFirst_name(),
                address.getLast_name()));
        mAddressDetailsTxt.setText(getString(R.string.address_details,address.getAddress_1(),
                address.getAddress_2()));
        mAddressPhoneTxt.setText(address.getPhone_number());
    }


    private long getUserId() {
        SessionManager sessionManager = new SessionManager(this);
        return  sessionManager.getId();
    }



    private void initShipmentRecyclerView() {
        mShipmentAdapter = new ShipmentAdapter(shipments,this);
        mShipmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShipmentRecyclerView.setAdapter(mShipmentAdapter);
        mShipmentRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("checkout","result "+resultCode);
        if(data != null){
            Address address = data.getParcelableExtra("new_address");
            if(requestCode == CHANGE_ADDRESS_REQUEST && resultCode == RESULT_OK){
                fillAddress(address);

            }
            else if(requestCode == ADD_ADDRESS_REQUEST){
                if(resultCode == RESULT_OK)fillAddress(address);
                else finish();
            }

        }
    }

    private void showAddressProgressBar(){
        mAddressProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideAddressProgressBar(){
        mAddressProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showShipmentProgressBar(){
        mShipmentsProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideShipmentProgressBar(){
        mShipmentsProgressBar.setVisibility(View.INVISIBLE);
    }
}
