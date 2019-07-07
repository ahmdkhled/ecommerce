package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.CheckoutViewPagerAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.utils.CustomViewPager;
import com.ahmdkhled.ecommerce.utils.PrefManager;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {


    private static final int SELECT_ADDRESS_REQUEST_CODE = 1005;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.checkout_view_pager)
    CustomViewPager mViewPager;


    @BindView(R.id.shipping_txt)
    TextView mShippingTxt;
    @BindView(R.id.shipping_value)
    TextView mShippingValueTxt;
    @BindView(R.id.total_txt)
    TextView mTotalTxt;
    @BindView(R.id.total_value)
    TextView mTotalValueTxt;
    @BindView(R.id.continue_btn)
    Button mContinueBtn;
    @BindView(R.id.CheckoutToolbar)
    Toolbar toolbar;

    CheckoutViewPagerAdapter mCheckoutViewPagerAdapter;
    private Fragment[] checkoutFragment = {new AddressFragment(), new CheckoutShippingFragment()
                                            , new CheckoutPaymentFragment()};
    private TabLayout.Tab mTab;
    private int tabPosition;
    private String[] pageTitles = {"Address","Shipping","Payment"};
    private CheckoutViewModel mCheckoutViewModel;
    private int shippingOption;
    private int shippingAddressId;
    private int paymentOption;
    private double total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // bind views
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        // set ROBOTO font
        setFonts();

        // setup viewpager
        setupViewPager();

        // set tab view
        setTabView();

        // disable tabs selection
        disableTabSelection();

        // set allowed viewpager swipe direction
        mViewPager.setAllowedDirecrion("left");

        // setup button text at the beginning
        mContinueBtn.setText("Continue To Shipping");

        // get total from intent
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("total")){
            total = intent.getIntExtra("total",0);
            mTotalValueTxt.setText(total+" EGP");
        }

        // handle tab view as step indicator
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() > tabPosition){
                    // forward
                    tab.setCustomView(null);
                    tab.setCustomView(mCheckoutViewPagerAdapter.getTabView(tab.getPosition(),true));
                }else{
                    for(int i = tabPosition; i - tab.getPosition() > 0; i--) {
                        mTab = mTabLayout.getTabAt(i);
                        mTab.setCustomView(null);
                        mTab.setCustomView(mCheckoutViewPagerAdapter.getTabView(i, false));
                    }
                }

                // change button's text based on page
                if(tab.getPosition() == 0) mContinueBtn.setText("Continue To Shipping");
                else if(tab.getPosition() == 1) mContinueBtn.setText("Continue To Payment");
                else if(tab.getPosition() == 2) mContinueBtn.setText("Place Order");


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getViewPagerItem() == 1) {
                    if(shippingOption != -1)
                        mViewPager.setCurrentItem(getViewPagerItem()+1,true);
                    else
                        Toast.makeText(CheckoutActivity.this, "please choose one shipping option",
                                Toast.LENGTH_SHORT).show();
                }
                else if(getViewPagerItem() == 2){
                    if(paymentOption != -1){
                        // place order into db
                        Log.d("order","shipping address "+shippingAddressId);
                        Log.d("order","shipping option "+shippingOption);
                        Log.d("order","payment option "+paymentOption);
                        Intent placeOrderIntent = new Intent(getApplicationContext(),OrderSummaryActivity.class);
                        placeOrderIntent.putExtra("shipping_address_id",shippingAddressId);
                        placeOrderIntent.putExtra("shipping_method",shippingOption);
                        placeOrderIntent.putExtra("payment_method",paymentOption);
                        startActivity(placeOrderIntent);
                    }else
                        Toast.makeText(CheckoutActivity.this, "please choose one payment option",
                                Toast.LENGTH_SHORT).show();

                }
                else if(getViewPagerItem() == 0){
                    if(shippingAddressId != -1)
                         mViewPager.setCurrentItem(getViewPagerItem()+1,true);
                    else Toast.makeText(CheckoutActivity.this, "please choose one address",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });



        // link to checkout view model
        mCheckoutViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);
        mCheckoutViewModel.init();

        mCheckoutViewModel.getShippingOption().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer option) {
                if(option != null) shippingOption = option;
            }
        });


        mCheckoutViewModel.getPaymentOption().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer option) {
                if(option != null) paymentOption = option;
            }
        });

        mCheckoutViewModel.getShippingAddress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer addressId) {
                if(addressId != null){
                    shippingAddressId = addressId;
                }
            }
        });

        mCheckoutViewModel.getmCouponAmount().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
                    builder.setTitle("Congrats")
                            .setMessage("You got a great offer")
                            .show();
                    total = total - (total * Integer.valueOf(s) / 100);
                    mTotalValueTxt.setText(total+" EGP");
                }
            }
        });

    }

    private void setupViewPager() {
        mCheckoutViewPagerAdapter = new CheckoutViewPagerAdapter(this,getSupportFragmentManager(),
                checkoutFragment,pageTitles);
        mViewPager.setAdapter(mCheckoutViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void setTabView() {
        for (int i = 0; i < mCheckoutViewPagerAdapter.getCount(); i++) {
            TabLayout.Tab mTab = mTabLayout.getTabAt(i);
            if (mTab != null) {
                mTab.setCustomView(mCheckoutViewPagerAdapter.getTabView(i));
            }
        }
    }

    private void disableTabSelection() {
        ViewGroup tabStrip = ((ViewGroup)mTabLayout.getChildAt(0));
        for(int i = 0; i <= 2; i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    public int getViewPagerItem(){
        return mViewPager.getCurrentItem();
    }

    private void setFonts() {
        mContinueBtn.setTypeface(Typeface.createFromAsset(getAssets(),getString(R.string.roboto_regular)));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
