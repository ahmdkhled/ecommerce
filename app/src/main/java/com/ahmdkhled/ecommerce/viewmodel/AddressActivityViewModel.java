package com.ahmdkhled.ecommerce.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.repository.AddressActivtyRepo;

import java.util.List;

public class AddressActivityViewModel extends ViewModel {


    AddressActivtyRepo mAddressActivtyRepo;
    MutableLiveData<List<Address>> mAddressList = new MutableLiveData<>();
    MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public void init(){
        if(mAddressActivtyRepo == null){
            mAddressActivtyRepo = new AddressActivtyRepo();
        }
    }

    public MutableLiveData<List<Address>> getAddresses(String userId){
        Log.d("viewmodeldemo","inside get address in view model");
        mAddressList = mAddressActivtyRepo.getAddresses(userId);
        List<Address> addresses = mAddressList.getValue();
        mIsLoading = mAddressActivtyRepo.getmIsLoading();

        return mAddressList;
    }

    public MutableLiveData<Boolean> isLoading(){
        return mIsLoading;
    }


}
