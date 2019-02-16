package com.ahmdkhled.ecommerce.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.repository.SharedAddressRepository;

import java.util.List;

public class AddressViewModel extends ViewModel {


    private SharedAddressRepository mAddressActivtyRepo;
    private MutableLiveData<List<Address>> mAddressList;
    private MutableLiveData<Boolean> mIsLoading,mIsAdding = new MutableLiveData<>();


    public void init(){
        Log.d("mvvm","inside init");
        if(mAddressList == null){
            mAddressList = new MutableLiveData<>();
        }
        mAddressActivtyRepo = SharedAddressRepository.getInstance();
    }


    // get list of addresses
    public MutableLiveData<List<Address>> getAddresses(String userId){
        mAddressList = mAddressActivtyRepo.getAddresses(userId);
        mIsLoading = mAddressActivtyRepo.getmIsLoading();
        mIsAdding = mAddressActivtyRepo.getmIsAdding();
        return mAddressList;
    }

    /*
    if mIsAdding is true, there is a new address has successfully added
     */
    public MutableLiveData<Boolean> isAdding() {
        return mIsAdding;
    }

    /*
    if mIsLoading is true, addresses are successfully fetched
     */
    public MutableLiveData<Boolean> isLoading(){
        return mIsLoading;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }




}
