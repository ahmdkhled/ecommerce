package com.ahmdkhled.ecommerce.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.AddressRepository;
import com.ahmdkhled.ecommerce.utils.PrefManager;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {


    private AddressRepository mAddressActivtyRepo;
    private MutableLiveData<List<Address>> mAddressList;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsDeleting,mIsAddressSatDefault,mIsEditing;
    private MutableLiveData<Response> mDeleteResponse,mSetDefaultResponse,editResponse;
    private MutableLiveData<Integer> shippingAddress;

    public AddressViewModel(@NonNull Application application) {
        super(application);
    }


    public void init(){
       mAddressActivtyRepo = AddressRepository.getInstance();
    }

    public void loadAddresses(String userId,String isDefault){
        mAddressList = mAddressActivtyRepo.getAddresses(userId, isDefault);
        mIsLoading = mAddressActivtyRepo.getmIsLoading();


    }



    public MutableLiveData<List<Address>> getAddressList() {
        if(mAddressList == null)mAddressList = new MutableLiveData<>();
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

    // edit address
    public void editAddress(Address addressEdited) {
        editResponse = mAddressActivtyRepo.editAddress(addressEdited);
        mIsEditing = mAddressActivtyRepo.getISEditing();
    }

    public MutableLiveData<Response> getEditResponse() {
        if(editResponse == null) editResponse = new MutableLiveData<>();
        return editResponse;
    }

    public MutableLiveData<Boolean> getIsEditing() {
        if(mIsEditing == null)mIsEditing = new MutableLiveData<>();
        return mIsEditing;
    }


}
