package com.ahmdkhled.ecommerce.ui;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AddressActivity extends AppCompatActivity {

    private static final String TAG = "fromAddressActivity";
    @BindView(R.id.address_recycler_view)
    RecyclerView mAddressRecyclerView;

    AddressAdapter mAddressAdapter;
    ArrayList<Address> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        // bind views
        ButterKnife.bind(this);

        // setup recycler view
        mAddressAdapter = new AddressAdapter(this,addresses);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
        getAddresses("2");

    }

    public void getAddresses(String userId){
        Call<ArrayList<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<ArrayList<Address>>() {
            @Override
            public void onResponse(Call<ArrayList<Address>> call, retrofit2.Response<ArrayList<Address>> response) {
                if(response.isSuccessful()){
                    addresses = response.body();
                    mAddressAdapter.notifyAdapter(addresses);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Address>> call, Throwable t) {
                Log.d(TAG,"failure "+t.getMessage());
            }
        });
    }
}
