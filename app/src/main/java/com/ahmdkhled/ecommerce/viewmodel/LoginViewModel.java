package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.LoginRepository;

public class LoginViewModel extends ViewModel{

    MutableLiveData<Boolean>isLoading;
    private MutableLiveData<Response> response=new MutableLiveData<>();
    MutableLiveData<String> error=new MutableLiveData<>();


    public void login(String email,String password) {
            response=LoginRepository.getInstance().login(email,password);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return LoginRepository.getInstance().getIsLoading();
    }

    public MutableLiveData<String> getError() {
        return LoginRepository.getInstance().getError();
    }

    public MutableLiveData<Response> getResponse() {
        return response;
    }
}
