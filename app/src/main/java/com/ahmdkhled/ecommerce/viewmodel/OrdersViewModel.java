package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.repository.OrdersRepository;

import java.util.ArrayList;
import java.util.List;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Order>> orders;
    private MutableLiveData<Boolean> isLoading;
    private OrdersRepository ordersRepository;
    private MutableLiveData<Order> placeOrderResponse;
    private MutableLiveData<Boolean> isOrderPlaceing;

    public void init(){
        ordersRepository = OrdersRepository.getInstence();
    }

    public MutableLiveData<ArrayList<Order>> getOrders(String userId){
        if (orders==null)
            orders= OrdersRepository.getInstence().getOrders(userId);
        return orders;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return OrdersRepository.getInstence().getIsLoading();
    }

    public void placeOrder(long userId, int addressId, List<CartItem> cartItems){
        placeOrderResponse = ordersRepository.placeOrder(userId,addressId,cartItems);
        isOrderPlaceing = ordersRepository.getIsPlacing();
    }

    public MutableLiveData<Order> getPlaceOrderResponse() {
        if(placeOrderResponse == null)placeOrderResponse = new MutableLiveData<>();
        return placeOrderResponse;
    }

    public MutableLiveData<Boolean> getIsOrderPlaceing() {
        if(isOrderPlaceing == null)isOrderPlaceing = new MutableLiveData<>();
        return isOrderPlaceing;
    }
}
