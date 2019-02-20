package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;
import com.ahmdkhled.ecommerce.ui.RegistrationActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationRepository {


    private static RegistrationRepository instance;
    private MutableLiveData<Response> mResponse = new MutableLiveData<>();

    public static RegistrationRepository getInstance(){
        if(instance == null){
            instance = new RegistrationRepository();
        }

        return instance;
    }

    public MutableLiveData<Response> signUp(String name, String email, String password) {
        Call<Response> call = RetrofetClient.getApiService()
                .signup(name, email, password);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    mResponse.setValue(response.body());
                }

            }


            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("reg_mvvm", "onFailure " + t.getMessage());
            }
        });

        return mResponse;

    }


}
