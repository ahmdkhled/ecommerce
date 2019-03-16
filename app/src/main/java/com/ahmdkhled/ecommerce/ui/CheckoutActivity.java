package com.ahmdkhled.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.ShipmentAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.utils.SessionManager;

import org.w3c.dom.Text;

import java.util.List;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



    List<CartItem> shipments;
    ShipmentAdapter mShipmentAdapter;
    String userId = "1";
    int subTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // bind views
        ButterKnife.bind(this);

        // get saved shipments in shared preference
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("total"))
                subTotal = intent.getIntExtra("total",-1);
            if(intent.getParcelableArrayListExtra("items") != null)
                shipments = intent.getParcelableArrayListExtra("items");
        }

        getAddress();
        initShipmentRecyclerView();
        mSubTotalTxt.setText(subTotal+"");
        mChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeAddressIntent = new Intent(CheckoutActivity.this,AddressActivity.class);
                startActivityForResult(changeAddressIntent,CHANGE_ADDRESS_REQUEST);
            }
        });


    }

    private void getAddress() {
        Call<List<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && response.body().size() > 0) {
                        fillAddress(response.body().get(0));
                    }else {
                        Intent addAddressIntent = new Intent(CheckoutActivity.this,AddAddressActivity.class);
                        addAddressIntent.putExtra("user_id",userId);
                        startActivityForResult(addAddressIntent,ADD_ADDRESS_REQUEST);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });
    }



    private void fillAddress(Address address) {
        mAddressUserNameTxt.setText(getString(R.string.address_user_name,address.getFirst_name(),
                address.getLast_name()));
        mAddressDetailsTxt.setText(getString(R.string.address_details,address.getAddress_1(),
                address.getAddress_2()));
        mAddressPhoneTxt.setText(address.getPhone_number());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null){
            Address address = data.getParcelableExtra("new_address");
            if(requestCode == CHANGE_ADDRESS_REQUEST || requestCode == ADD_ADDRESS_REQUEST){
                Log.d(TAG,"change address");
                fillAddress(address);
            }

        }
    }
}
