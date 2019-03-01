package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.SharedAddressRepository;

import java.util.List;

public class AddAddressViewModel extends ViewModel {

    private SharedAddressRepository mAddressActivtyRepo;
    private MutableLiveData<Response> response;
    private MutableLiveData<Boolean> mIsAdding = new MutableLiveData<>();


    public void init(){

        mAddressActivtyRepo = SharedAddressRepository.getInstance();
    }

    // To add new address
    public void addAddress(Address address,String userId){
        response = mAddressActivtyRepo.addAddress(address,userId);

    }

    public MutableLiveData<Response> getResponse() {
        if(response == null)response = new MutableLiveData<>();
        return response;
    }

    @Override
    protected void onCleared() {
        Log.d("mvvm","add address viewmodel is cleared");
        super.onCleared();
    }
}
