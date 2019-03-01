package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SharedAddressRepository {

    private static final String TAG = SharedAddressRepository.class.getSimpleName();

    private static SharedAddressRepository mInstance;
    private MutableLiveData<List<Address>> mAddressList = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading,mIsDeleting;
    private MutableLiveData<Boolean> mIsAdding;
    private MutableLiveData<Response> mAddAddressResponse,mDeleteResponse;


    public static SharedAddressRepository getInstance(){
        if(mInstance == null){
            mInstance = new SharedAddressRepository();
        }
        return mInstance;
    }



    // get list of user addresses
    public MutableLiveData<List<Address>> getAddresses(String userId){
        final MutableLiveData<List<Address>> mAddressList = new MutableLiveData<>();

        /*
        Fetching data is processing so mIsLoading will be true
         */
        mIsLoading = new MutableLiveData<>();
        mIsLoading.setValue(true);
        Call<List<Address>> call = RetrofetClient.getApiService().getAddresses(userId);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, retrofit2.Response<List<Address>> response) {
                if(response.isSuccessful()){
                    /*
                    fetching data is done so mIsloading will be false
                     */
                    Log.d("add_address","assas");
                    mIsLoading.setValue(false);
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
        final MutableLiveData<Response> mResponse = new MutableLiveData<>();
        mAddAddressResponse = new MutableLiveData<>();
        mIsAdding = new MutableLiveData<>();
        mIsAdding.setValue(true);
        Call<Response> call = RetrofetClient.getApiService().addAddress(userId,address.getState(),address.getCity(),
                address.getZip_code(),address.getAddress1(),address.getAddress2());

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    mIsAdding.setValue(false);
                    mAddAddressResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG,"failure message : "+t.getMessage());
                mIsAdding.setValue(false);
                mAddAddressResponse.setValue(new Response(true,400));
            }
        });

        return mAddAddressResponse;
    }



    public MutableLiveData<Response> deleteAddress(int addressId){
        mDeleteResponse = new MutableLiveData<>();
        mIsDeleting = new MutableLiveData<>();
        mIsDeleting.setValue(true);
        Call<Response> call = RetrofetClient.getApiService().deleteAddress(addressId);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    Log.d("add_mvvm","onResponse");
                    mIsDeleting.setValue(false);
                    mDeleteResponse.setValue(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Log.d("fromAddressAdapter","delete address fail "+t.getMessage());
                mIsDeleting.setValue(false);
            }
        });

        return mDeleteResponse;


    }


    public MutableLiveData<Boolean> getmIsAdding() {
        return mIsAdding;
    }

    public MutableLiveData<Boolean> getmIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> getIsDeleting() {
        return mIsDeleting;
    }
}
