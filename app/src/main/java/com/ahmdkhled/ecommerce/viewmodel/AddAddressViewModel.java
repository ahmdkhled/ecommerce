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
        Log.d("mvvm","inside init");
        if(response != null){
            return;
        }
        mAddressActivtyRepo = SharedAddressRepository.getInstance();
    }

    // To add new address
    public MutableLiveData<Response> addAddress(Address address,String userId){
        Log.d("mvvm","add address view model");
        response = mAddressActivtyRepo.addAddress(address,userId);
        return response;
    }

    @Override
    protected void onCleared() {
        Log.d("mvvm","add address viewmodel is cleared");
        super.onCleared();
    }
}
