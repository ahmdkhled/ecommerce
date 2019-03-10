package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;

import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginRepository {

    private static LoginRepository loginRepository;
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();
    private MutableLiveData<Response> mResponse;
    private MutableLiveData<String> error=new MutableLiveData<>();

    public static LoginRepository getInstance(){
        if (loginRepository==null)
            loginRepository=new LoginRepository();
        return loginRepository;
    }

    public MutableLiveData<Response> login(String email,String password){
        mResponse=new MutableLiveData<>();
        isLoading.setValue(true);
        RetrofetClient.getApiService()
                .login(email,password)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        isLoading.setValue(false);
                        Response r =response.body();
                        mResponse.setValue(r);

                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        isLoading.setValue(false);
                        error.setValue(t.getMessage());
                    }
                });
        return mResponse;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
