package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepository {

    private static OrdersRepository ordersRepository;
    private MutableLiveData<ArrayList<Order>> orders=new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();

    public static OrdersRepository getInstence(){
        if (ordersRepository==null)
            ordersRepository=new OrdersRepository();
        return ordersRepository;
    }

    public MutableLiveData<ArrayList<Order>> getOrders(final String userId){
        isLoading.setValue(true);
        RetrofetClient.getApiService()
                .getOrders(userId)
                .enqueue(new Callback<ArrayList<Order>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                        if (response.isSuccessful()){
                            orders.setValue(response.body());
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                        Log.d("ORDERSS",t.getMessage());
                        isLoading.setValue(false);
                    }
                });
        return orders;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
