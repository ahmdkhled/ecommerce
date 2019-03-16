package com.ahmdkhled.ecommerce.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.repository.AddressRepository;
import com.ahmdkhled.ecommerce.repository.CartRepo;
import com.ahmdkhled.ecommerce.utils.CartItemsManger;

import java.util.ArrayList;
import java.util.List;

public class CheckoutViewModel extends ViewModel {

    private AddressRepository mAddressRepository;
    private MutableLiveData<List<Address>> mAddress;
    private MutableLiveData<CartResponse> mCart;
    private CartRepo mCartRepo;
    private CartItemsManger cartItemsManger;
    private MutableLiveData<Boolean> addressIsLoading,cartIsLoading;

    public void init(Application application,String userId) {


        // initiate repos
        mAddressRepository = AddressRepository.getInstance();
        mCartRepo = CartRepo.getInstance();

        // get address
        mAddress = mAddressRepository.getAddresses(userId);
        addressIsLoading = mAddressRepository.getmIsLoading();

        // get cart items
        mCart = mCartRepo.getCart(loadCartFromSharedPref(application),"1");
        cartIsLoading = mCartRepo.cartIsLoading();


    }

    private List<CartItem> loadCartFromSharedPref(Context context) {
        cartItemsManger = new CartItemsManger(context);
        return cartItemsManger.getCartItems();

    }


    public MutableLiveData<List<Address>> getAddress() {
        if(mAddress == null) mAddress = new MutableLiveData<>();
        return mAddress;
    }


    public MutableLiveData<CartResponse> getCart() {
        if (mCart == null) mCart = new MutableLiveData<>();
        return mCart;
    }

    public MutableLiveData<Boolean> addressIsLoading() {
        if(addressIsLoading == null) addressIsLoading = new MutableLiveData<>();
        return addressIsLoading;
    }

    public MutableLiveData<Boolean> cartIsLoading() {
        if(cartIsLoading == null) cartIsLoading = new MutableLiveData<>();
        return cartIsLoading;
    }
}
