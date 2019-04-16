package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.repository.CartItemsRepository;

public class CartItemsViewModel extends ViewModel {

    private MutableLiveData<CartResponse> cartItems;

    public MutableLiveData<CartResponse> getCartItems(String ids,String q,String page){
           cartItems=CartItemsRepository.getInstance().getCartItems(ids,q,page);
       return cartItems;
    }

    public MutableLiveData<CartResponse> getCartItems() {
        return cartItems;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("CARRTTT","onCleared  ");

    }
}
