package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.ui.LoginActivity;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

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

    private int mSubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        // get sub total
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("sub_total")){
            mSubTotal = intent.getIntExtra("sub_total",0);
        }

        fillViews();

        mCODRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) mCODLayoutTxt.setVisibility(View.VISIBLE);
            }
        });


    }

    private void fillViews() {
        mSubTotalTxt.setText(getString(R.string.sub_total_payment,mSubTotal));
    }


}
