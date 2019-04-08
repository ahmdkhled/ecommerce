package com.ahmdkhled.ecommerce.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.AddressRepository;

import java.util.List;

public class AddressViewModel extends ViewModel {


    private AddressRepository mAddressActivtyRepo;
    private MutableLiveData<List<Address>> mAddressList;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsDeleting,mIsAddressSatDefault;
    private MutableLiveData<Response> mDeleteResponse,mSetDefaultResponse;


    public void init(){
       if(mAddressList != null){
            return;
       }
       mAddressActivtyRepo = AddressRepository.getInstance();
    }

    public void loadAddresses(String userId,String isDefault){
        Log.d("add_address","loadAddress vm");
        if(mAddressList != null){
            Log.d("add_address","loadAddress vm list not null");
            return;
        }
        mAddressList = mAddressActivtyRepo.getAddresses(userId,isDefault);
        mIsLoading = mAddressActivtyRepo.getmIsLoading();

    }



    public MutableLiveData<List<Address>> getAddressList() {
        return mAddressList;
    }

    public void deleteAddress(Address mAddress){
        mDeleteResponse = mAddressActivtyRepo.deleteAddress(mAddress.getId());
        mIsDeleting = mAddressActivtyRepo.getIsDeleting();
    }

    public MutableLiveData<Response> getDeleteResponse() {
        if(mDeleteResponse == null)mDeleteResponse = new MutableLiveData<>();
        return mDeleteResponse;
    }

    public MutableLiveData<Boolean> getIsDeleting() {
        if(mIsDeleting == null)mIsDeleting = new MutableLiveData<>();
        return mIsDeleting;
    }

    public MutableLiveData<Boolean> isLoading(){
        if(mIsLoading == null)mIsLoading = new MutableLiveData<>();
        return mIsLoading;

    }


    public void setDefaultAddress(long userId, Address address) {
        mSetDefaultResponse = mAddressActivtyRepo.setDefaultAddress(userId,address);
        mIsAddressSatDefault = mAddressActivtyRepo.getmIsAddressSatDefault();
    }

    public MutableLiveData<Response> getSetDefaultResponse(){
        if(mSetDefaultResponse == null)mSetDefaultResponse = new MutableLiveData<>();

        return mSetDefaultResponse;

    }

    public MutableLiveData<Boolean> getmIsAddressSatDefault() {
        if(mIsAddressSatDefault == null) mIsAddressSatDefault = new MutableLiveData<>();
        return mIsAddressSatDefault;
    }
}
