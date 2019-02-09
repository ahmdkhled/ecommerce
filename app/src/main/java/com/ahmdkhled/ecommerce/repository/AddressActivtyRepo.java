package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.ui.AddressActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AddressActivtyRepo {

    private static final String TAG = AddressActivtyRepo.class.getSimpleName();

    MutableLiveData<List<Address>> mAddressList = new MutableLiveData<>();
    List<Address> addresses = new ArrayList<>();
    MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public MutableLiveData<List<Address>> getAddresses(String userId){
        mIsLoading.setValue(true);
        Log.d("viewmodeldemo","inside get address in  repo");


        Call<List<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, retrofit2.Response<List<Address>> response) {
                if(response.isSuccessful()){
                    mIsLoading.setValue(false);
                    Log.d("viewmodeldemo","succeccfully loaded");
                    addresses = response.body();
                    mAddressList.setValue(addresses);
                    Log.d("viewmodeldemo","size of list "+mAddressList.getValue().get(0).getId());

                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.d("viewmodeldemo","failure "+t.getMessage());

            }
        });

        return mAddressList;
    }

    public MutableLiveData<Boolean> getmIsLoading() {
        return mIsLoading;
    }
}
