package com.ahmdkhled.ecommerce.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.SharedAddressRepository;

import java.util.List;

public class AddressViewModel extends ViewModel {


    private SharedAddressRepository mAddressActivtyRepo;
    private MutableLiveData<List<Address>> mAddressList;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Response> mDeleteResponse;


    public void init(){
        mAddressActivtyRepo = SharedAddressRepository.getInstance();
    }

    public void loadAddresses(String userId){
        if(mAddressList != null){
            return;
        }
        mAddressList = mAddressActivtyRepo.getAddresses(userId);
        mIsLoading = mAddressActivtyRepo.getmIsLoading();

    }



    public MutableLiveData<List<Address>> getAddressList() {
        return mAddressList;
    }

    public void deleteAddress(Address mAddress){
        mDeleteResponse = mAddressActivtyRepo.deleteAddress(mAddress.getId());
    }

    public MutableLiveData<Response> getDeleteResponse() {
        if(mDeleteResponse == null)mDeleteResponse = new MutableLiveData<>();
        return mDeleteResponse;
    }

    public MutableLiveData<Boolean> isLoading(){
        return mIsLoading;

    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }




}
