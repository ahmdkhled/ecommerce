package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.repository.OrdersRepository;

import java.util.List;

public class PaymentViewModel extends ViewModel {

    private OrdersRepository mOrdersRepository;
    private MutableLiveData<Order> mOrder;
    private MutableLiveData<Boolean> isPlacing;

    public void placeOrder(long userId, int addressId, List<CartItem> cartItems){
        mOrdersRepository = OrdersRepository.getInstence();
        mOrder = mOrdersRepository.placeOrder(userId,addressId,cartItems);
        isPlacing = mOrdersRepository.getIsPlacing();
    }

    public MutableLiveData<Boolean> getIsPlacing() {
        if(isPlacing == null)isPlacing = new MutableLiveData<>();
        return isPlacing;
    }

    public MutableLiveData<Order> getmOrder() {
        if(mOrder == null) mOrder = new MutableLiveData<>();
        return mOrder;
    }
}
