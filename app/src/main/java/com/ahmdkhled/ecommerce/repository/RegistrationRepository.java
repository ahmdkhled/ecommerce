package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationRepository {


    private static RegistrationRepository instance;
    private MutableLiveData<Boolean> mIsProcessing;

    public static RegistrationRepository getInstance(){
        if(instance == null){
            instance = new RegistrationRepository();
        }

        return instance;
    }

    public MutableLiveData<Response> signUp(final String name, String email, String password) {
        final MutableLiveData<Response> mResponse = new MutableLiveData<>();
        mIsProcessing = new MutableLiveData<>();
        mIsProcessing.setValue(true);
        Call<Response> call = RetrofetClient.getApiService()
                .signup(name, email, password);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    mIsProcessing.setValue(false);
                    mResponse.setValue(response.body());
                }

            }


            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                mIsProcessing.setValue(false);
                mResponse.setValue(null);
            }
        });

        return mResponse;

    }

    public MutableLiveData<Boolean> isProcessing() {
        return mIsProcessing;
    }
}
