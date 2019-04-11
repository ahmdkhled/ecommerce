package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.Order;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepository {

    private static OrdersRepository ordersRepository;
    private MutableLiveData<ArrayList<Order>> orders;
    private MutableLiveData<Order> mOrder;
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();
    private MutableLiveData<Boolean> isPlacing;

    public static OrdersRepository getInstence(){
        if (ordersRepository==null)
            ordersRepository=new OrdersRepository();
        return ordersRepository;
    }

    public MutableLiveData<ArrayList<Order>> getOrders(final String userId){
        orders = new MutableLiveData<>();
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


    public MutableLiveData<Order> placeOrder(long userid, int addressId, List<CartItem> cartItems){
        mOrder = new MutableLiveData<>();
        isPlacing = new MutableLiveData<>();
        isPlacing.setValue(false);
        RetrofetClient.getApiService()
                .makeOrder(getIdsAsString(cartItems),getQuantitiesAsString(cartItems),userid,addressId)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.isSuccessful()){
                            Log.d("ORDERSS","q "+response.body().getOrderItems().get(0).getOrderItem_quantity());
                            mOrder.setValue(response.body());
                        }
                        isPlacing.setValue(true);
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.d("ORDERSS",t.getMessage());
                        isPlacing.setValue(true);
                    }
                });
        return mOrder;
    }


    private String getIdsAsString(List<CartItem> cartItems){
        StringBuilder sb=new StringBuilder();
        for (int i=0 ;i<cartItems.size(); i++){
            if (i<cartItems.size()-1){
                sb.append(cartItems.get(i).getProduct().getId()).append(",");
            }else {
                sb.append(cartItems.get(i).getProduct().getId());
            }
        }
        return sb.toString();
    }

    private String getQuantitiesAsString(List<CartItem> cartItems){
        StringBuilder sb=new StringBuilder();
        for (int i=0 ;i<cartItems.size(); i++){
            if (i<cartItems.size()-1){
                sb.append(cartItems.get(i).getQuantity()).append(",");
            }else {
                sb.append(cartItems.get(i).getQuantity());
            }
        }
        return sb.toString();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getIsPlacing() {
        return isPlacing;
    }
}
