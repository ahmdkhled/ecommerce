package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.model.OrderItem;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PAYMENT_TAG";
    @BindView(R.id.sub_total_value)
    TextView mSubTotalTxt;
    @BindView(R.id.cod_value)
    TextView mCODTxt;
    @BindView(R.id.shipping_value)
    TextView mShippingTxt;
    @BindView(R.id.total_value)
    TextView mGrandTotalTxt;
    @BindView(R.id.place_order_btn)
    Button mPlaceOrderBtn;
    @BindView(R.id.cod_rb)
    RadioButton mCODRb;
    @BindView(R.id.visa_rb)
    RadioButton mVisaRb;
    @BindView(R.id.cod_container)
    ConstraintLayout mCODLayoutTxt;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private int mSubTotal;
    private Address mShipmentAddress;
    private List<CartItem> mCartItems = new ArrayList<>();
    private PaymentViewModel mViewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        setupToolbar();

        // get sub total
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("sub_total")) {
                mSubTotal = intent.getIntExtra("sub_total", 0);
            }
            if(intent.getParcelableExtra("shipment_address") != null){
                mShipmentAddress = intent.getParcelableExtra("shipment_address");
                Log.d(TAG,"address id "+mShipmentAddress.getId());
                Log.d(TAG,"firstnamr id "+mShipmentAddress.getFirst_name());
                Log.d(TAG,"address_1 id "+mShipmentAddress.getAddress_1());
            }
        }

        fillViews();

        mCODRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) mCODLayoutTxt.setVisibility(View.VISIBLE);
            }
        });

        // view model
        mViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);

        // read cart items from shared preference
        CartItemsManger cartItemsManger = CartItemsManger.getInstance(this);
        mCartItems = cartItemsManger.getCartItems();
        Log.d(TAG,"quantity "+mCartItems.get(0).getQuantity());

        // read user id from shared preference
        userId = new SessionManager(this).getId();

        mPlaceOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.placeOrder(userId,mShipmentAddress.getId(),mCartItems);
                observePlacingOrderResponse();
                observePlacingOrderStatus();
            }
        });


    }

    private void observePlacingOrderStatus() {
        mViewModel.getIsPlacing().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(!aBoolean)showProgressBar();
                else hideProgressBar();
            }
        });
    }

    private void observePlacingOrderResponse() {
        mViewModel.getmOrder().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(@Nullable Order order) {
                Log.d(TAG,"q "+order.getOrderItems().get(0).getOrderItem_quantity());
                Intent intent = new Intent(getApplicationContext(),OrderSummaryActivity.class);
                intent.putExtra("order_id",order.getOrder_id());
                startActivity(intent);

            }
        });
    }



    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.payment_activiy_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fillViews() {
        mSubTotalTxt.setText(getString(R.string.sub_total_payment,mSubTotal));
    }



    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
