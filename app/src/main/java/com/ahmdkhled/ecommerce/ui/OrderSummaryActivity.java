package com.ahmdkhled.ecommerce.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity {
    private static final String TAG = OrderSummaryActivity.class.getSimpleName();
    @BindView(R.id.tv_full_name)
    TextView mNameTxt;
    @BindView(R.id.tv_address)
    TextView mAddressTxt;
    @BindView(R.id.tv_phone_number)
    TextView mPhoneNumberTxt;
    private String userId = "2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        ButterKnife.bind(this);
        getAvailableAddress(userId);

    }

    private void getAvailableAddress(String userId) {
        Call<List<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if(response.isSuccessful()){
                    List<Address> mAddresses = response.body();
                    if(mAddresses != null && mAddresses.size() > 0){
                        updateAddress(mAddresses.get(0));
                    }

                    else{
                        userHasNoAddressYet();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });
    }

    private void userHasNoAddressYet() {
        Log.d(TAG,"userHasNoAddressYet");
    }

    private void updateAddress(Address address) {
        mNameTxt.setText(address.getFirst_name());
        mAddressTxt.setText(address.getAddress_1());
        mPhoneNumberTxt.setText(address.getPhone_number());
    }
}