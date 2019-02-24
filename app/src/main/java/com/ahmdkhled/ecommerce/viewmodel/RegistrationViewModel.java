package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.RegistrationRepository;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<Response> mResponse;
    private RegistrationRepository mRegistrationRepository;
    private MutableLiveData<Boolean> mIsProcessing = new MutableLiveData<>();


    public void init(){
        if(mResponse == null) {
            mResponse = new MutableLiveData<>();
        }
        mRegistrationRepository = RegistrationRepository.getInstance();
    }

    public void signUp(String name, String email, String password) {
        mResponse = mRegistrationRepository.signUp(name,email,password);
        mIsProcessing = mRegistrationRepository.isProcessing();


    }


    public MutableLiveData<Response> getSignUpResponse() {
        return mResponse;
    }

    public MutableLiveData<Boolean> isProcessing() {
        return mIsProcessing;
    }
}
