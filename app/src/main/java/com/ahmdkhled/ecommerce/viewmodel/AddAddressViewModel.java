package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.repository.AddressRepository;


public class AddAddressViewModel extends ViewModel {

    private AddressRepository mAddressActivtyRepo;
    private MutableLiveData<Response> addResponse,editResponse;
    private MutableLiveData<Boolean> mIsAdding,mIsEditing;


    public void init(){
        if(addResponse != null){
            return;
        }
        mAddressActivtyRepo = AddressRepository.getInstance();
    }

    // To add new address
    public void addAddress(Address address,String userId){
        addResponse = mAddressActivtyRepo.addAddress(address,userId);
        mIsAdding = mAddressActivtyRepo.getmIsAdding();

    }

    public MutableLiveData<Response> getAddResponse() {
        if(addResponse == null) addResponse = new MutableLiveData<>();
        return addResponse;
    }

    public MutableLiveData<Boolean> getIsAdding() {
        if(mIsAdding == null)mIsAdding = new MutableLiveData<>();
        return mIsAdding;
    }



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
