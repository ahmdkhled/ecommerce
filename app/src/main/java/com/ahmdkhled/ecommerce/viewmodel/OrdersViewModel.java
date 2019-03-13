package com.ahmdkhled.ecommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.repository.OrdersRepository;

import java.util.ArrayList;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Order>> orders;
    private MutableLiveData<Boolean> isLoading;


    public MutableLiveData<ArrayList<Order>> getOrders(String userId){
        if (orders==null)
            orders= OrdersRepository.getInstence().getOrders(userId);

        return orders;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return OrdersRepository.getInstence().getIsLoading();
    }
}
