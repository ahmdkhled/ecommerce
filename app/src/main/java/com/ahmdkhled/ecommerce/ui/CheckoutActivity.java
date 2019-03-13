package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ShipmentAdapter;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CHECKOUT_ACTIVITY_TAG";

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


    List<CartItem> shipments;
    ShipmentAdapter mShipmentAdapter;
    String subTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // bind views
        ButterKnife.bind(this);

        // get saved shipments in shared preference
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra("total") != null) subTotal = intent.getStringExtra("total");
            if(intent.getParcelableArrayListExtra("items") != null)
                shipments = intent.getParcelableArrayListExtra("items");
        }
        

        initShipmentRecyclerView();
        mSubTotalTxt.setText(subTotal);


    }



    private boolean userHasSession() {
        SessionManager sessionManager = new SessionManager(this);
        long userId = sessionManager.getId();
        Log.d(TAG, "user id " + userId);
        return  (userId != -1);
    }



    private void initShipmentRecyclerView() {
        mShipmentAdapter = new ShipmentAdapter(shipments,this);
        mShipmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShipmentRecyclerView.setAdapter(mShipmentAdapter);
        mShipmentRecyclerView.setHasFixedSize(true);
    }

}
