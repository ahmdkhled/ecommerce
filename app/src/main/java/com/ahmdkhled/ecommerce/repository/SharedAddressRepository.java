package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SharedAddressRepository {

    private static final String TAG = SharedAddressRepository.class.getSimpleName();

    private MutableLiveData<List<Address>> mAddressList = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private static SharedAddressRepository mInstance;
    private MutableLiveData<Boolean> mIsAdding = new MutableLiveData<>();
    private MutableLiveData<Response> mResponse = new MutableLiveData<>();


    public static SharedAddressRepository getInstance(){
        if(mInstance == null){
            mInstance = new SharedAddressRepository();
        }
        return mInstance;
    }



    // get list of user addresses
    public MutableLiveData<List<Address>> getAddresses(String userId){
        /*
        Fetching data is processing so mIsLoading will be true
         */
        mIsLoading.setValue(true);
        Log.d("mvvm","inside get address in  repo");


        Call<List<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, retrofit2.Response<List<Address>> response) {
                if(response.isSuccessful()){
                    /*
                    fetching data is done so mIsloading will be false
                     */
                    mIsLoading.setValue(false);
                    Log.d("mvvm","succeccfully loaded");
                    mAddressList.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.d("mvvm","failure "+t.getMessage());

            }
        });

        return mAddressList;
    }



    // To add new address
    public MutableLiveData<Response> addAddress(Address address,String userId) {
        Call<Response> call = RetrofetClient.getApiService().addAddress(userId,address.getState(),address.getCity(),
                address.getZip_code(),address.getAddress1(),address.getAddress2());

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    /*
                    new address is successfully added, so mIsAdding will be true
                     */
                    mIsAdding.setValue(true);
                    mResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG,"failure message : "+t.getMessage());
                /*
                  Adding address process is failed, so mIsAdding will be false
                 */
                mIsAdding.setValue(false);
            }
        });

        return mResponse;
    }

    public MutableLiveData<Boolean> getmIsAdding() {
        return mIsAdding;
    }

    public MutableLiveData<Boolean> getmIsLoading() {
        return mIsLoading;
    }
}
