package com.ahmdkhled.ecommerce.viewmodel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.repository.AddressRepository;

import java.util.List;

public class CheckoutViewModel extends ViewModel {

    private AddressRepository mAddressRepository;
    private MutableLiveData<List<com.ahmdkhled.ecommerce.model.Address>> mAddress;
    private MutableLiveData<Boolean> isAddressLoading;
    private MutableLiveData<Boolean> isShippingComplete,isPaymentComplete,mChooseAddress;
    private MutableLiveData<Address> mSetNewAddress;


    public void init(){
        if(isShippingComplete == null){
            Log.d("checkout_tag","init");
            isShippingComplete = new MutableLiveData<>();

            isShippingComplete.setValue(false);


        }

        if(isPaymentComplete == null){
            isPaymentComplete = new MutableLiveData<>();
            isPaymentComplete.setValue(false);
        }

        if(mChooseAddress == null){
            mChooseAddress = new MutableLiveData<>();
            mChooseAddress.setValue(false);
        }

        if(mSetNewAddress == null) mSetNewAddress = new MutableLiveData<>();
    }

    public void setShippingComplete(boolean isAllowed){
        Log.d("checkout_tag","setShippingPolicy");
        isShippingComplete.setValue(isAllowed);
    }

    public MutableLiveData<Boolean> getIsShippingComplete() {
        Log.d("checkout_tag","getIsShippingAllowed");
        return isShippingComplete;
    }

    public void setPaymentComplete(boolean isAllowed){
        Log.d("checkout_tag","setShippingPolicy");
        isPaymentComplete.setValue(isAllowed);
    }

    public MutableLiveData<Boolean> getIsPaymentComplete() {
        Log.d("checkout_tag","getIsShippingAllowed");
        return isPaymentComplete;
    }



}
