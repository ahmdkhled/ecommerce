package com.ahmdkhled.ecommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.view.View;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.CartItem;
import com.ahmdkhled.ecommerce.model.CartResponse;
import com.ahmdkhled.ecommerce.model.Product;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartRepo {

    private static CartRepo mInstance;
    private MutableLiveData<CartResponse> mCart;
    private MutableLiveData<Boolean> isLoading;

    public static CartRepo getInstance(){
        if(mInstance == null){
            mInstance = new CartRepo();
        }
        return mInstance;
    }

    public MutableLiveData<CartResponse> getCart(final List<CartItem> cartItems, final String page){
        mCart = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        isLoading.setValue(true);
        String ids = getIdsAsString(cartItems);
        String q=getQuantitiesAsString(cartItems);
        RetrofetClient.getApiService().getCartItems(ids,q,page)
                .enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse (Call<CartResponse> call, Response<CartResponse> response) {
                        isLoading.setValue(false);
                        if(response.isSuccessful()){
                            mCart.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {
                        Log.d("CARTTT",t.getMessage());
                        isLoading.setValue(false);
                    }
                });

        return mCart;
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

    public MutableLiveData<Boolean> cartIsLoading() {
        if(isLoading == null) isLoading = new MutableLiveData<>();
        return isLoading;
    }
}
