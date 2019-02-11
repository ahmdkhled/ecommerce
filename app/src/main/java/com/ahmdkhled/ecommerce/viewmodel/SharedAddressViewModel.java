package com.ahmdkhled.ecommerce.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.SharedAddressRepository;

import java.util.List;

public class SharedAddressViewModel extends ViewModel {


    private SharedAddressRepository mAddressActivtyRepo;
    private MutableLiveData<List<Address>> mAddressList;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Response> response = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsAdding = new MutableLiveData<>();


    public void init(){
        Log.d("mvvm","inside init");
        mAddressActivtyRepo = SharedAddressRepository.getInstance();
    }


    // get list of addresses
    public MutableLiveData<List<Address>> getAddresses(String userId){
        Log.d("mvvm","inside get address in view model");
        mAddressList = mAddressActivtyRepo.getAddresses(userId);
        mIsLoading = mAddressActivtyRepo.getmIsLoading();

        return mAddressList;
    }


    // To add new address
    public MutableLiveData<Response> addAddress(Address address,String userId){
        Log.d("mvvm","add address view model");
        response = mAddressActivtyRepo.addAddress(address,userId);
        mIsAdding = mAddressActivtyRepo.getmIsAdding();
        return response;
    }



    /*
    if mIsAdding is true, there is a new address has successfylly added
     */
    public MutableLiveData<Boolean> getmIsAdding() {
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
        Log.d("mvvm","address viewmodel is cleared");
        super.onCleared();
    }




}
