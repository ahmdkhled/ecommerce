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
    private MutableLiveData<Response> mDeleteResponse;
    private MutableLiveData<Boolean> mIsLoading,mIsAdding = new MutableLiveData<>();


    public void init(String userId){
        Log.d("mvvm","inside init");
        if(mAddressList != null){
            return;
        }
        mAddressActivtyRepo = SharedAddressRepository.getInstance();
        setAddressList(userId);

    }

    public void setAddressList(String userId){
        mAddressList = mAddressActivtyRepo.getAddresses(userId);
        mIsLoading = mAddressActivtyRepo.getmIsLoading();
        mIsAdding = mAddressActivtyRepo.getmIsAdding();
    }




    /**
     * if mIsAdding is true, there is a new address has successfully added
     */
    public MutableLiveData<Boolean> isAdding() {
        return mIsAdding;
    }

    /**
     * if mIsLoading is true, addresses are successfully fetched
     */
    public MutableLiveData<Boolean> isLoading(){
        Log.d("add_mvvm","vm isLoading");
        return mIsLoading;

    }


    public MutableLiveData<List<Address>> getAddressList() {
        Log.d("add_mvvm","vm getAddressList");
        return mAddressList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }




}
